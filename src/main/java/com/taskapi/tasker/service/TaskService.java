package com.taskapi.tasker.service;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.taskapi.tasker.audit.AuditService;
import com.taskapi.tasker.entity.CreateTaskInput;
import com.taskapi.tasker.entity.Task;
import com.taskapi.tasker.entity.TaskPriority;
import com.taskapi.tasker.entity.TaskStatus;
import com.taskapi.tasker.entity.UpdateTaskInput;
import com.taskapi.tasker.entity.User;
import com.taskapi.tasker.repository.TaskRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
	 private final TaskRepository taskRepo; 

	    private final AuditService auditService; 

	 

	    public List<Task> getAllTasks() { 

	        return taskRepo.findAll(); 

	    } 

	 

	    public List<Task> getTasksByOwner(User owner) { 

	        return taskRepo.findByOwnerId(owner.getId()); 

	    } 

	 

	    public Task getById(String id) { 

	        return taskRepo.findById(id) 

	                .orElseThrow(() -> new RuntimeException("Task not found: " + id)); 

	    } 

	 

	    @Transactional 

	    public Task create(CreateTaskInput input, User owner) { 

	        var task = Task.builder() 

	                .title(input.title()) 

	                .description(input.description()) 

	                .priority(input.priority() != null ? input.priority() : TaskPriority.MEDIUM) 

	                .owner(owner) 

	                .build(); 

	        var saved = taskRepo.save(task); 

	        auditService.log("CREATE", "task", saved.getId(), owner.getId()); 

	        return saved; 

	    } 

	 

	    @Transactional 

	    public Task update(String id, UpdateTaskInput input, User caller) { 

	        var task = getById(id); 

	        assertOwner(task, caller); 

	        if (input.title()       != null) task.setTitle(input.title()); 

	        if (input.description() != null) task.setDescription(input.description()); 

	        if (input.priority()    != null) task.setPriority(input.priority()); 

	        if (input.dueDate()     != null) task.setDueDate(input.dueDate()); 

	        var saved = taskRepo.save(task); 

	        auditService.log("UPDATE", "task", saved.getId(), caller.getId()); 

	        return saved; 

	    } 

	 

	    @Transactional 

	    public Task updateStatus(String id, TaskStatus status, User caller) { 

	        var task = getById(id); 

	        assertOwner(task, caller); 

	        task.setStatus(status); 

	        return taskRepo.save(task); 

	    } 

	 

	    @Transactional 

	    public boolean delete(String id, User caller) { 

	        var task = getById(id); 

	        assertOwner(task, caller); 

	        taskRepo.delete(task); 

	        auditService.log("DELETE", "task", id, caller.getId()); 

	        return true; 

	    } 

	 

	    private void assertOwner(Task task, User caller) { 

	        if (!task.getOwner().getId().equals(caller.getId())) { 

	            throw new AccessDeniedException("You do not own this task"); 

	        } 

	    } 

	} 
