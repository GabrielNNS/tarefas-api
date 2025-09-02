package com.gabriel.tarefas_api.model;

import lombok.Getter;

@Getter
public class TarefaBuilder {

    private String name;
    private String description;

    public TarefaBuilder name(String name) {
        this.name = name;
        return this;
    }

    public TarefaBuilder description(String description) {
        this.description = description;
        return this;
    }

    public Tarefa build() {
        return new Tarefa(this);
    }
}
