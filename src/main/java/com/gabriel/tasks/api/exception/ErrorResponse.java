package com.gabriel.tasks.api.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private LocalDateTime timestamp;
    private Integer errorCode;
    private String errorTitle;
    private String errorMessage;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, Integer errorCode, String errorTitle, String errorMessage, String path) {
        this.timestamp = timestamp;
        this.errorCode = errorCode;
        this.errorTitle = errorTitle;
        this.errorMessage = errorMessage;
        this.path = path;
    }
}
