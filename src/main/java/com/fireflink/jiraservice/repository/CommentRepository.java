package com.fireflink.jiraservice.repository;

import com.fireflink.jiraservice.model.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment,String> {
}
