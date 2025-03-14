package com.fireflink.jiraservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TopicDTO {

    private String topicName;
    private List<StepDTO> steps;

}
