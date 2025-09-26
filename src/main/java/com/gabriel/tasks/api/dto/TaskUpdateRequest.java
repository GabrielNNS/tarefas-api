package com.gabriel.tasks.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;

public record TaskUpdateRequest(String name, String description) {

    @AssertTrue(message = "You must provide at least 1 of the fields!")
    @Schema(hidden = true)
    public boolean isALeastOneFieldPresent() {
        return (name != null && !name.isBlank()
                || description != null && !description.isBlank());
    }
}
