package com.fireflink.jiraservice.dao;

import com.fireflink.jiraservice.model.dto.TicketDTO;
import com.fireflink.jiraservice.model.entity.Ticket;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface TicketDao {

    Page<TicketDTO> findAllTickets(int pageNumber, int pageSize);

    Page<TicketDTO> findAllTicketsByEmail(int pageNumber, int pageSize, String email);

    void createTicket(Ticket ticket);

    Optional<Ticket> findTicketById(String ticketId);

    void deleteTicketById(Ticket ticket);


    void deleteAllTicket();

    List<Ticket> findAllTickets();

    Page<TicketDTO> searchTicket(String searchItem,int pageNumber,int pageSize);

}
