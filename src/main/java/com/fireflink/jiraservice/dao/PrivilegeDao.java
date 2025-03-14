package com.fireflink.jiraservice.dao;

import com.fireflink.jiraservice.model.dto.PrivilegeDTO;
import com.fireflink.jiraservice.model.entity.Privilege;

import java.util.List;

public interface PrivilegeDao {

    String savePrivilege(Privilege privilege);

    List<PrivilegeDTO> getAllPrivileges();

    void deletePrivilegeById(String privilegeId);
}
