package com.fireflink.jiraservice.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmailConfiguration extends BaseEntity{

    @Id
    private String id;
    private String action;
    private String description;
    private boolean isEnabled;
    private String subject;
    private String body;

}
