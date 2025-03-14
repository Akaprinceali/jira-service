package com.fireflink.jiraservice.dao.impl;

import com.fireflink.jiraservice.dao.FAQDao;
import com.fireflink.jiraservice.model.entity.FAQ;
import com.fireflink.jiraservice.repository.FAQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FAQDaoImpl implements FAQDao {

    private final FAQRepository faqRepository;

    @Override
    public String saveFAQ(FAQ faq) {
        return "FAQ created with id: "+faqRepository.save(faq).getFaqId();
    }

    @Override
    public List<FAQ> findAllFAQ() {
        return faqRepository.findAll();
    }

    @Override
    public void deleteAllFAQ() {
        faqRepository.deleteAll();
    }

    @Override
    public void deleteById(String faqId) {
         faqRepository.deleteById(faqId);
    }

    @Override
    public Optional<FAQ> findByFAQId(String faqId) {
        return faqRepository.findById(faqId);
    }
}
