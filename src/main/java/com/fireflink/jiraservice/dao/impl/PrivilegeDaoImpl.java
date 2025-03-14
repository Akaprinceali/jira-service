package com.fireflink.jiraservice.dao.impl;

import com.fireflink.jiraservice.dao.PrivilegeDao;
import com.fireflink.jiraservice.model.dto.PrivilegeDTO;
import com.fireflink.jiraservice.model.entity.Privilege;
import com.fireflink.jiraservice.repository.PrivilegeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PrivilegeDaoImpl implements PrivilegeDao {

    private final PrivilegeRepository privilegeRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public String savePrivilege(Privilege privilege) {
        return privilegeRepository.save(privilege).getPrivilegeId();
    }

    @Override
    public List<PrivilegeDTO> getAllPrivileges() {
        Query query=new Query();
        return mongoTemplate.find(query, PrivilegeDTO.class,"privilege");
    }

    @Override
    public void deletePrivilegeById(String privilegeId) {

        privilegeRepository.deleteById(privilegeId);

    }
}
