package com.gabriel.tarefas_api.mapper;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;
import com.gabriel.tarefas_api.model.Tarefa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TarefaMapper {

    public Tarefa toEntity(TarefaRequest dto){
        return new Tarefa(dto.nome(), dto.descricao());
    }

    public TarefaResponse toTarefaOutputDTO(Tarefa tarefa){
        return new TarefaResponse(tarefa.getId(),
                                    tarefa.getNome(),
                                    tarefa.getDescricao(),
                                    tarefa.getConcluida(),
                                    tarefa.getDataCriacao());
    }

    public List<TarefaResponse> tarefaOutputDTOList(List<Tarefa> tarefas){
        return tarefas.stream()
                      .map(this::toTarefaOutputDTO)
                      .toList();
    }
}
