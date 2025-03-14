package com.fireflink.jiraservice.repository;

import com.fireflink.jiraservice.model.entity.Privilege;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivilegeRepository extends MongoRepository<Privilege,String> {
}
