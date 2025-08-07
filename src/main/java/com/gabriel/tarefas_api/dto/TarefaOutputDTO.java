package com.gabriel.tarefas_api.dto;

public record TarefaOutputDTO(
        Long id,
        String nome,
        String descricao,
        Boolean concluida) {
}
