package com.fireflink.jiraservice.service.impl;

import com.fireflink.jiraservice.dao.EmailConfigurationDao;
import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.EmailConfigurationDTO;
import com.fireflink.jiraservice.model.dto.EmailTemplateDTO;
import com.fireflink.jiraservice.model.entity.EmailConfiguration;
import com.fireflink.jiraservice.service.EmailConfigurationService;
import com.fireflink.jiraservice.utils.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailConfigurationServiceImpl implements EmailConfigurationService {

    private final EmailConfigurationDao emailConfigurationDao;

    @Override
    public ResponseEntity<ApiResponseDTO<String>> saveConfig(EmailConfigurationDTO emailConfigurationDTO) {

        EmailConfiguration emailConfiguration= EmailConfiguration.builder().build();

        BeanUtils.copyProperties(emailConfigurationDTO,emailConfiguration);

        emailConfiguration.setEnabled(true);

        return ResponseUtil.getCreatedResponse(emailConfigurationDao.saveConfig(emailConfiguration),"Email Configuration data saved successfully");
    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> updateConfig(String action, boolean isEnabled) {

        EmailConfiguration emailConfiguration=emailConfigurationDao.getConfig(action);
        emailConfiguration.setEnabled(isEnabled);

        emailConfigurationDao.saveConfig(emailConfiguration);

        return ResponseUtil.getOkResponse(null,"Email configuration updated successfully");
    }

    @Override
    public ResponseEntity<ApiResponseDTO<List<EmailConfigurationDTO>>> getAllConfigurations() {

        List<EmailConfiguration> allConfigurations=emailConfigurationDao.getAllConfigurations();

        List<EmailConfigurationDTO> emailConfigurationDTOS=new ArrayList<>();

        if(!allConfigurations.isEmpty()){
            for (EmailConfiguration emailConfiguration:allConfigurations){
                EmailConfigurationDTO emailConfigurationDTO= EmailConfigurationDTO.builder().build();
                BeanUtils.copyProperties(emailConfiguration,emailConfigurationDTO);
                emailConfigurationDTOS.add(emailConfigurationDTO);
            }

            return ResponseUtil.getOkResponse(emailConfigurationDTOS,"Email configuration fetched successfully");
        }

        return ResponseUtil.getNoContentResponse(null,"Email configuration not exists");
    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> updateEmailTemplate(EmailTemplateDTO emailTemplateDTO) {
        EmailConfiguration emailConfiguration = emailConfigurationDao.getConfig(emailTemplateDTO.getAction());
        if(!Objects.isNull(emailConfiguration)) {
            emailConfiguration.setSubject(emailTemplateDTO.getSubject());
            emailConfiguration.setBody(emailTemplateDTO.getBody());
            emailConfigurationDao.saveConfig(emailConfiguration);
            return ResponseUtil.getOkResponse(null,"Email template updated successfully");

        }
        return ResponseUtil.getNoContentResponse(null,"Action doesn't existed");

    }


}
