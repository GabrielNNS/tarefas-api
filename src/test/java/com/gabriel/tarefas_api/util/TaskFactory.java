package com.gabriel.tarefas_api.util;

import com.gabriel.tarefas_api.dto.TaskRequest;
import com.gabriel.tarefas_api.dto.TaskResponse;
import com.gabriel.tarefas_api.dto.TaskUpdateRequest;
import com.gabriel.tarefas_api.model.Task;
import com.gabriel.tarefas_api.model.TaskStatus;

import java.time.LocalDateTime;

public class TaskFactory {

    private final LocalDateTime FIXED_DATE = LocalDateTime.of(2025, 1, 1, 12, 0);

    public Task buildTask(String name, String desc) {
        return Task.builder()
                .name(name)
                .description(desc)
                .createDate(FIXED_DATE)
                .build();
    }

    public Task buildTask(Long id, String name, String desc) {
        return Task.builder()
                .id(id)
                .name(name)
                .description(desc)
                .createDate(FIXED_DATE)
                .build();
    }

    public TaskResponse buildTaskResponse(Long id, String name, String desc) {
        return TaskResponse.builder()
                .id(id)
                .name(name)
                .description(desc)
                .createDate(FIXED_DATE)
                .build();
    }

    public TaskResponse buildTaskResponse(Long id, String name, String desc, TaskStatus status) {
        return TaskResponse.builder()
                .id(id)
                .name(name)
                .description(desc)
                .createDate(FIXED_DATE)
                .status(status)
                .build();
    }

    public TaskRequest buildTaskRequest(String name, String desc) {
        return new TaskRequest(name, desc);
    }


    public TaskUpdateRequest buildTaskUpdateRequest(String name, String desc) {
        return new TaskUpdateRequest(name, desc);
    }
}
