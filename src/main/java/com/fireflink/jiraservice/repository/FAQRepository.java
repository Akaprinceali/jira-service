package com.fireflink.jiraservice.repository;

import com.fireflink.jiraservice.model.entity.FAQ;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FAQRepository extends MongoRepository<FAQ,String> {
}
