package com.taskapi.tasker.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.taskapi.tasker.dto.CreateTaskRequest;
import com.taskapi.tasker.dto.TaskResponse;
import com.taskapi.tasker.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper
{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    Task toEntity(CreateTaskRequest request);

    TaskResponse toResponse(Task task);
}
