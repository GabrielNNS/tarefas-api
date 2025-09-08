package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TaskRequest;
import com.gabriel.tarefas_api.dto.TaskResponse;
import com.gabriel.tarefas_api.model.TaskStatus;

import java.util.List;

public interface ITaskService {

    TaskResponse create(TaskRequest dto);
    List<TaskResponse> listAll();
    TaskResponse findById(Long id);
    TaskResponse update(Long id, TaskRequest dto);
    void delete(Long id);
    TaskResponse alterStatus(Long id, TaskStatus newStatus);
}
