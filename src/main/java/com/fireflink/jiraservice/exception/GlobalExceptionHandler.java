package com.fireflink.jiraservice.exception;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.entity.User;
import com.fireflink.jiraservice.utils.ResponseUtil;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponseDTO<User> userNotFoundExceptionHandler(UserNotFoundException exception){

        return ApiResponseDTO.<User>builder().message(exception.getMessage()).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).data(null).build();

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> userInputValidationHandler(MethodArgumentNotValidException exception){

        Map<String,String> errorMap=new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return errorMap;

    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResponseDTO<User> defaultExceptionHandler(RuntimeException exception){

        return ApiResponseDTO.<User>builder().message(exception.getMessage()).httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).data(null).build();

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        ex.getConstraintViolations().forEach(error -> {
            atomicInteger.getAndIncrement();
            String errorNumber = "Error " + atomicInteger.get();
            String errorMessage = error.getMessage();
            errors.put(errorNumber, errorMessage);
        });

        return  ResponseUtil.getBadRequestResponse(errors,"Parameter violation warning");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NoHandlerFoundException ex) {
        if (ex.getRequestURL().startsWith("/v3/api-docs")) {
            return ResponseEntity.ok().build(); // Return empty response for Swagger API docs
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
    }
}
