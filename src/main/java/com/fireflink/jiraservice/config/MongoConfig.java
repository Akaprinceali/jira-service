package com.fireflink.jiraservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MongoConfig {

   @Bean
   public MongoClient mongoClient(){
       return MongoClients.create("mongodb://localhost:27017/");
   }

   @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient){
        return new MongoTemplate(mongoClient,"jiraservice");
    }

    @Bean
    public ModelMapper modelMapper(){return new ModelMapper();}

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }
}
