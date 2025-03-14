package com.fireflink.jiraservice.dao.impl;

import com.fireflink.jiraservice.dao.TicketDao;
import com.fireflink.jiraservice.model.dto.TicketDTO;
import com.fireflink.jiraservice.model.entity.Ticket;
import com.fireflink.jiraservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketDaoImpl implements TicketDao {

    private final TicketRepository ticketRepository;

    @Override
    public Page<TicketDTO> findAllTickets(int pageNumber, int pageSize) {

        return ticketRepository.findAllTickets(PageRequest.of(pageNumber,pageSize));
    }

    @Override
    public Page<TicketDTO> findAllTicketsByEmail(int pageNumber, int pageSize, String email) {

        PageRequest pageable = PageRequest.of(pageNumber, pageSize);

        return ticketRepository.findAllTicketsByEmail(email,pageable);
    }

    @Override
    public void createTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public Optional<Ticket> findTicketById(String ticketId) {
        return ticketRepository.findById(ticketId);
    }

    @Override
    public void deleteTicketById(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    @Override
    public void deleteAllTicket() {
        ticketRepository.deleteAll();
    }

    @Override
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Page<TicketDTO> searchTicket(String searchItem,int pageNumber,int pageSize) {
        return ticketRepository.findBySummaryRegex(searchItem,PageRequest.of(pageNumber,pageSize));
    }


}
