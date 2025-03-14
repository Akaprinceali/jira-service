package com.fireflink.jiraservice.dao.impl;

import com.fireflink.jiraservice.dao.EmailConfigurationDao;
import com.fireflink.jiraservice.model.entity.EmailConfiguration;
import com.fireflink.jiraservice.repository.EmailConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmailConfigurationDaoImpl implements EmailConfigurationDao {

    private final EmailConfigurationRepository emailConfigurationRepository;


    @Override
    public String saveConfig(EmailConfiguration emailConfiguration) {
        return "Email configuration saved successfully with id "+emailConfigurationRepository.save(emailConfiguration).getId();
    }

    @Override
    public EmailConfiguration getConfig(String action) {
        return emailConfigurationRepository.findByAction(action);
    }

    @Override
    public List<EmailConfiguration> getAllConfigurations() {
        return emailConfigurationRepository.findAll();
    }
}
