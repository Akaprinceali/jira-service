package com.fireflink.jiraservice.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sequence_generator")
public class SequenceGenerator {

    @Id
    private String id;
    private long sequence;
}
