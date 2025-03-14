package com.fireflink.jiraservice.model.entity;

import com.fireflink.jiraservice.model.enums.Access;
import com.fireflink.jiraservice.model.enums.Role;
import com.fireflink.jiraservice.service.SequenceGeneratorService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class User extends BaseEntity{

    @Id
    private String userId;
    private String username;
    private String privilege;
    private long noOfTickets;
    private String status;
    private  String email;
    private long phoneNumber;
    private String password;
    private Access access;
    @Field("password_reset_token")
    private String passwordResetToken;
    private Role role;

    @Field("token_generated_time")
    private LocalDateTime tokenGeneratedTime;

    public void generateId(SequenceGeneratorService sequenceGeneratorService) {
        this.userId = sequenceGeneratorService.getNextSequence("sequence_generator");
    }

}
