package com.taskapi.tasker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import graphql.scalars.ExtendedScalars; 
@Configuration 

public class GraphQlConfig { 

 
	 

    /** 

     * Register extended scalars (Date, DateTime, etc.). 

     * Add more scalars here as the schema grows. 

     */ 

    @Bean 

    public RuntimeWiringConfigurer runtimeWiringConfigurer() { 

        return wiringBuilder -> wiringBuilder 

                .scalar(ExtendedScalars.Date) 

                .scalar(ExtendedScalars.DateTime); 

    } 

} 