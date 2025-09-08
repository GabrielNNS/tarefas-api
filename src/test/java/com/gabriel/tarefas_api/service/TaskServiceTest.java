package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TaskRequest;
import com.gabriel.tarefas_api.dto.TaskResponse;
import com.gabriel.tarefas_api.mapper.TaskMapper;
import com.gabriel.tarefas_api.model.Task;
import com.gabriel.tarefas_api.model.TaskStatus;
import com.gabriel.tarefas_api.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository repository;

    @Mock
    private TaskMapper mapper;

    @InjectMocks
    private TaskService service;

    private static final Long ID = 1L;
    private static final String NAME = "Test Mock";
    private static final String DESCRIPTION = "Agora vai";
    private static final TaskStatus STATUS = TaskStatus.TO_DO;
    private static final LocalDateTime DATE = LocalDateTime.now();

    @Test
    public void givenValidTaskRequestThenSaveEntityAndReturnTaskResponse() {
        TaskRequest request = new TaskRequest(NAME, DESCRIPTION);
        Task task = Task.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .build();
        TaskResponse response = new TaskResponse(ID, NAME, DESCRIPTION, STATUS, DATE);

        when(mapper.toEntity(request)).thenReturn(task);
        when(mapper.toTarefaResponse(task)).thenReturn(response);

        TaskResponse result = service.create(request);

        assertEquals(request.name(), result.name());
        assertEquals(request.description(), result.description());
        verify(repository).save(any(Task.class));
    }

    @Test
    public void listAllTarefas() {
        Task task = Task.builder()
                .name("Test Mock")
                .description("Agora vai")
                .build();

        Task task2 = Task.builder()
                .name("Testando")
                .description("Agora vai 2")
                .build();

        TaskResponse response = new TaskResponse(1L,
                "Test Mock",
                "Agora vai",
                TaskStatus.TO_DO,
                LocalDateTime.now());

        TaskResponse response2 = new TaskResponse(2L,
                "Testando",
                "Agora vai 2",
                TaskStatus.TO_DO,
                LocalDateTime.now());

        List<Task> entities = List.of(task, task2);
        List<TaskResponse> responses = List.of(response, response2);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.tarefaResponseList(entities)).thenReturn(responses);


        List<TaskResponse> results = service.listAll();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Test Mock", results.get(0).name());
        assertEquals("Testando", results.get(1).name());
        verify(repository).findAll();
    }

    @Test
    public void findTarefaById() {
        Task task = Task.builder()
                .name("Test Mock")
                .description("Agora vai")
                .build();

        TaskResponse response = new TaskResponse(1L,
                "Test Mock",
                "Agora vai",
                TaskStatus.TO_DO,
                LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(task));
        when(mapper.toTarefaResponse(task)).thenReturn(response);

        TaskResponse result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(repository).findById(1L);
    }

    @Test
    public void findByIdThrowsExceptionEntityNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
    }

    @Test
    public void updatedNameAndDescriptionTarefa() {
        TaskRequest request = new TaskRequest("Tarefa Nova",
                "Desc Nova");

        Task currentTask = Task.builder()
                .id(1L)
                .name("Tarefa atual")
                .description("Desc atual")
                .createDate(LocalDateTime.now())
                .status(TaskStatus.TO_DO)
                .build();

        TaskResponse newTaskResponse = new TaskResponse(1L,
                "Tarefa Nova",
                "Desc Nova",
                TaskStatus.TO_DO,
                LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(currentTask));
        when(repository.save(currentTask)).thenReturn(currentTask);
        when(mapper.toTarefaResponse(currentTask)).thenReturn(newTaskResponse);

        TaskResponse result = service.update(1L, request);

        assertNotNull(result);
        assertEquals("Tarefa Nova", result.name());
        assertEquals("Desc Nova", result.description());
        verify(repository).findById(1L);
        verify(repository).save(any(Task.class));
    }

    @Test
    public void deletedTarefaSuccess() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    public void deletedTarefaThrowsEntityNotFoundException() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    public void alterStatusSuccess() {
        Task currentTask = Task.builder()
                .id(1L)
                .name("Tarefa atual")
                .description("Desc atual")
                .createDate(LocalDateTime.now())
                .status(TaskStatus.TO_DO)
                .build();

        TaskResponse newTaskResponse = new TaskResponse(1L,
                "Tarefa alter",
                "Desc alter",
                TaskStatus.DOING,
                LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(currentTask));
        when(repository.save(currentTask)).thenReturn(currentTask);
        when(mapper.toTarefaResponse(currentTask)).thenReturn(newTaskResponse);

        TaskResponse result = service.alterStatus(1L, TaskStatus.DOING);

        assertNotNull(result);
        assertEquals(TaskStatus.DOING, result.status());
        verify(repository).findById(1L);
        verify(repository).save(any(Task.class));
    }
}
