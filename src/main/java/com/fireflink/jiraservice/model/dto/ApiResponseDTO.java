package com.fireflink.jiraservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO<T> {

    private String message;
    private HttpStatus httpStatus;
    private T data;

}
