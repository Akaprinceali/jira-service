package com.fireflink.jiraservice.controller;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.CommentDTO;
import com.fireflink.jiraservice.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post-comment")
    @SecurityRequirement(name = "bearer Auth")
    public ResponseEntity<ApiResponseDTO<String>> postComment(@RequestBody CommentDTO commentDTO){

        return commentService.postComment(commentDTO);

    }

    @GetMapping("/get-comment-id/{pageNumber}/{pageSize}")
    @SecurityRequirement(name = "bearer Auth")
    public ResponseEntity<ApiResponseDTO<Page<CommentDTO>>> getCommentsByTicketId(@RequestParam String ticketId,@PathVariable int pageNumber,@PathVariable int pageSize){

        return commentService.getCommentsByTicketId(ticketId,pageNumber,pageSize);

    }

    @DeleteMapping("/delete-comment-id")
    @SecurityRequirement(name = "bearer Auth")
    public ResponseEntity<ApiResponseDTO<String>> deleteCommentById(@RequestParam String commentId){

        return commentService.deleteCommentById(commentId);

    }

}
