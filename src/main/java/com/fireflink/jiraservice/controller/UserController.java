package com.fireflink.jiraservice.controller;

import com.fireflink.jiraservice.model.dto.*;
import com.fireflink.jiraservice.model.entity.Topic;
import com.fireflink.jiraservice.model.entity.User;
import com.fireflink.jiraservice.service.FAQService;
import com.fireflink.jiraservice.service.TicketService;
import com.fireflink.jiraservice.service.TopicService;
import com.fireflink.jiraservice.service.UserService;
import com.fireflink.jiraservice.utils.JwtUtils;
import com.fireflink.jiraservice.utils.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "This section is for user management")
@Slf4j
public class UserController {

    private final UserService userService;
    private final TopicService topicService;
    private final FAQService faqService;
    private final TicketService ticketService;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @PostMapping("/find-all-users")
    @Operation(summary = "Fetching all users", description = "Fetches the data of all the users", responses = {
            @ApiResponse(responseCode = "200", description = "Users data fetched successfully"),
            @ApiResponse(responseCode = "500", description = "No users found")
    })
    public ApiResponseDTO<List<User>> findAllUser() {
        return userService.findAllUser();
    }

    @GetMapping("/find-user-username/{username}")
    @Operation(summary = "Fetching specific user", description = "Fetches the data of a specific user", responses = {
            @ApiResponse(responseCode = "200", description = "User data fetched successfully"),
            @ApiResponse(responseCode = "500", description = "User doesn't exist with this username")
    })
    public ApiResponseDTO<User> findUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset user password", description = "Reset the user password using the reset token", responses = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token")
    })
    public ApiResponseDTO<String> resetPassword(@RequestParam String token, @RequestParam String newPassword,@RequestParam String confirmPassword) {
        return userService.resetPassword(token, newPassword,confirmPassword);
    }

    @GetMapping("/all/{pageNo}/{pageSize}")
    public ApiResponseDTO<Page<TopicDTO>> getAllTopicsByPagination(@PathVariable int pageNo, @PathVariable int pageSize){
        return topicService.getAllTopicByPagination(pageNo,pageSize);
    }

    @GetMapping("/search/{content}")
    public ApiResponseDTO<List<Topic>> searchByTopicName(@PathVariable String content){
        return topicService.searchByTopicName(content);
    }

    @GetMapping("/all")
    public ApiResponseDTO<List<FAQResponse>> findAllFAQ(){
        return faqService.findAllFAQ();
    }

    @PostMapping("/create-ticket")
    public ResponseEntity<ApiResponseDTO<String>> createTicket(@RequestPart MultipartFile[] file, @RequestPart String ticketDTOString, @RequestParam  String email) throws IOException {

        log.info("service executed {}","successfully");
        return ticketService.createTicket(file,ticketDTOString,email);

    }

    @PutMapping("/update-ticket")
    public ResponseEntity<ApiResponseDTO<String>> updateTicket(@RequestPart MultipartFile[] file,@RequestPart String ticketDTOString, @RequestParam  String email) throws IOException {

        return ticketService.updateTicket(file,ticketDTOString,email);

    }

    @GetMapping("/get-anything")
    public ResponseEntity<ApiResponseDTO<String>> getAnything() throws IOException {

        String s1="This is for user";

        return ResponseUtil.getOkResponse(s1,s1);

    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseDTO<String>> updateUser(@RequestBody UserUpdateDTO userUpdateDTO,
                                                           @RequestHeader("Authorization") String bearerToken,
                                                           Principal principal) {

        log.info("Update user request received");

        String token = bearerToken.substring(7);
        String usernameFromToken = jwtUtils.getUsername(token);

        // Principal contains the currently logged-in user's information
        String loggedInUsername = principal.getName();

        if (!usernameFromToken.equals(loggedInUsername)) {
            return ResponseUtil.getBadRequestResponse(null,"You can only update your own profile.");

        }

        // Check if the user exists
        UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
        if (userDetails == null) {
            return ResponseUtil.getBadRequestResponse(null,"User not found.");
        }

        return userService.updateUser(userUpdateDTO, usernameFromToken);

    }
}

