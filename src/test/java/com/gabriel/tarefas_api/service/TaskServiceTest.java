package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TaskRequest;
import com.gabriel.tarefas_api.dto.TaskResponse;
import com.gabriel.tarefas_api.mapper.TaskMapper;
import com.gabriel.tarefas_api.model.Task;
import com.gabriel.tarefas_api.model.TaskStatus;
import com.gabriel.tarefas_api.repository.TaskRepository;
import com.gabriel.tarefas_api.utils.TaskFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("unit")
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskMapper mapper;

    @InjectMocks
    private TaskService service;

    private TaskFactory factory;
    private Task task;
    private TaskResponse taskResponse;
    private TaskRequest taskRequest;

    private static final Long FIXED_ID = 1L;
    private static final String NAME = "Task";
    private static final String DESC = "Desc";

    private void mockMapperToTaskResponse(Task task, TaskResponse taskResponse) {
        when(mapper.toTaskResponse(task)).thenReturn(taskResponse);
    }

    private void mockRepositoryFindById(Long id, Task task) {
        when(repository.findById(id)).thenReturn(Optional.of(task));
    }

    @BeforeEach
    public void setUp() {
        factory = new TaskFactory();
        task = factory.buildTask(FIXED_ID, NAME, DESC);
        taskResponse = factory.buildTaskResponse(FIXED_ID, NAME, DESC);
        taskRequest = factory.buildTaskRequest(NAME, DESC);
    }

    @Test
    public void shouldReturnTaskResponseAndCreateTaskWhenTaskRequestIsValid() {
        when(mapper.toEntity(taskRequest)).thenReturn(task);
        mockMapperToTaskResponse(task, taskResponse);

        TaskResponse result = service.create(taskRequest);

        assertNotNull(result);
        assertEquals(NAME, result.name());
        assertEquals(DESC, result.description());
        verify(repository).save(any(Task.class));
    }

    @Test
    public void shouldReturnAllTasks() {
        Task secondTask = factory.buildTask(2L, "Second Task", "Second Desc");
        TaskResponse secondTaskResponse = factory.buildTaskResponse(2L, "Second Task", "Second Desc");
        List<Task> tasks = List.of(task, secondTask);
        List<TaskResponse> responses = List.of(taskResponse, secondTaskResponse);

        when(repository.findAll()).thenReturn(tasks);
        when(mapper.taskResponseList(tasks)).thenReturn(responses);

        List<TaskResponse> results = service.listAll();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(task.getName(), results.get(0).name());
        assertEquals(secondTask.getName(), results.get(1).name());
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnEmptyListTasksWhenTaskNotExists() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<TaskResponse> results = service.listAll();

        assertEquals(0, results.size());
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnTaskWhenIdIsValid() {
        mockRepositoryFindById(FIXED_ID, task);
        mockMapperToTaskResponse(task, taskResponse);

        TaskResponse result = service.findById(1L);

        assertNotNull(result);
        assertEquals(FIXED_ID, result.id());
        assertEquals(NAME, result.name());
        assertEquals(DESC, result.description());
        verify(repository).findById(FIXED_ID);
    }

    @Test
    public void shouldThrowExceptionEntityNotFoundWhenTaskIdInvalid() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    public void shouldSaveAndReturnTaskResponseWhenTaskRequestIdAndTaskRequestIsValid() {
        TaskResponse updatedTaskResponse = factory.buildTaskResponse(FIXED_ID, "Task Update", "Desc Update");

        mockRepositoryFindById(FIXED_ID, task);
        mockMapperToTaskResponse(task, updatedTaskResponse);

        TaskResponse result = service.update(1L, taskRequest);

        assertNotNull(result);
        assertEquals(FIXED_ID, result.id());
        assertEquals("Task Update", result.name());
        assertEquals("Desc Update", result.description());
        verify(repository).findById(FIXED_ID);
        verify(repository).save(any(Task.class));
    }

    @Test
    public void shouldDeleteTaskWhenIdIsValid() {
        Task taskToDelete = factory.buildTask(20L, NAME, DESC);
        mockRepositoryFindById(20L, taskToDelete);

        service.delete(20L);

        verify(repository).findById(20L);
        verify(repository).deleteById(20L);
    }

    @Test
    public void shouldThrowEntityNotFoundWhenIdIsInvalid() {
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    public void shouldAlterStatusWhenIdIsValid() {
        TaskResponse newTaskResponse = factory.buildTaskResponse(FIXED_ID, NAME, DESC, TaskStatus.DOING);

        mockRepositoryFindById(FIXED_ID, task);
        mockMapperToTaskResponse(task, newTaskResponse);

        TaskResponse result = service.alterStatus(FIXED_ID, TaskStatus.DOING);

        assertNotNull(result);
        assertEquals(TaskStatus.DOING, result.status());
        verify(repository).findById(FIXED_ID);
        verify(repository).save(any(Task.class));
    }
}