package com.fireflink.jiraservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FAQ extends BaseEntity{

    @Id
    private String faqId;
    private String question;
    private String answer;

}
