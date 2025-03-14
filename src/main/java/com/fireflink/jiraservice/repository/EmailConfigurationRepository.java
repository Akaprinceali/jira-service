package com.fireflink.jiraservice.repository;

import com.fireflink.jiraservice.model.entity.EmailConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailConfigurationRepository extends MongoRepository<EmailConfiguration,String> {

    EmailConfiguration findByAction(String action);
}
