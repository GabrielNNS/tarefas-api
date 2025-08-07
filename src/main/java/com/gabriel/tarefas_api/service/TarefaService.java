package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TarefaInputDTO;
import com.gabriel.tarefas_api.dto.TarefaOutputDTO;
import com.gabriel.tarefas_api.mapper.TarefaMapper;
import com.gabriel.tarefas_api.model.Tarefa;
import com.gabriel.tarefas_api.repository.TarefaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaMapper mapper;

    @Autowired
    private TarefaRepository repository;

    public TarefaOutputDTO criarTarefa(TarefaInputDTO dto){
        Tarefa tarefa = mapper.toEntity(dto);
        repository.save(tarefa);
        return mapper.toTarefaOutputDTO(tarefa);
    }

    public List<TarefaOutputDTO> listar(){
        return mapper.tarefaOutputDTOList(repository.findAll());
    }

    public TarefaOutputDTO buscarPorId(Long id){
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));
        return mapper.toTarefaOutputDTO(tarefa);
    }

    public TarefaOutputDTO atualizar(Long id, TarefaInputDTO dto){
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));
        tarefa.setNome(dto.nome());
        tarefa.setDescricao(dto.descricao());
        repository.save(tarefa);
        return mapper.toTarefaOutputDTO(tarefa);
    }

    public void deletar(Long id){
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não localizada!");
        }
        repository.deleteById(id);
    }

    public TarefaOutputDTO alternarConclusao(Long id){
        Tarefa tarefa = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));
        tarefa.setConcluida(!tarefa.getConcluida());
        repository.save(tarefa);
        return mapper.toTarefaOutputDTO(tarefa);
    }
}
