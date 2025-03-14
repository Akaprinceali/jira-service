package com.fireflink.jiraservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fireflink.jiraservice.dao.TicketDao;
import com.fireflink.jiraservice.dao.UserDao;
import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.TicketDTO;
import com.fireflink.jiraservice.model.dto.UpdateTicketDTO;
import com.fireflink.jiraservice.model.entity.Ticket;
import com.fireflink.jiraservice.model.entity.User;
import com.fireflink.jiraservice.service.FileService;
import com.fireflink.jiraservice.service.SequenceGeneratorService;
import com.fireflink.jiraservice.service.TicketService;
import com.fireflink.jiraservice.utils.ResponseUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketServiceImpl implements TicketService {

    private final TicketDao ticketDao;
    private final ModelMapper modelMapper;
    private final UserDao userDao;
    private final FileService fileService;
    private final SequenceGeneratorService ticketSequenceService;
    private final Validator validator;
    private final ObjectMapper objectMapper;
    private static final int MAX_FILES = 5;
    private static final long MAX_SIZE = (long) (14.3 * 1024 * 1024);

    @Override
    public ResponseEntity<ApiResponseDTO<Page<TicketDTO>>> findAllTickets(int pageNumber, int pageSize) {


        Page<TicketDTO> tickets = ticketDao.findAllTickets(pageNumber, pageSize);

        if(!tickets.isEmpty()){
            return ResponseUtil.getOkResponse(tickets,"Tickets fetched successfully");
        }

        return ResponseUtil.getNoContentResponse(null,"Tickets not assigned yet....");

    }

    @Override
    public ResponseEntity<ApiResponseDTO<Page<TicketDTO>>> findTicketsById(int pageNumber, int pageSize, String email) {

        Page<TicketDTO> ticketsByEmail = ticketDao.findAllTicketsByEmail(pageNumber, pageSize, email);

        if(!ticketsByEmail.isEmpty()){
            return ResponseUtil.getOkResponse(ticketsByEmail,"Tickets fetched successfully");
        }

        return ResponseUtil.getNoContentResponse(null,"Tickets not assigned yet....");

    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> createTicket(MultipartFile[] file, String ticketDTOString, String email) throws IOException {

        log.info("service executed {}","successfully");
        if (file.length > MAX_FILES) {
            return ResponseUtil.getBadRequestResponse(null,"You can upload a maximum of 5 files.");
        }

        // Check total file size
        long totalSize = 0;
        for (MultipartFile files : file) {
            totalSize += files.getSize();

            if (totalSize > MAX_SIZE) {
                return ResponseUtil.getBadRequestResponse(null,"File size should not exceed 14.3 MB");
            }
        }



        if(email!=null){
            TicketDTO ticketDTO = objectMapper.readValue(ticketDTOString, TicketDTO.class);
            Set<ConstraintViolation<TicketDTO>> violations = validator.validate(ticketDTO);

            if (!violations.isEmpty()) {
                throw  new ConstraintViolationException(violations);
            }

            Ticket ticket = modelMapper.map(ticketDTO, Ticket.class);
            Optional<User> user = userDao.findByEmail(email);
            if(user.isPresent()){
                ticket.setCreateEntity(user.get().getUsername(),user.get().getEmail());

                    for (MultipartFile multipartFile : file) {
                        if (!Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()) {
                            ticket.getAttachedFileId().add(fileService.addFiles(multipartFile.getOriginalFilename(), multipartFile));
                        }

                    }

                    ticket.generateId(ticketSequenceService);
                    ticketDao.createTicket(ticket);

                    return ResponseUtil.getOkResponse(null,"Ticket saved successfully with id :"+ticket.getTicketId());

            }

            return ResponseUtil.getBadRequestResponse(null,"User not exist");


        }

        return ResponseUtil.getBadRequestResponse(null,"Please enter email");


    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> updateTicket(MultipartFile[] file, String ticketDTOString, String email) throws IOException {

        if (file.length > MAX_FILES) {
            return ResponseUtil.getBadRequestResponse(null,"You can upload a maximum of 5 files.");
        }

        // Check total file size
        long totalSize = 0;
        for (MultipartFile files : file) {
            totalSize += files.getSize();

            if (totalSize > MAX_SIZE) {
                return ResponseUtil.getBadRequestResponse(null,"File size should not exceed 14.3 MB");
            }
        }

        if(email!=null){
            UpdateTicketDTO ticketDTO = objectMapper.readValue(ticketDTOString, UpdateTicketDTO.class);
            Optional<Ticket> ticket = ticketDao.findTicketById(ticketDTO.getTicketId());

            if(ticket.isPresent()) {
                Ticket oldTicket = ticket.get();
                modelMapper.map(ticketDTO, oldTicket);
                Set<ConstraintViolation<UpdateTicketDTO>> violations = validator.validate(ticketDTO);

             //   System.out.println(objectMapper.writeValueAsString(ticket));

                if (!violations.isEmpty()) {
                    throw new ConstraintViolationException(violations);
                }

                Optional<User> user = userDao.findByEmail(email);
                if (user.isPresent()) {
                    oldTicket.setModifiedEntity(user.get().getUsername(), user.get().getEmail());

                    for (MultipartFile multipartFile : file) {
                        if (!Objects.requireNonNull(multipartFile.getOriginalFilename()).isEmpty()) {
                            oldTicket.getAttachedFileId().add(fileService.addFiles(multipartFile.getOriginalFilename(), multipartFile));
                        }

                    }
                    ticketDao.createTicket(oldTicket);

                    return ResponseUtil.getOkResponse(null, "Ticket updated successfully with id :" + oldTicket.getTicketId());

                }

                return ResponseUtil.getBadRequestResponse(null, "User not exist");

            }

            return ResponseUtil.getBadRequestResponse(null, "Ticket not exist with this id: "+ticketDTO.getTicketId());

        }

        return ResponseUtil.getBadRequestResponse(null,"Please enter email");
    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> assignTicket(List<String> ticketIds, String userEmail, String adminEmail) {

        if(ticketIds.isEmpty()){

            return ResponseUtil.getBadRequestResponse(null,"Make sure to add tickets to assign.");

        }
       Optional<User> admin =userDao.findByEmail(adminEmail);

        if(admin.isPresent() && admin.get().getPrivilege().equalsIgnoreCase("ADMIN")){

                Optional<User> user = userDao.findByEmail(userEmail);

                if (user.isPresent()){

                    List<Ticket> assignedTickets=new ArrayList<>();

                    ticketIds.forEach(ticketId->{

                        Ticket oldTicket = ticketDao.findTicketById(ticketId).get();
                        oldTicket.setAssignedToName(user.get().getUsername());
                        oldTicket.setAssignedToEmail(user.get().getEmail());
                        ticketDao.createTicket(oldTicket);
                        assignedTickets.add(oldTicket);

                    });

                    user.get().setNoOfTickets(user.get().getNoOfTickets()+assignedTickets.size());
                    userDao.saveUser(user.get());

                    return ResponseUtil.getOkResponse(null,"Tickets assigned successfully");

                }

                return ResponseUtil.getBadRequestResponse(null,"User not exist with this email");

        }

        return ResponseUtil.getBadRequestResponse(null,"Admin not exist with this email, only admin authorize");

    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> deleteTicketById(String ticketId, String adminEmail) {

        Optional<User> user = userDao.findByEmail(adminEmail);

        if (user.isPresent() && user.get().getPrivilege().equalsIgnoreCase("ADMIN")){
            Optional<Ticket> optionalTicket = ticketDao.findTicketById(ticketId);

            if (optionalTicket.isPresent()){
                Ticket ticket = optionalTicket.get();
                ticketDao.deleteTicketById(ticket);

                return ResponseUtil.getOkResponse(null,"Ticket deleted successfully");
            }

            return ResponseUtil.getBadRequestResponse(null,"Ticket not exist with this id");

        }

        return ResponseUtil.getBadRequestResponse(null,"Admin not exist with this email, only admin authorize");

    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> deleteAllTicket() {

        List<Ticket> tickets = ticketDao.findAllTickets();

        if (!tickets.isEmpty()) {

            ticketDao.deleteAllTicket();

            return ResponseUtil.getOkResponse(null, "All tickets deleted successfully");

        }

        return ResponseUtil.getBadRequestResponse(null, "Ticket doesn't exist, first add tickets");

    }

    @Override
    public ResponseEntity<ApiResponseDTO<Page<TicketDTO>>> searchTicket(String searchItem,int pageNumber,int pageSize) {

        Page<TicketDTO> ticketDTOS = ticketDao.searchTicket(searchItem, pageNumber, pageSize);

        if (ticketDTOS.hasContent()) {
            return ResponseUtil.getOkResponse(ticketDTOS, "Tickets fetched successfully");
        }

        // Check if the search term has results in any page
        Page<TicketDTO> firstPageTickets = ticketDao.searchTicket(searchItem, 0, pageSize);
        if (firstPageTickets.hasContent()) {
            return ResponseUtil.getBadRequestResponse(null, "Data doesn't exist on this page, try a lower page number");
        }

        return ResponseUtil.getBadRequestResponse(null, "Ticket doesn't exist, search with another keyword");

    }


}
