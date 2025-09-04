package com.gabriel.tarefas_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.gabriel.tarefas_api.model.TarefaStatus.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TarefaStatus status = TO_DO;
}
