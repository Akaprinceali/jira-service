package com.fireflink.jiraservice.dao.impl;

import com.fireflink.jiraservice.dao.UserDao;
import com.fireflink.jiraservice.model.entity.User;
import com.fireflink.jiraservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public String saveUser(User user) {

         return userRepository.save(user).getUserId();

    }



    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByPasswordResetToken(String token) {
        return userRepository.findByPasswordResetToken(token);
    }


}
