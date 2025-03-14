package com.fireflink.jiraservice.controller;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.EmailConfigurationDTO;
import com.fireflink.jiraservice.model.dto.EmailTemplateDTO;
import com.fireflink.jiraservice.service.EmailConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailConfigurationController {

    private final EmailConfigurationService emailConfigurationService;

    @PostMapping("/save-config")
    public ResponseEntity<ApiResponseDTO<String>> saveConfig(@RequestBody EmailConfigurationDTO emailConfigurationDTO){

        return emailConfigurationService.saveConfig(emailConfigurationDTO);
    }


    @PatchMapping("/update-config")
    public ResponseEntity<ApiResponseDTO<String>> updateConfig(@RequestParam String action,@RequestParam boolean isEnabled){

        return emailConfigurationService.updateConfig(action,isEnabled);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDTO<List<EmailConfigurationDTO>>> getAllConfiguration(){
        return emailConfigurationService.getAllConfigurations();
    }


    @GetMapping("/update-email-template")
    public ResponseEntity<ApiResponseDTO<String>> updateEmailTemplate(@RequestBody EmailTemplateDTO emailTemplateDTO){
        return emailConfigurationService.updateEmailTemplate(emailTemplateDTO);
    }


}
