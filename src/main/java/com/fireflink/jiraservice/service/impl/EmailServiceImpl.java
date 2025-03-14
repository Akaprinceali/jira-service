package com.fireflink.jiraservice.service.impl;

import com.fireflink.jiraservice.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendResetPasswordEmail(String to, String token) {
        String resetLink = "http://localhost:8080/api/public/verify-reset-token?token=" + token;

        String emailContent = "<!DOCTYPE html>" +
                "<html><head><title>Reset Your Password</title></head><body>" +
                "<h2>Reset Your Password</h2>" +
                "<p>Click the link below to reset your password. This link will expire in 5 minutes.</p>" +
                "<a href='" + resetLink + "' style='display:inline-block;background-color:#008CBA;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;'>Reset Password</a>" +
                "<p>If you didn't request this, you can ignore this email.</p>" +
                "</body></html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Reset Your Password");
            helper.setText(emailContent, true); // Set true for HTML email

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public void sendCommentEmail(String to,String username,String comment) {


        String emailContent = "<!DOCTYPE html>" +
                "<html><head><title>New Comment on Assigned Ticket</title></head><body>" +
                "<h2>New Comment on Your Assigned Ticket</h2>" +
                "<p>"+username+" has commented on your assigned ticket:"+"'"+ comment+"' </p>" +

                "<blockquote style='font-style: italic; color: #555;'>\"This should be prioritized.\"</blockquote>" +
                "<p>Please review the comment and take necessary action.</p>" +
                "<a href=' ' style='display:inline-block;background-color:#28a745;color:white;padding:10px 20px;text-decoration:none;border-radius:5px;'>View Ticket</a>" +
                "<p>If this wasn't intended for you, please ignore this email.</p>" +
                "</body></html>";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Reset Your Password");
            helper.setText(emailContent, true); // Set true for HTML email

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public String generateToken() {
        return UUID.randomUUID().toString(); // Secure random token
    }
}
