package com.fireflink.jiraservice.model.entity;

import com.fireflink.jiraservice.model.dto.StepDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
@Builder
public class Topic extends BaseEntity{

    @Id
    private String topicId;
    private String topicName;
    private List<StepDTO> steps;

}
