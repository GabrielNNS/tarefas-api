package com.gabriel.tarefas_api.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String errorMessage;
    private Integer status;

    public ErrorResponse(String errorMessage, Integer status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }
}
