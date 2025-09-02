package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;
import com.gabriel.tarefas_api.mapper.TarefaMapper;
import com.gabriel.tarefas_api.model.Tarefa;
import com.gabriel.tarefas_api.model.TarefaStatus;
import com.gabriel.tarefas_api.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService implements ITarefaService {

    private final TarefaMapper mapper;
    private final TarefaRepository repository;

    public TarefaService(TarefaMapper mapper, TarefaRepository repository){
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public TarefaResponse create(TarefaRequest dto){
        Tarefa tarefa = mapper.toEntity(dto);
        repository.save(tarefa);
        return mapper.toTarefaResponse(tarefa);
    }

    @Override
    public List<TarefaResponse> listAll(){
        return mapper.tarefaResponseList(repository.findAll());
    }

    @Override
    public TarefaResponse findById(Long id){
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));
        return mapper.toTarefaResponse(tarefa);
    }

    @Override
    public TarefaResponse update(Long id, TarefaRequest dto){
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));
        tarefa.setName(dto.name());
        tarefa.setDescription(dto.description());
        repository.save(tarefa);
        return mapper.toTarefaResponse(tarefa);
    }

    @Override
    public void delete(Long id){
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não localizada!");
        }
        repository.deleteById(id);
    }

    @Override
    public TarefaResponse alterStatus(Long id, TarefaStatus newStatus){
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));

        tarefa.setStatus(newStatus);
        repository.save(tarefa);
        return mapper.toTarefaResponse(tarefa);
    }
}
