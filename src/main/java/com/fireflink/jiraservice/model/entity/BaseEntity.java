package com.fireflink.jiraservice.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    private LocalDateTime createdOn;
    private LocalDateTime lastUpdateOn;
    private String createdByName;
    private String createdByEmail;
    private String modifiedByName;
    private String modifiedByEmail;

    public void setCreateEntity(String userName,String userEmail){
        this.createdByEmail=userEmail;
        this.createdByName=userName;
        this.createdOn = LocalDateTime.now();

    }

    public void setModifiedEntity(String userName, String userEmail){
        this.modifiedByEmail = userEmail;
        this.modifiedByName = userName;
        this.lastUpdateOn = LocalDateTime.now();
    }
}
