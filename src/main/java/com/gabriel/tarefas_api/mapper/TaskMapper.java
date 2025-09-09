package com.gabriel.tarefas_api.mapper;

import com.gabriel.tarefas_api.dto.TaskRequest;
import com.gabriel.tarefas_api.dto.TaskResponse;
import com.gabriel.tarefas_api.model.Task;
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

    public TaskResponse toTarefaResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .status(task.getStatus())
                .createDate(task.getCreateDate())
                .build();
    }

    public List<TaskResponse> tarefaResponseList(List<Task> tasks) {
        return tasks.stream()
                .map(this::toTarefaResponse)
                .toList();
    }
}
