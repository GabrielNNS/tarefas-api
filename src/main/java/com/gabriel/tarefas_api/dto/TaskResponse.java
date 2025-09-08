package com.gabriel.tarefas_api.dto;

import com.gabriel.tarefas_api.model.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String name,
        String description,
        TaskStatus status,
        LocalDateTime createDate) {
}
