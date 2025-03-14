package com.fireflink.jiraservice.service;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.PrivilegeDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PrivilegeService {

    ResponseEntity<ApiResponseDTO<String>> savePrivilege(PrivilegeDTO privilegeDTO, String email);

    ResponseEntity<ApiResponseDTO<List<String>>> getPrivilegeNames();

    ResponseEntity<ApiResponseDTO<List<PrivilegeDTO>>> getAllPrivileges();

    ResponseEntity<ApiResponseDTO<String>> updatePrivilege(PrivilegeDTO privilegeDTO,String email);

    ResponseEntity<ApiResponseDTO<String>> deletePrivilegeById(String email, String privilegeId);
}
