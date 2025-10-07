package com.gabriel.tasks.api.service;

import com.gabriel.tasks.api.dto.TaskRequest;
import com.gabriel.tasks.api.dto.TaskResponse;
import com.gabriel.tasks.api.dto.TaskUpdateRequest;
import com.gabriel.tasks.api.model.TaskStatus;

import java.util.List;

public interface ITaskService {

    TaskResponse create(TaskRequest dto);
    List<TaskResponse> listAll();
    List<TaskResponse> listAll(TaskStatus status);
    TaskResponse findById(Long id);
    TaskResponse update(Long id, TaskUpdateRequest dto);
    void delete(Long id);
    TaskResponse alterStatus(Long id, TaskStatus newStatus);
}
