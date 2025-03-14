package com.fireflink.jiraservice.dao.impl;

import com.fireflink.jiraservice.dao.CommentDao;
import com.fireflink.jiraservice.model.entity.Comment;
import com.fireflink.jiraservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {

    private final CommentRepository commentRepository;

    @Override
    public boolean existById(String commentId) {
        return commentRepository.existsById(commentId);
    }

    @Override
    public void deleteCommentById(String commentId) {
        commentRepository.deleteById(commentId);
    }
}
