package com.gabriel.tasks.api.service;

import com.gabriel.tasks.api.dto.TaskRequest;
import com.gabriel.tasks.api.dto.TaskResponse;
import com.gabriel.tasks.api.dto.TaskUpdateRequest;
import com.gabriel.tasks.api.mapper.TaskMapper;
import com.gabriel.tasks.api.model.Task;
import com.gabriel.tasks.api.model.TaskStatus;
import com.gabriel.tasks.api.repository.TaskRepository;
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
        return mapper.toTaskResponse(task);
    }

    @Override
    public List<TaskResponse> listAll(){
        return mapper.taskResponseList(repository.findAll());
    }

    @Override
    public List<TaskResponse> listAllByStatus(TaskStatus status) {
        return mapper.taskResponseList(repository.findByStatus(status));
    }

    @Override
    public TaskResponse findById(Long id){
        return mapper.toTaskResponse(getUserOrThrow(id));
    }

    @Override
    public TaskResponse update(Long id, TaskUpdateRequest dto){
        Task task = getUserOrThrow(id);
        if(!dto.name().isBlank()) task.setName(dto.name());
        if(!dto.description().isBlank()) task.setDescription(dto.description());
        repository.save(task);
        return mapper.toTaskResponse(task);
    }

    @Override
    public void delete(Long id){
        Task task = getUserOrThrow(id);
        repository.deleteById(id);
    }

    @Override
    public TaskResponse alterStatus(Long id, TaskStatus newStatus){
        Task task = getUserOrThrow(id);
        task.setStatus(newStatus);
        repository.save(task);
        return mapper.toTaskResponse(task);
    }

    private Task getUserOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The id " + id + " does not exist!!"));
    }
}