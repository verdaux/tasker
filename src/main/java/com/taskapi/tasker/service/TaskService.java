package com.taskapi.tasker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taskapi.tasker.entity.Task;
import com.taskapi.tasker.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task createTask(Task task) {
        return repository.save(task);
    }
}
