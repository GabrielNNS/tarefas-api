package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TaskRequest;
import com.gabriel.tarefas_api.dto.TaskResponse;
import com.gabriel.tarefas_api.mapper.TaskMapper;
import com.gabriel.tarefas_api.model.Task;
import com.gabriel.tarefas_api.model.TaskStatus;
import com.gabriel.tarefas_api.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService implements ITaskService {

    private final TaskMapper mapper;
    private final TaskRepository repository;

    public TaskService(TaskMapper mapper, TaskRepository repository){
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public TaskResponse create(TaskRequest dto){
        Task task = mapper.toEntity(dto);
        repository.save(task);
        return mapper.toTarefaResponse(task);
    }

    @Override
    public List<TaskResponse> listAll(){
        return mapper.tarefaResponseList(repository.findAll());
    }

    @Override
    public TaskResponse findById(Long id){
        Task task = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));
        return mapper.toTarefaResponse(task);
    }

    @Override
    public TaskResponse update(Long id, TaskRequest dto){
        Task task = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));
        task.setName(dto.name());
        task.setDescription(dto.description());
        repository.save(task);
        return mapper.toTarefaResponse(task);
    }

    @Override
    public void delete(Long id){
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não localizada!");
        }
        repository.deleteById(id);
    }

    @Override
    public TaskResponse alterStatus(Long id, TaskStatus newStatus){
        Task task = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não localizada!"));

        task.setStatus(newStatus);
        repository.save(task);
        return mapper.toTarefaResponse(task);
    }
}
