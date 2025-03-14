package com.fireflink.jiraservice.utils;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    private ResponseUtil(){}

    private static <T> ApiResponseDTO<T> initApiResponse() {
        return new ApiResponseDTO<>();
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> getCreatedResponse(T response,String message) {
        ApiResponseDTO<T> apiResponse = initApiResponse();
        apiResponse.setHttpStatus(HttpStatus.CREATED);
        apiResponse.setMessage(message);
        apiResponse.setData(response);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> getBadRequestResponse(T response,String message) {
        ApiResponseDTO<T> apiResponse = initApiResponse();
        apiResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setMessage(message);
        apiResponse.setData(response);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> getOkResponse(T response,String message) {
        ApiResponseDTO<T> apiResponse = initApiResponse();
        apiResponse.setHttpStatus(HttpStatus.OK);
        apiResponse.setMessage(message);
        apiResponse.setData(response);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> getConflictResponse(T response,String message) {
        ApiResponseDTO<T> apiResponse = initApiResponse();
        apiResponse.setHttpStatus(HttpStatus.CONFLICT);
        apiResponse.setMessage(message);
        apiResponse.setData(response);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }

    public static <T> ResponseEntity<ApiResponseDTO<T>> getNoContentResponse(T response,String message) {
        ApiResponseDTO<T> apiResponse = initApiResponse();
        apiResponse.setHttpStatus(HttpStatus.NO_CONTENT);
        apiResponse.setMessage(message);
        apiResponse.setData(response);
        return ResponseEntity.status(apiResponse.getHttpStatus()).body(apiResponse);
    }
}
