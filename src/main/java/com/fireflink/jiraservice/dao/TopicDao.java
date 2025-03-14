package com.fireflink.jiraservice.dao;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.entity.Topic;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TopicDao {

    String saveTopic(Topic topic);

    Page<Topic> getAllTopicByPagination(int pageNo, int pageSize);

    List<Topic> findByTopicName(String content);

    void deleteAllTopic();

    void deleteTopicById(String topicId);

    Optional<Topic> findByTopicId(String topicId);
}
