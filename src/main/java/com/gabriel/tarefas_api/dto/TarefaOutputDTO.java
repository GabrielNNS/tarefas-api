package com.gabriel.tarefas_api.dto;

import java.time.LocalDateTime;

public record TarefaOutputDTO(
        Long id,
        String nome,
        String descricao,
        Boolean concluida,
        LocalDateTime dataCriacao) {
}
