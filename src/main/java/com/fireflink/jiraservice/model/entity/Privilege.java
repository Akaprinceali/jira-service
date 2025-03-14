package com.fireflink.jiraservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Privilege extends BaseEntity{

    @Id
    private String privilegeId;
    private String privilegeName;
    private String description;

}
