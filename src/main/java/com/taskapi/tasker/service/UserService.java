package com.taskapi.tasker.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskapi.tasker.entity.User;
import com.taskapi.tasker.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service 

@RequiredArgsConstructor 

public class UserService implements UserDetailsService { 

 

    private final UserRepository userRepo; 

 

    @Override 

    public User loadUserByUsername(String username) throws UsernameNotFoundException { 

        return userRepo.findByUsername(username) 

                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)); 

    } 

 

    /** Convenience alias used by resolvers */ 

    public User loadByUsername(String username) { 

        return loadUserByUsername(username); 

    } 

 

    public User findById(String id) { 

        return userRepo.findById(id) 

                .orElseThrow(() -> new RuntimeException("User not found: " + id)); 

    } 

} 
