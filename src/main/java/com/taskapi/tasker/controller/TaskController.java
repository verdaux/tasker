package com.taskapi.tasker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taskapi.tasker.dto.CreateTaskRequest;
import com.taskapi.tasker.dto.TaskResponse;
import com.taskapi.tasker.entity.Task;
import com.taskapi.tasker.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }
    @PostMapping
    public TaskResponse createTask(@RequestBody CreateTaskRequest request) {
        return service.createTask(request);
    }

    @GetMapping
    public List<TaskResponse> getTasks() {
        return service.getAllTasks();
    }
}
