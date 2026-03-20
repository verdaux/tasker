package com.taskapi.tasker.resolver;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.taskapi.tasker.entity.CreateTaskInput;
import com.taskapi.tasker.entity.Task;
import com.taskapi.tasker.entity.TaskStatus;
import com.taskapi.tasker.entity.UpdateTaskInput;
import com.taskapi.tasker.service.TaskService;
import com.taskapi.tasker.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller 

@RequiredArgsConstructor 

public class TaskResolver { 

 

    private final TaskService taskService; 

    private final UserService userService; 

 

    @QueryMapping 

    @PreAuthorize("isAuthenticated()") 

    public List<Task> myTasks(@AuthenticationPrincipal UserDetails principal) { 

        var user = userService.loadByUsername(principal.getUsername()); 

        return taskService.getTasksByOwner(user); 

    } 

 

    @QueryMapping 

    @PreAuthorize("isAuthenticated()") 

    public Task task(@Argument String id) { 

        return taskService.getById(id); 

    } 

 

    @MutationMapping 

    @PreAuthorize("isAuthenticated()") 

    public Task createTask(@Argument CreateTaskInput input, 

                           @AuthenticationPrincipal UserDetails principal) { 

        var user = userService.loadByUsername(principal.getUsername()); 

        return taskService.create(input, user); 

    } 

 

    @MutationMapping 

    @PreAuthorize("isAuthenticated()") 

    public Task updateTask(@Argument String id, 

                           @Argument UpdateTaskInput input, 

                           @AuthenticationPrincipal UserDetails principal) { 

        var user = userService.loadByUsername(principal.getUsername()); 

        return taskService.update(id, input, user); 

    } 

 

    @MutationMapping 

    @PreAuthorize("isAuthenticated()") 

    public Task updateTaskStatus(@Argument String id, 

                                 @Argument TaskStatus status, 

                                 @AuthenticationPrincipal UserDetails principal) { 

        var user = userService.loadByUsername(principal.getUsername()); 

        return taskService.updateStatus(id, status, user); 

    } 

 

    @MutationMapping 

    @PreAuthorize("isAuthenticated()") 

    public Boolean deleteTask(@Argument String id, 

                              @AuthenticationPrincipal UserDetails principal) { 

        var user = userService.loadByUsername(principal.getUsername()); 

        return taskService.delete(id, user); 

    } 

} 
