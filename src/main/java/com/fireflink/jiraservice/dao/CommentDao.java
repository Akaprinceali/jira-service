package com.fireflink.jiraservice.dao;

import com.fireflink.jiraservice.model.entity.Comment;

public interface CommentDao {

    boolean existById(String commentId);

    void deleteCommentById(String commentId);
}
