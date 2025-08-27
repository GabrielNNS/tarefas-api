package com.gabriel.tarefas_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.gabriel.tarefas_api.model.TarefaStatus.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    private TarefaStatus status;

    public Tarefa(TarefaBuilder tarefaBuilder) {
        this.nome = tarefaBuilder.getNome();
        this.descricao = tarefaBuilder.getDescricao();
        this.status = TO_DO;
        this.dataCriacao = LocalDateTime.now();
    }
}
