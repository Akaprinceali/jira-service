package com.fireflink.jiraservice.model.dto;

import com.fireflink.jiraservice.model.enums.Severity;
import com.fireflink.jiraservice.model.enums.Status;
import lombok.Data;

@Data
public class UpdateTicketDTO {

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

}
