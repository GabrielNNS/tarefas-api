package com.gabriel.tarefas_api.dto;

import com.gabriel.tarefas_api.model.TarefaStatus;

import java.time.LocalDateTime;

public record TarefaResponse(
        Long id,
        String nome,
        String descricao,
        TarefaStatus status,
        LocalDateTime dataCriacao) {
}
