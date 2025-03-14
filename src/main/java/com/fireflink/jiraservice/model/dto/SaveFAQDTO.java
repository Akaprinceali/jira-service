package com.fireflink.jiraservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaveFAQDTO {

    private String question;
    private String answer;

}
