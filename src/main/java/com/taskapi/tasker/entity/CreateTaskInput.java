package com.taskapi.tasker.entity;

public record CreateTaskInput(String title, String description, TaskPriority priority, java.time.LocalDate dueDate) {}