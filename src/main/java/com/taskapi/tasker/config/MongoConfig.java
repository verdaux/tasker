package com.taskapi.tasker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@Configuration 

@EnableMongoAuditing 

public class MongoConfig { 

   // Spring Boot auto-configures the MongoClient from application.yml. 

   // Repository scanning is handled by @SpringBootApplication on the main class. 

} 