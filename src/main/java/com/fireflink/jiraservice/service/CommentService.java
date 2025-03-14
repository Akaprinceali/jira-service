package com.fireflink.jiraservice.service;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CommentService {

    ResponseEntity<ApiResponseDTO<String>> postComment(CommentDTO commentDTO);

    ResponseEntity<ApiResponseDTO<Page<CommentDTO>>> getCommentsByTicketId(String ticketId, int pageNumber, int pageSize);

    ResponseEntity<ApiResponseDTO<String>> deleteCommentById(String commentId);
}
