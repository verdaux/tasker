package com.taskapi.tasker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;


@Configuration 

//@EnableMongoAuditing 

public class MongoConfig { 
	 private static final String MONGO_URI = "mongodb+srv://tasker_user:a2RgHJWz7b3C68PC@task.kzvmvis.mongodb.net/tasker_audit?retryWrites=true&w=majority&appName=Task&authSource=admin";

	    @Bean
	     MongoClient mongoClient() {
	        return MongoClients.create(MONGO_URI);
	    }

	    @Bean
	     MongoTemplate mongoTemplate() {
	        return new MongoTemplate(mongoClient(), "tasker_audit");
	    }
	
   // Spring Boot auto-configures the MongoClient from application.yml. 

   // Repository scanning is handled by @SpringBootApplication on the main class. 

} 