package com.gabriel.tarefas_api.mapper;

import com.gabriel.tarefas_api.dto.TarefaInputDTO;
import com.gabriel.tarefas_api.dto.TarefaOutputDTO;
import com.gabriel.tarefas_api.model.Tarefa;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TarefaMapper {

    public Tarefa toEntity(TarefaInputDTO dto){
        return new Tarefa(dto.nome(), dto.descricao());
    }

    public TarefaOutputDTO toTarefaOutputDTO(Tarefa tarefa){
        return new TarefaOutputDTO(tarefa.getId(),
                                    tarefa.getNome(),
                                    tarefa.getDescricao(),
                                    tarefa.getConcluida(),
                                    tarefa.getDataCriacao());
    }

    public List<TarefaOutputDTO> tarefaOutputDTOList(List<Tarefa> tarefas){
        return tarefas.stream()
                      .map(this::toTarefaOutputDTO)
                      .toList();
    }
}
