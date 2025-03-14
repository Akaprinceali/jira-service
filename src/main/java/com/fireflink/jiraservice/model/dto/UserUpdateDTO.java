package com.fireflink.jiraservice.model.dto;

import com.fireflink.jiraservice.model.enums.Access;
import com.fireflink.jiraservice.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateDTO {

    private String username;
    private String privilege;
    private long noOfTickets;
    private String status;
    private long phoneNumber;
    private String password;
    private Access access;
    private Role role;
}
