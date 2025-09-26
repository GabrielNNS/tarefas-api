package com.gabriel.tasks.api.dto;

import com.gabriel.tasks.api.model.TaskStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TaskResponse(
        Long id,
        String name,
        String description,
        TaskStatus status,
        LocalDateTime createDate) {
}
