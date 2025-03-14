package com.fireflink.jiraservice.dao.impl;

import com.fireflink.jiraservice.dao.TopicDao;
import com.fireflink.jiraservice.model.entity.Topic;
import com.fireflink.jiraservice.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TopicDaoImpl implements TopicDao {

    private final TopicRepository topicRepository;

    @Override
    public String saveTopic(Topic topic) {
        return "Topic created with id: "+topicRepository.save(topic).getTopicId();
    }

    @Override
    public Page<Topic> getAllTopicByPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return topicRepository.findAll(pageable);
    }

    @Override
    public List<Topic> findByTopicName(String content) {
        return topicRepository.findByTopicNameRegex(content);
    }

    @Override
    public void deleteAllTopic() {
        topicRepository.deleteAll();
    }

    @Override
    public void deleteTopicById(String topicId) {
        topicRepository.deleteById(topicId);
    }

    @Override
    public Optional<Topic> findByTopicId(String topicId) {
        return topicRepository.findById(topicId);
    }


}
