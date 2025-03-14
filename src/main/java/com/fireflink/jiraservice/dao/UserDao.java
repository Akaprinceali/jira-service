package com.fireflink.jiraservice.dao;

import com.fireflink.jiraservice.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {


    String saveUser(User user);

    Optional<User> findByUsername(String username);

    List<User> findAllUser();

    Optional<User> findByEmail(String email);

    Optional<User> findByPasswordResetToken(String token);
}
