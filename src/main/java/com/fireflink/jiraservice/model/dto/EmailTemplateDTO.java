package com.fireflink.jiraservice.model.dto;

import lombok.Data;

@Data
public class EmailTemplateDTO {

    private String action;
    private String subject;
    private String body;

}
