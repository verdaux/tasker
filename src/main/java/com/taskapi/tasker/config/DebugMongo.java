package com.taskapi.tasker.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebugMongo {

    @Bean
    CommandLineRunner printMongoUri() {
        return args -> {
            System.out.println("MONGO URI: " + System.getenv("MONGO_URI"));
        };
    }
}