package com.gabriel.tarefas_api.mapper;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;
import com.gabriel.tarefas_api.model.Tarefa;
import com.gabriel.tarefas_api.model.TarefaBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TarefaMapper {

    public Tarefa toEntity(TarefaRequest dto) {
        return new TarefaBuilder().nome(dto.nome())
                .descricao(dto.descricao())
                .build();
    }

    public TarefaResponse toTarefaResponse(Tarefa tarefa) {
        return new TarefaResponse(tarefa.getId(),
                tarefa.getNome(),
                tarefa.getDescricao(),
                tarefa.getStatus(),
                tarefa.getDataCriacao());
    }

    public List<TarefaResponse> tarefaResponseList(List<Tarefa> tarefas) {
        return tarefas.stream()
                .map(this::toTarefaResponse)
                .toList();
    }
}
