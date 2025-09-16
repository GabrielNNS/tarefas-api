package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TaskRequest;
import com.gabriel.tarefas_api.dto.TaskResponse;
import com.gabriel.tarefas_api.mapper.TaskMapper;
import com.gabriel.tarefas_api.model.Task;
import com.gabriel.tarefas_api.model.TaskStatus;
import com.gabriel.tarefas_api.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private Task taskTest;
    private TaskRequest taskRequestTest;
    private TaskResponse taskResponseTest;

    private static final LocalDateTime FIXED_DATE = LocalDateTime.of(2025, 1, 1, 12, 0);

    @BeforeEach
    public void setUp() {
        taskRequestTest = new TaskRequest("Test", "Desc");
        taskTest = Task.builder()
                .name("Test")
                .description("Desc")
                .createDate(FIXED_DATE)
                .build();
        taskResponseTest = TaskResponse.builder()
                .id(1L)
                .name("Test")
                .description("Desc")
                .status(TaskStatus.TO_DO)
                .createDate(FIXED_DATE)
                .build();
    }

    @Test
    public void shouldReturnTaskResponseAndCreateTaskWhenTaskRequestIsValid() {
        when(mapper.toEntity(any(TaskRequest.class))).thenReturn(taskTest);
        when(mapper.toTarefaResponse(any(Task.class))).thenReturn(taskResponseTest);

        TaskResponse result = service.create(taskRequestTest);

        assertNotNull(result);
        assertEquals(taskRequestTest.name(), result.name());
        assertEquals(taskRequestTest.description(), result.description());
        verify(repository).save(any(Task.class));
    }

    @Test
    public void shouldReturnAllTasks() {
        Task taskTest2 = Task.builder()
                .name("Test 2")
                .description("Desc 2")
                .createDate(FIXED_DATE)
                .build();

        TaskResponse taskResponseTest2 = new TaskResponse(2L,
                "Test 2",
                "Desc 2",
                TaskStatus.TO_DO,
                FIXED_DATE);

        List<Task> tasks = List.of(taskTest, taskTest2);
        List<TaskResponse> responses = List.of(taskResponseTest, taskResponseTest2);

        when(repository.findAll()).thenReturn(tasks);
        when(mapper.tarefaResponseList(tasks)).thenReturn(responses);

        List<TaskResponse> results = service.listAll();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Test", results.get(0).name());
        assertEquals("Test 2", results.get(1).name());
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnEmptyListOfTasksWhenTaskNotExists() {
        List<Task> tasks = new ArrayList<>();

        when(repository.findAll()).thenReturn(tasks);

        List<TaskResponse> results = service.listAll();

        assertEquals(0, results.size());
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnTaskWhenIdIsValid() {
        when(repository.findById(1L)).thenReturn(Optional.of(taskTest));
        when(mapper.toTarefaResponse(taskTest)).thenReturn(taskResponseTest);

        TaskResponse result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Test", result.name());
        verify(repository).findById(1L);
    }

    @Test
    public void shouldThrowExceptionEntityNotFoundWhenTaskIdInvalid() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    public void shouldSaveAndReturnTaskResponseWhenTaskRequestIdAndTaskRequestIsValid() {
        TaskResponse newTaskResponse = TaskResponse.builder()
                .name("New Test")
                .description("New Desc")
                .status(TaskStatus.TO_DO)
                .createDate(FIXED_DATE)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(taskTest));
        when(mapper.toTarefaResponse(taskTest)).thenReturn(newTaskResponse);

        TaskResponse result = service.update(1L, taskRequestTest);

        assertNotNull(result);
        assertEquals("New Test", result.name());
        assertEquals("New Desc", result.description());
        verify(repository).findById(1L);
        verify(repository).save(any(Task.class));
    }

    @Test
    public void shouldDeleteTaskWhenIdIsValid() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    public void shouldThrowExceptionEntityNotFoundWhenIdIsInvalid() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    public void alterStatusSuccess() {
        TaskResponse newTaskResponse = TaskResponse.builder()
                .id(1L)
                .name("Test")
                .description("Desc")
                .status(TaskStatus.DOING)
                .createDate(FIXED_DATE)
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(taskTest));
        when(mapper.toTarefaResponse(taskTest)).thenReturn(newTaskResponse);

        TaskResponse result = service.alterStatus(1L, TaskStatus.DOING);

        assertNotNull(result);
        assertEquals(TaskStatus.DOING, result.status());
        verify(repository).findById(1L);
        verify(repository).save(any(Task.class));
    }
}
