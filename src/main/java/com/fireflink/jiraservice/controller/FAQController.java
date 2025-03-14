package com.fireflink.jiraservice.controller;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.FAQResponse;
import com.fireflink.jiraservice.model.dto.SaveFAQDTO;
import com.fireflink.jiraservice.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FAQController {

    private final FAQService faqService;

    @PostMapping("/save-faq")
    public ApiResponseDTO<String> saveFAQ(@RequestBody SaveFAQDTO saveFAQDTO,@RequestParam String email){
        return faqService.saveFAQ(saveFAQDTO,email);
    }

    @DeleteMapping("/all")
    public ApiResponseDTO<String> deleteAllFAQ(){
        return faqService.deleteAllFAQ();
    }

    @DeleteMapping("/delete-id")
    public ApiResponseDTO<String> deleteById(@RequestParam String faqId){
        return faqService.deleteById(faqId);
    }




}
