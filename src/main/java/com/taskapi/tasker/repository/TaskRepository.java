package com.taskapi.tasker.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskapi.tasker.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
	List<Task> findByOwnerId(String ownerId); 
}
