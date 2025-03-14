package com.fireflink.jiraservice.utils;

import com.fireflink.jiraservice.model.entity.User;
import org.springframework.beans.BeanUtils;

public class UserUtils {

    private UserUtils(){

    }

    public static User convertToUser(Object userDTO){

        User user=User.builder().build();

        BeanUtils.copyProperties(userDTO,user);

        return user;
    }

}
