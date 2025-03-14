package com.fireflink.jiraservice.controller;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.TopicDTO;
import com.fireflink.jiraservice.model.entity.Topic;
import com.fireflink.jiraservice.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping("/save-topic")
    public ApiResponseDTO<String> saveTopic(@RequestBody TopicDTO topicDTO, @RequestParam String email){
        return topicService.saveTopic(topicDTO,email);
    }



    @DeleteMapping("/delete-all")
    public ApiResponseDTO<String> deleteAllTopic(){
        return topicService.deleteAllTopic();
    }

    @DeleteMapping("/delete-topic-id")
    public ApiResponseDTO<String> deleteTopicById(@RequestParam String topicId){
        return topicService.deleteTopicById(topicId);
    }

}
