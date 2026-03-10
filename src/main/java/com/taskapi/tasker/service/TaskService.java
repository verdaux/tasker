package com.taskapi.tasker.service;

import org.springframework.stereotype.Service;

import com.taskapi.tasker.dto.CreateTaskRequest;
import com.taskapi.tasker.dto.TaskResponse;
import com.taskapi.tasker.entity.Task;
import com.taskapi.tasker.mapper.TaskMapper;
import com.taskapi.tasker.repository.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TaskResponse createTask(CreateTaskRequest request) {

        Task task = mapper.toEntity(request);

        Task saved = repository.save(task);

        return mapper.toResponse(saved);
    }

    public List<TaskResponse> getAllTasks() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
