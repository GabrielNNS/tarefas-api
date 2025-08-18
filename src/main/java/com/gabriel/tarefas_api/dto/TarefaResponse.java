package com.gabriel.tarefas_api.dto;

import java.time.LocalDateTime;

public record TarefaResponse(
        Long id,
        String nome,
        String descricao,
        Boolean concluida,
        LocalDateTime dataCriacao) {
}
