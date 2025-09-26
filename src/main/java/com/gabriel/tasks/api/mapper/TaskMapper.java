package com.gabriel.tasks.api.mapper;

import com.gabriel.tasks.api.dto.TaskRequest;
import com.gabriel.tasks.api.dto.TaskResponse;
import com.gabriel.tasks.api.model.Task;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequest dto) {
        return Task.builder()
                .name(dto.name())
                .description(dto.description())
                .build();
    }

    public TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .createDate(task.getCreateDate())
                .build();
    }

    public List<TaskResponse> taskResponseList(List<Task> tasks) {
        return tasks.stream()
                .map(this::toTaskResponse)
                .toList();
    }
}
