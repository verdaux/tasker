package com.taskapi.tasker.entity;

public record UpdateTaskInput(String title, String description, TaskPriority priority, java.time.LocalDate dueDate) {}