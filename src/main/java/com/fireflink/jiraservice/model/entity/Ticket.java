package com.fireflink.jiraservice.model.entity;

import com.fireflink.jiraservice.model.enums.Severity;
import com.fireflink.jiraservice.model.enums.Status;
import com.fireflink.jiraservice.service.SequenceGeneratorService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Ticket extends BaseEntity{

    @Id
    private String ticketId;
    private String issueRelatedTo;
    private String summary;
    private Status status;
    private String assignedToName;
    private String assignedToEmail;
    private String assigneeGroup;
    private String description;
    private String priority;
    private Severity severity;
    private String license;
    private Boolean licenseAccess;
    private List<String> participantsEmail;
    private List<Comment> comments=new ArrayList<>();
    private List<String> attachedFileId = new ArrayList<>();
    private String clientName;
    private String clientEmail;

    public void generateId(SequenceGeneratorService ticketSequenceService) {
        this.ticketId = ticketSequenceService.getNextTicketSequence("ticket_sequence");
    }


}










