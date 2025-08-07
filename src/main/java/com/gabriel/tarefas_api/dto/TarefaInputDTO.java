package com.gabriel.tarefas_api.dto;

import jakarta.validation.constraints.NotBlank;

public record TarefaInputDTO(
        @NotBlank
        String nome,
        @NotBlank
        String descricao) {
}
