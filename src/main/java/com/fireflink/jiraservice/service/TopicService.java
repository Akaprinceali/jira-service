package com.fireflink.jiraservice.service;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.TopicDTO;
import com.fireflink.jiraservice.model.entity.Topic;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TopicService {

    ApiResponseDTO<String> saveTopic(TopicDTO topicDTO, String email);

    ApiResponseDTO<Page<TopicDTO>> getAllTopicByPagination(int pageNo, int pageSize);

    ApiResponseDTO<List<Topic>> searchByTopicName(String content);

    ApiResponseDTO<String> deleteAllTopic();

    ApiResponseDTO<String> deleteTopicById(String topicId);
}
