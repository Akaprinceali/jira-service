package com.fireflink.jiraservice.repository;

import com.fireflink.jiraservice.model.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TopicRepository extends MongoRepository<Topic,String> {

//    Page<Topic> getAllTopicByPagination(int pageNo, int pageSize);

    @Query("{'topicName': {$regex: ?0, $options: 'i'}}")
    List<Topic> findByTopicNameRegex(String regexPattern);
}
