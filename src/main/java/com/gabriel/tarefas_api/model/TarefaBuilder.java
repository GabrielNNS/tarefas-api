package com.gabriel.tarefas_api.model;

import lombok.Getter;

@Getter
public class TarefaBuilder {

    private String nome;
    private String descricao;

    public TarefaBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public TarefaBuilder descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Tarefa build() {
        return new Tarefa(this);
    }
}
