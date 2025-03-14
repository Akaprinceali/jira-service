package com.fireflink.jiraservice.service.impl;

import com.fireflink.jiraservice.dao.FAQDao;
import com.fireflink.jiraservice.dao.UserDao;
import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.FAQResponse;
import com.fireflink.jiraservice.model.dto.SaveFAQDTO;
import com.fireflink.jiraservice.model.entity.FAQ;
import com.fireflink.jiraservice.model.entity.User;
import com.fireflink.jiraservice.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private final FAQDao faqDao;
    private final UserDao userDao;

    @Override
    public ApiResponseDTO<String> saveFAQ(SaveFAQDTO saveFAQDTO, String email) {

        if(email!=null){
            Optional<User> user = userDao.findByEmail(email);
            if(user.isPresent()){
                FAQ faq= FAQ.builder().build();
                BeanUtils.copyProperties(saveFAQDTO,faq);
                faq.setCreateEntity(user.get().getUsername(),email);

                return ApiResponseDTO.<String>builder().message("FAQ saved successfully").data(faqDao.saveFAQ(faq)).httpStatus(HttpStatus.ACCEPTED).build();
            }
            throw new RuntimeException("User not exist with this email");
        }

        return ApiResponseDTO.<String>builder().message("Make sure to enter email").data(null).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @Override
    public ApiResponseDTO<List<FAQResponse>> findAllFAQ() {
        List<FAQ> faqs= faqDao.findAllFAQ();

        if(!faqs.isEmpty()){
            List<FAQResponse> faqResponses=new ArrayList<>();

            for (FAQ faq:faqs){
                FAQResponse faqContainer= new FAQResponse();
                BeanUtils.copyProperties(faq,faqContainer);
                faqResponses.add(faqContainer);
            }

            return ApiResponseDTO.<List<FAQResponse>>builder().message("FAQs fetch successfully").data(faqResponses).httpStatus(HttpStatus.FOUND).build();

        }

        throw new RuntimeException("FAQ doesn't exist");

    }

    @Override
    public ApiResponseDTO<String> deleteAllFAQ() {
        faqDao.deleteAllFAQ();
        return ApiResponseDTO.<String>builder().message("FAQs deleted successfully").data(null).httpStatus(HttpStatus.OK).build();

    }

    @Override
    public ApiResponseDTO<String> deleteById(String faqId) {

        Optional<FAQ> faq=faqDao.findByFAQId(faqId);

        if(faq.isPresent()) {
            faqDao.deleteById(faqId);
            return ApiResponseDTO.<String>builder().message("FAQ deleted successfully").data(null).httpStatus(HttpStatus.OK).build();
        }
        return ApiResponseDTO.<String>builder().message("FAQ doesn't with this id").data(null).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
