package com.fireflink.jiraservice.service.impl;

import com.fireflink.jiraservice.dao.TopicDao;
import com.fireflink.jiraservice.dao.UserDao;
import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.TopicDTO;
import com.fireflink.jiraservice.model.entity.Topic;
import com.fireflink.jiraservice.model.entity.User;
import com.fireflink.jiraservice.service.TopicService;
import com.fireflink.jiraservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicDao topicDao;
    private final UserDao userDao;


    @Override
    public ApiResponseDTO<String> saveTopic(TopicDTO topicDTO, String email) {

        if(email!=null){

            Optional<User> user = userDao.findByEmail(email);
            if(user.isPresent()) {

                Topic topic= Topic.builder().build();
                BeanUtils.copyProperties(topicDTO,topic);
                topic.setCreateEntity(user.get().getUsername(),email);

                return ApiResponseDTO.<String>builder().message("Topic saved successfully").data(topicDao.saveTopic(topic)).httpStatus(HttpStatus.ACCEPTED).build();
            }
                throw new RuntimeException("User not exist with this email");
        }

        return ApiResponseDTO.<String>builder().message("Make sure to enter email").data(null).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @Override
    public ApiResponseDTO<Page<TopicDTO>> getAllTopicByPagination(int pageNo, int pageSize) {
        Page<Topic> topicList = topicDao.getAllTopicByPagination(pageNo, pageSize);

        if (Objects.nonNull(topicList)) {

            Page<TopicDTO> topicDTOList = topicList.map(topic ->
                    new TopicDTO(topic.getTopicName(), topic.getSteps()));

            return ApiResponseDTO.<Page<TopicDTO>>builder().message("Topic fetched successfully").data(topicDTOList).httpStatus(HttpStatus.FOUND).build();
        }
        return ApiResponseDTO.<Page<TopicDTO>>builder().message("Topic doesn't exist").data(null).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ApiResponseDTO<List<Topic>> searchByTopicName(String content) {

        List<Topic> topic = topicDao.findByTopicName(content);
        if (Objects.nonNull(topic)) {
            return ApiResponseDTO.<List<Topic>>builder().message("Topic fetched successfully").data(topic).httpStatus(HttpStatus.FOUND).build();
        }
        return ApiResponseDTO.<List<Topic>>builder().message("Topic doesn't exist ").data(null).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @Override
    public ApiResponseDTO<String> deleteAllTopic() {
         topicDao.deleteAllTopic();
         return ApiResponseDTO.<String>builder().message("Topic data deleted successfully ").data(null).httpStatus(HttpStatus.OK).build();
    }

    @Override
    public ApiResponseDTO<String> deleteTopicById(String topicId) {

        Optional<Topic> topic = topicDao.findByTopicId(topicId);

        if(topic.isPresent()) {
            topicDao.deleteTopicById(topicId);
            return ApiResponseDTO.<String>builder().message("Topic data deleted successfully ").data(null).httpStatus(HttpStatus.OK).build();
        }
        return ApiResponseDTO.<String>builder().message("Topic doesn't exist ").data(null).httpStatus(HttpStatus.OK).build();
    }
}
