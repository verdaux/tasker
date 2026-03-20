package com.taskapi.tasker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.taskapi.tasker"})
@EnableJpaRepositories(basePackages = "com.taskapi.tasker.repository") 
@EnableMongoRepositories(basePackages = "com.taskapi.tasker.audit")
public class TaskerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskerApplication.class, args);
	}

}
