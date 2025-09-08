package com.gabriel.tarefas_api.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private Integer errorCode;
    private String error;
    private String errorMessage;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, Integer errorCode, String error, String errorMessage, String path) {
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.error = error;
        this.errorMessage = errorMessage;
        this.path = path;
    }
}
