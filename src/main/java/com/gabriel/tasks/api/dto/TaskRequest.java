package com.gabriel.tasks.api.dto;

import jakarta.validation.constraints.NotBlank;

public record TaskRequest(
        @NotBlank
        String name,
        @NotBlank
        String description) {
}
