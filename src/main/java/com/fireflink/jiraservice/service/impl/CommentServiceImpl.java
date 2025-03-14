package com.fireflink.jiraservice.service.impl;

import com.fireflink.jiraservice.dao.CommentDao;
import com.fireflink.jiraservice.dao.TicketDao;
import com.fireflink.jiraservice.dao.UserDao;
import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.CommentDTO;
import com.fireflink.jiraservice.model.entity.Comment;
import com.fireflink.jiraservice.model.entity.Ticket;
import com.fireflink.jiraservice.model.entity.User;
import com.fireflink.jiraservice.service.CommentService;
import com.fireflink.jiraservice.service.EmailService;
import com.fireflink.jiraservice.service.SequenceGeneratorService;
import com.fireflink.jiraservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;
    private final UserDao userDao;
    private final TicketDao ticketDao;
    private final ModelMapper modelMapper;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final EmailService emailService;


    @Override
    public ResponseEntity<ApiResponseDTO<String>> postComment(CommentDTO commentDTO) {
        Optional<User> optionalUser = userDao.findByEmail(commentDTO.getEmail());

        if (optionalUser.isPresent()) {
            Optional<Ticket> optionalTicket = ticketDao.findTicketById(commentDTO.getTicketId());

            if (optionalTicket.isPresent()) {
                Ticket ticket = optionalTicket.get();
                List<Comment> comments = ticket.getComments();

                Comment newComment = modelMapper.map(commentDTO, Comment.class);
                newComment.generateId(sequenceGeneratorService);

                // 🔗 Add reply logic
                if (commentDTO.getParentCommentId() != null) {
                    boolean replyAdded = addReplyToComment(comments, commentDTO.getParentCommentId(), newComment);
                    if (!replyAdded) {
                        return ResponseUtil.getBadRequestResponse(null, "Parent comment not found.");
                    }
                } else {
                    comments.add(newComment);
                }

                ticket.setComments(comments);
                ticketDao.createTicket(ticket); // Update ticket with new comment/reply

                return ResponseUtil.getOkResponse(null, "Comment saved successfully with id: ");
            }
            return ResponseUtil.getBadRequestResponse(null, "Ticket does not exist with this ID.");
        }
        return ResponseUtil.getBadRequestResponse(null, "Invalid user.");
    }

    // 🔍 Recursive method to add reply
    private boolean addReplyToComment(List<Comment> comments, String parentCommentId, Comment reply) {
        for (Comment comment : comments) {
            if (Objects.equals(comment.getCommentId(), parentCommentId)) {
                comment.getReplies().add(reply);
                return true;
            } else if (!comment.getReplies().isEmpty()) {
                if (addReplyToComment(comment.getReplies(), parentCommentId, reply)) {
                    return true;
                }
            }
        }
        return false;
    }



    @Override
    public ResponseEntity<ApiResponseDTO<Page<CommentDTO>>> getCommentsByTicketId(String ticketId, int pageNumber, int pageSize) {
        Optional<Ticket> optionalTicket = ticketDao.findTicketById(ticketId);

        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            List<Comment> comments = ticket.getComments();

            if (!comments.isEmpty()) {
                List<CommentDTO> commentDTOList = comments.parallelStream()
                        .filter(comment -> !comment.isPrivate())
                        .map(comment -> modelMapper.map(comment, CommentDTO.class))
                        .toList();

                int totalComments = commentDTOList.size();
                int start = pageNumber * pageSize;
                int end = Math.min(start + pageSize, totalComments);

                if (start >= totalComments) {
                    return ResponseUtil.getBadRequestResponse(null, "No comments available on this page.");
                }

                Page<CommentDTO> paginatedComments = new PageImpl<>(
                        commentDTOList.subList(start, end),
                        PageRequest.of(pageNumber, pageSize),
                        totalComments
                );

                return ResponseUtil.getOkResponse(paginatedComments, "Comments fetched successfully.");
            }
            return ResponseUtil.getOkResponse(null, "No comments yet. Be the first to comment.");
        }
        return ResponseUtil.getBadRequestResponse(null, "Invalid ticket ID.");
    }


    @Override
    public ResponseEntity<ApiResponseDTO<String>> deleteCommentById(String commentId) {
        if (commentDao.existById(commentId)) {
            commentDao.deleteCommentById(commentId);
            return ResponseUtil.getOkResponse(null, "Comment deleted successfully.");
        }
        return ResponseUtil.getBadRequestResponse(null, "This comment doesn't exist.");
    }

}
