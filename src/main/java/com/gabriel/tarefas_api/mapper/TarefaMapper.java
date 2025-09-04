package com.gabriel.tarefas_api.mapper;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;
import com.gabriel.tarefas_api.model.Tarefa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TarefaMapper {

    public Tarefa toEntity(TarefaRequest dto) {
        return Tarefa.builder()
                .name(dto.name())
                .description(dto.description())
                .build();
    }

    public TarefaResponse toTarefaResponse(Tarefa tarefa) {
        return new TarefaResponse(tarefa.getId(),
                tarefa.getName(),
                tarefa.getDescription(),
                tarefa.getStatus(),
                tarefa.getCreateDate());
    }

    public List<TarefaResponse> tarefaResponseList(List<Tarefa> tarefas) {
        return tarefas.stream()
                .map(this::toTarefaResponse)
                .toList();
    }
}
