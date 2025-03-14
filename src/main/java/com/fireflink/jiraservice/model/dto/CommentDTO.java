package com.fireflink.jiraservice.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDTO {

    private String ticketId;
    private String email;
    private String content;
    private boolean isPrivate;
    private String parentCommentId;
    private List<CommentDTO> replies = new ArrayList<>();

}
