package com.fireflink.jiraservice.repository;

import com.fireflink.jiraservice.model.dto.TicketDTO;
import com.fireflink.jiraservice.model.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket,String> {

    @Query(value = "{}",
            fields = "{ 'issueRelatedTo': 1, 'summary': 1, 'status': 1, 'assignedToName': 1, 'assignedToEmail': 1, 'assigneeGroup': 1, 'description': 1, 'priority': 1, 'severity': 1, 'license': 1, 'licenseAccess': 1 }")
    Page<TicketDTO> findAllTickets(Pageable pageable);

    @Query(value = "{ 'assignedToEmail': ?0 }",
            fields = "{ 'issueRelatedTo': 1, 'summary': 1, 'status': 1, 'assignedToName': 1, 'assignedToEmail': 1, 'assigneeGroup': 1, 'description': 1, 'priority': 1, 'severity': 1, 'license': 1, 'licenseAccess': 1 }")
    Page<TicketDTO> findAllTicketsByEmail(String email, Pageable pageable);

    @Query("{ 'summary': { $regex: ?0, $options: 'i' } }")
    Page<TicketDTO> findBySummaryRegex(String summary, Pageable pageable);

    Optional<Ticket> findByTicketId(String ticketId);
}
