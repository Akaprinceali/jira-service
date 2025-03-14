package com.fireflink.jiraservice.service;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.EmailConfigurationDTO;
import com.fireflink.jiraservice.model.dto.EmailTemplateDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmailConfigurationService {

    ResponseEntity<ApiResponseDTO<String>> saveConfig(EmailConfigurationDTO emailConfigurationDTO);

    ResponseEntity<ApiResponseDTO<String>> updateConfig(String action, boolean isEnabled);

    ResponseEntity<ApiResponseDTO<List<EmailConfigurationDTO>>> getAllConfigurations();

    ResponseEntity<ApiResponseDTO<String>> updateEmailTemplate(EmailTemplateDTO emailTemplateDTO);
}
