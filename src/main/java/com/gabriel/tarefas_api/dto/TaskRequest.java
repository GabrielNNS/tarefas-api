package com.gabriel.tarefas_api.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
        @NotBlank
        String name,
        @NotBlank
        String description) {
}
