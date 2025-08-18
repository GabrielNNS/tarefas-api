package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;
import com.gabriel.tarefas_api.mapper.TarefaMapper;
import com.gabriel.tarefas_api.model.Tarefa;
import com.gabriel.tarefas_api.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaServiceImpl implements ITarefaService {

    private final TarefaMapper mapper;
    private final TarefaRepository repository;

    public TarefaServiceImpl (TarefaMapper mapper, TarefaRepository repository){
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public TarefaResponse criarTarefa(TarefaRequest dto){
        Tarefa tarefa = mapper.toEntity(dto);
        repository.save(tarefa);
        return mapper.toTarefaOutputDTO(tarefa);
    }

    @Override
    public List<TarefaResponse> listar(){
        return mapper.tarefaOutputDTOList(repository.findAll());
    }

    @Override
    public TarefaResponse buscarPorId(Long id){
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));
        return mapper.toTarefaOutputDTO(tarefa);
    }

    @Override
    public TarefaResponse atualizar(Long id, TarefaRequest dto){
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));
        tarefa.setNome(dto.nome());
        tarefa.setDescricao(dto.descricao());
        repository.save(tarefa);
        return mapper.toTarefaOutputDTO(tarefa);
    }

    @Override
    public void deletar(Long id){
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não localizada!");
        }
        repository.deleteById(id);
    }

    @Override
    public TarefaResponse alternarConclusao(Long id){
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));
        tarefa.setConcluida(!tarefa.getConcluida());
        repository.save(tarefa);
        return mapper.toTarefaOutputDTO(tarefa);
    }
}
