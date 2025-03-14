package com.fireflink.jiraservice.service;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.TicketDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TicketService {

    ResponseEntity<ApiResponseDTO<Page<TicketDTO>>> findAllTickets(int pageNumber, int pageSize);

    ResponseEntity<ApiResponseDTO<Page<TicketDTO>>> findTicketsById(int pageNumber, int pageSize, String email);

    ResponseEntity<ApiResponseDTO<String>> createTicket(MultipartFile[] file, String ticketDTO, String email) throws IOException;

    ResponseEntity<ApiResponseDTO<String>> updateTicket(MultipartFile[] file, String ticketDTOString, String email) throws IOException;

    ResponseEntity<ApiResponseDTO<String>> assignTicket(List<String> ticketIds, String userEmail, String adminEmail);

    ResponseEntity<ApiResponseDTO<String>> deleteTicketById(String ticketId, String adminEmail);

    ResponseEntity<ApiResponseDTO<String>> deleteAllTicket();

    ResponseEntity<ApiResponseDTO<Page<TicketDTO>>> searchTicket(String searchItem,int pageNumber,int pageSize);
}
