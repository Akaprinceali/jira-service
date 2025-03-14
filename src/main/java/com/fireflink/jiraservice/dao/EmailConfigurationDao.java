package com.fireflink.jiraservice.dao;

import com.fireflink.jiraservice.model.entity.EmailConfiguration;

import java.util.List;

public interface EmailConfigurationDao {

    String saveConfig(EmailConfiguration emailConfiguration);

    EmailConfiguration getConfig(String action);

    List<EmailConfiguration> getAllConfigurations();
}
