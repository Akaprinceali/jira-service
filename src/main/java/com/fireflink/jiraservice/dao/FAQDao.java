package com.fireflink.jiraservice.dao;

import com.fireflink.jiraservice.model.entity.FAQ;

import java.util.List;
import java.util.Optional;

public interface FAQDao {

    String saveFAQ(FAQ faq);

    List<FAQ> findAllFAQ();

    void deleteAllFAQ();

    void deleteById(String faqId);

    Optional<FAQ> findByFAQId(String faqId);
}
