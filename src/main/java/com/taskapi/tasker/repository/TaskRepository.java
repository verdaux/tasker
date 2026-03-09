package com.taskapi.tasker.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskapi.tasker.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
}
