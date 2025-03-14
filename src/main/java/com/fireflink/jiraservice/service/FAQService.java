package com.fireflink.jiraservice.service;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.FAQResponse;
import com.fireflink.jiraservice.model.dto.SaveFAQDTO;

import java.util.List;

public interface FAQService {


    ApiResponseDTO<String> saveFAQ(SaveFAQDTO saveFAQDTO, String email);

    ApiResponseDTO<List<FAQResponse>> findAllFAQ();

    ApiResponseDTO<String> deleteAllFAQ();

    ApiResponseDTO<String> deleteById(String faqId);
}
