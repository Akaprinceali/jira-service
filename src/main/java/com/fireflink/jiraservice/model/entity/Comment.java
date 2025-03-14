package com.fireflink.jiraservice.model.entity;

import com.fireflink.jiraservice.service.SequenceGeneratorService;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Comment extends BaseEntity{

    private String commentId;
    private String email;
    private String content;
    private boolean isPrivate;

    private String parentCommentId;

    private List<Comment> replies = new ArrayList<>();

    public void generateId(SequenceGeneratorService sequenceGeneratorService) {
        this.commentId = sequenceGeneratorService.getNextCommentSequence("comment_sequence");
    }
}
