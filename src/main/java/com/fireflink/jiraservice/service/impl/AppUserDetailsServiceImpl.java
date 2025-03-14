package com.fireflink.jiraservice.service.impl;

import com.fireflink.jiraservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.fireflink.jiraservice.model.entity.User appUser=userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("username not found"));

        return new org.springframework.security.core.userdetails.User(

                appUser.getUsername(),
                appUser.getPassword(),
                List.of(new SimpleGrantedAuthority(appUser.getRole().toString()))
        );
    }
}

