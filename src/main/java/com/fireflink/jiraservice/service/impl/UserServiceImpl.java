package com.fireflink.jiraservice.service.impl;

import com.fireflink.jiraservice.dao.UserDao;
import com.fireflink.jiraservice.exception.UserNotFoundException;
import com.fireflink.jiraservice.model.dto.ApiResponseDTO;
import com.fireflink.jiraservice.model.dto.UserLoginDTO;
import com.fireflink.jiraservice.model.dto.UserSignupDTO;
import com.fireflink.jiraservice.model.dto.UserUpdateDTO;
import com.fireflink.jiraservice.model.entity.User;
import com.fireflink.jiraservice.service.EmailService;
import com.fireflink.jiraservice.service.SequenceGeneratorService;
import com.fireflink.jiraservice.service.UserService;
import com.fireflink.jiraservice.utils.JwtUtils;
import com.fireflink.jiraservice.utils.ResponseUtil;
import com.fireflink.jiraservice.utils.UserUtils;
import com.mongodb.client.MongoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final MongoTemplate mongoTemplate;
    private final EmailService emailService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponseDTO<String> saveUser(UserSignupDTO userSignupDTO) {

        User user = UserUtils.convertToUser(userSignupDTO);
        user.setCreatedOn(LocalDateTime.now());

        Optional<User> duplicateUser = userDao.findByUsername(user.getUsername());
        if (duplicateUser.isPresent()) {
            return ApiResponseDTO.<String>builder()
                    .message("User already exists, try another username")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .data(null)
                    .build();
        }

        // Generate token for password reset
        String token = emailService.generateToken();
        user.setPasswordResetToken(token);
        user.setTokenGeneratedTime(LocalDateTime.now());
        user.generateId(sequenceGeneratorService);
        user.setPassword(passwordEncoder.encode(userSignupDTO.getPassword()));
        // Save user in MongoDB
        User savedUser = mongoTemplate.insert(user);

        // Send reset password email with token
        emailService.sendResetPasswordEmail(user.getEmail(), token);

        log.info("User signed up successfully. Password reset link sent to email.");

        return ApiResponseDTO.<String>builder()
                .message("User registered successfully. Please check your email for password reset link.")
                .httpStatus(HttpStatus.ACCEPTED)
                .data(savedUser.getUserId())
                .build();
    }

    @Override
    public ApiResponseDTO<String> userLogin(UserLoginDTO userLoginDTO) {

        log.info("password id "+passwordEncoder.encode(userLoginDTO.getPassword()));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword())
        );


        return ApiResponseDTO.<String>builder().message("Login successful").httpStatus(HttpStatus.ACCEPTED).data(jwtUtils.generateToken(userLoginDTO.getUsername())).build();
         }

    @Override
    public ApiResponseDTO<List<User>> findAllUser() {

        List<User> users = userDao.findAllUser();

        if (!users.isEmpty()) {
            return ApiResponseDTO.<List<User>>builder().message("User data fetched successfully").httpStatus(HttpStatus.OK).data(users).build();
        }
        return ApiResponseDTO.<List<User>>builder().message("Users not present").httpStatus(HttpStatus.NO_CONTENT).data(null).build();
    }

    @Override
    public ApiResponseDTO<User> findUserByUsername(String username) {

        Optional<User> user = userDao.findByUsername(username);

        if (user.isPresent()) {
            return ApiResponseDTO.<User>builder().message("User data fetched successfully").httpStatus(HttpStatus.OK).data(user.get()).build();
        }
        throw new UserNotFoundException("User doesn't exist with username " + username);
    }

    @Override
    public ApiResponseDTO<String> verifyPasswordResetToken(String token) {
        Optional<User> userOpt = userDao.findByPasswordResetToken(token);

        if (userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found with token: " + token);
        }

        User user = userOpt.get();

        // Check if token has expired (valid for 5 minutes)
        if (user.getTokenGeneratedTime().plusMinutes(5).isBefore(LocalDateTime.now())) {
            return ApiResponseDTO.<String>builder()
                    .message("Token expired. Please request a new password reset.")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .data(null)
                    .build();
        }

        return ApiResponseDTO.<String>builder()
                .message("Token is valid. You can now reset your password.")
                .httpStatus(HttpStatus.OK)
                .data(null)
                .build();
    }

    @Override
    public ApiResponseDTO<String> resetPassword(String token, String newPassword,String confirmPassword) {

        if(newPassword.equals(confirmPassword)) {
            Optional<User> userOpt = userDao.findByPasswordResetToken(token);

            if (userOpt.isEmpty()) {
                throw new UserNotFoundException("User not found with token: " + token);
            }

            User user = userOpt.get();

            // Check if token has expired
            if (user.getTokenGeneratedTime().plusMinutes(5).isBefore(LocalDateTime.now())) {
                return ApiResponseDTO.<String>builder()
                        .message("Token expired. Please request a new password reset.")
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .data(null)
                        .build();
            }

            // Update password
            user.setPassword(newPassword);
            user.setPasswordResetToken(null);  // Clear token after use
            user.setTokenGeneratedTime(null);  // Clear the token time

            userDao.saveUser(user);

            return ApiResponseDTO.<String>builder()
                    .message("Password successfully reset.")
                    .httpStatus(HttpStatus.OK)
                    .data(null)
                    .build();
        }
            return ApiResponseDTO.<String>builder()
                .message("entered password and confirm password mismatch.")
                .httpStatus(HttpStatus.BAD_REQUEST)
                .data(null)
                .build();
    }

    @Override
    public ResponseEntity<ApiResponseDTO<String>> updateUser(UserUpdateDTO userUpdateDTO, String username) {
        Optional<User> optionalUser = userDao.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Update the user's fields based on userUpdateDTO
            modelMapper.map(userUpdateDTO,user);
            // ... other fields

            userDao.saveUser(user);
            return ResponseUtil.getOkResponse(null,"User updated successfully");
        } else {
            return ResponseUtil.getBadRequestResponse(null,"User not found");
        }
    }
}


