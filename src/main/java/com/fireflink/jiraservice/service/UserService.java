package com.fireflink.jiraservice.service;

import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.UserLoginDTO;
import com.fireflink.jiraservice.model.dto.UserSignupDTO;
import com.fireflink.jiraservice.model.dto.UserUpdateDTO;
import com.fireflink.jiraservice.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    ApiResponseDTO<String> saveUser(UserSignupDTO userSignupDTO);

    ApiResponseDTO<String> userLogin(UserLoginDTO userLoginDTO);

    ApiResponseDTO<List<User>> findAllUser();

    ApiResponseDTO<User> findUserByUsername(String username);

    ApiResponseDTO<String> verifyPasswordResetToken(String token);

    ApiResponseDTO<String> resetPassword(String token, String newPassword,String confirmPassword);

    ResponseEntity<ApiResponseDTO<String>> updateUser(UserUpdateDTO userUpdateDTO, String usernameFromToken);
}
