package com.fireflink.jiraservice.service;

import org.springframework.mail.SimpleMailMessage;

import java.util.Random;

public interface EmailService {

     void sendResetPasswordEmail(String to, String token);

     String generateToken();

     void sendCommentEmail(String to,String username,String comment);
}
