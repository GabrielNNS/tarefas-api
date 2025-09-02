package com.gabriel.tarefas_api.dto;

import com.gabriel.tarefas_api.model.TarefaStatus;

import java.time.LocalDateTime;

public record TarefaResponse(
        Long id,
        String name,
        String description,
        TarefaStatus status,
        LocalDateTime createDate) {
}
