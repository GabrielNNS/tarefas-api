package com.gabriel.tasks.api.utils;

import com.gabriel.tasks.api.dto.TaskRequest;
import com.gabriel.tasks.api.dto.TaskResponse;
import com.gabriel.tasks.api.dto.TaskUpdateRequest;
import com.gabriel.tasks.api.model.Task;
import com.gabriel.tasks.api.model.TaskStatus;

import java.time.LocalDateTime;

public class TaskFactory {

    private static final LocalDateTime FIXED_DATE = LocalDateTime.of(2025, 1, 1, 12, 0);

    public static Task buildTask(String name, String desc) {
        return Task.builder()
                .name(name)
                .description(desc)
                .createDate(FIXED_DATE)
                .build();
    }

    public static Task buildTask(Long id, String name, String desc) {
        return Task.builder()
                .id(id)
                .name(name)
                .description(desc)
                .createDate(FIXED_DATE)
                .build();
    }

    public static Task buildTask(String name, String desc, TaskStatus status) {
        return Task.builder()
                .name(name)
                .description(desc)
                .status(status)
                .createDate(FIXED_DATE)
                .build();
    }

    public static TaskResponse buildTaskResponse(Long id, String name, String desc) {
        return TaskResponse.builder()
                .id(id)
                .name(name)
                .description(desc)
                .createDate(FIXED_DATE)
                .build();
    }

    public static TaskResponse buildTaskResponse(Long id, String name, String desc, TaskStatus status) {
        return TaskResponse.builder()
                .id(id)
                .name(name)
                .description(desc)
                .createDate(FIXED_DATE)
                .status(status)
                .build();
    }

    public static TaskRequest buildTaskRequest(String name, String desc) {
        return new TaskRequest(name, desc);
    }


    public static TaskUpdateRequest buildTaskUpdateRequest(String name, String desc) {
        return new TaskUpdateRequest(name, desc);
    }
}
