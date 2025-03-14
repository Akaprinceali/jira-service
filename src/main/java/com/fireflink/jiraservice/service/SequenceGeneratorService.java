package com.fireflink.jiraservice.service;

public interface SequenceGeneratorService {

    String getNextSequence(String sequenceName);

    String getNextTicketSequence(String sequenceName);

    String getNextCommentSequence(String sequenceName);
}
