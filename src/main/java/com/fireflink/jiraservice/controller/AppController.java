package com.fireflink.jiraservice.controller;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.FAQResponse;
import com.fireflink.jiraservice.model.dto.UserLoginDTO;
import com.fireflink.jiraservice.model.dto.UserSignupDTO;
import com.fireflink.jiraservice.service.FAQService;
import com.fireflink.jiraservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
@Tag(name = "App Controller", description = "This section is for public management, any user without authentication can access this...")
public class AppController {

    private final UserService userService;
    private final FAQService faqService;

    @PostMapping("/user-signup")
    @Operation(summary = "Save a user", description = "Saves the user information inside database", responses = {
            @ApiResponse(responseCode = "202", description = "Data saved successfully"),
            @ApiResponse(responseCode = "500", description = "User already exists with this username"),
    })
    public ApiResponseDTO<String> saveUser(@RequestBody UserSignupDTO userSignupDTO) {
        return userService.saveUser(userSignupDTO);
    }

    @PostMapping("/user-login")
    @Operation(summary = "User Login", description = "Authenticate the user", responses = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "500", description = "User doesn't exist with this username")
    })
    public ApiResponseDTO<String> userLogin(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.userLogin(userLoginDTO);
    }

    @GetMapping("/faq-all")
    public ApiResponseDTO<List<FAQResponse>> findAllFAQ(){
        return faqService.findAllFAQ();
    }

    @GetMapping("/verify-reset-token")
    @Operation(summary = "Verify password reset token", description = "Verify if the reset password token is valid", responses = {
            @ApiResponse(responseCode = "200", description = "Token verified successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token")
    })
    public ApiResponseDTO<String> verifyResetToken(@RequestParam String token) {
        return userService.verifyPasswordResetToken(token);
    }
}
