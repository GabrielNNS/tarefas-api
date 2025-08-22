package com.gabriel.tarefas_api.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String name;
    private Integer status;

    public ErrorResponse(String name, Integer status) {
        this.name = name;
        this.status = status;
    }
}
