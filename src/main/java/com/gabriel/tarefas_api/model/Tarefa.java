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

    private String name;
    private String description;
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private TarefaStatus status;

    public Tarefa(TarefaBuilder tarefaBuilder) {
        this.name = tarefaBuilder.getName();
        this.description = tarefaBuilder.getDescription();
        this.status = TO_DO;
        this.createDate = LocalDateTime.now();
    }
}
