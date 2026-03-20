package com.taskapi.tasker.auth.dto;

public record AuthResponse( 

        String token, 

        String username, 

        String email 

) {} 
