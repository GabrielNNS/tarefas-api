package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;
import com.gabriel.tarefas_api.mapper.TarefaMapper;
import com.gabriel.tarefas_api.model.Tarefa;
import com.gabriel.tarefas_api.model.TarefaBuilder;
import com.gabriel.tarefas_api.model.TarefaStatus;
import com.gabriel.tarefas_api.repository.TarefaRepository;
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
public class TarefaServiceTest {

    @Mock
    private TarefaRepository repository;

    @Mock
    private TarefaMapper mapper;

    @InjectMocks
    private TarefaService service;

    @Test
    public void createTarefaSuccess() {
        TarefaRequest request = new TarefaRequest("Test Mock", "Agora vai");

        Tarefa tarefa = new TarefaBuilder().name("Test Mock")
                .description("Agora vai")
                .build();

        TarefaResponse response = new TarefaResponse(1L,
                "Test Mock",
                "Agora vai",
                TarefaStatus.TO_DO,
                LocalDateTime.now());

        when(mapper.toEntity(request)).thenReturn(tarefa);
        when(mapper.toTarefaResponse(tarefa)).thenReturn(response);
        when(repository.save(any(Tarefa.class))).thenReturn(tarefa);

        TarefaResponse result = service.create(request);

        assertNotNull(result);
        assertEquals("Test Mock", result.name());
        verify(repository).save(any(Tarefa.class));
    }

    @Test
    public void listAllTarefas() {
        Tarefa tarefa = new TarefaBuilder().name("Test Mock")
                .description("Agora vai")
                .build();

        Tarefa tarefa2 = new TarefaBuilder().name("Testando")
                .description("Agora vai 2")
                .build();

        TarefaResponse response = new TarefaResponse(1L,
                "Test Mock",
                "Agora vai",
                TarefaStatus.TO_DO,
                LocalDateTime.now());

        TarefaResponse response2 = new TarefaResponse(2L,
                "Testando",
                "Agora vai 2",
                TarefaStatus.TO_DO,
                LocalDateTime.now());

        List<Tarefa> entities = List.of(tarefa, tarefa2);
        List<TarefaResponse> responses = List.of(response, response2);

        when(repository.findAll()).thenReturn(entities);
        when(mapper.tarefaResponseList(entities)).thenReturn(responses);


        List<TarefaResponse> results = service.listAll();

        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Test Mock", results.get(0).name());
        assertEquals("Testando", results.get(1).name());
        verify(repository).findAll();
    }

    @Test
    public void findTarefaById() {
        Tarefa tarefa = new TarefaBuilder().name("Test Mock")
                .description("Agora vai")
                .build();

        TarefaResponse response = new TarefaResponse(1L,
                "Test Mock",
                "Agora vai",
                TarefaStatus.TO_DO,
                LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(mapper.toTarefaResponse(tarefa)).thenReturn(response);

        TarefaResponse result = service.findById(1L);

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
        TarefaRequest request = new TarefaRequest("Tarefa Nova",
                "Desc Nova");

        Tarefa currentTarefa = new Tarefa(1L,
                "Tarefa atual",
                "Desc atual",
                LocalDateTime.now(),
                TarefaStatus.TO_DO);

        TarefaResponse newTarefaResponse = new TarefaResponse(1L,
                "Tarefa Nova",
                "Desc Nova",
                TarefaStatus.TO_DO,
                LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(currentTarefa));
        when(repository.save(currentTarefa)).thenReturn(currentTarefa);
        when(mapper.toTarefaResponse(currentTarefa)).thenReturn(newTarefaResponse);

        TarefaResponse result = service.update(1L, request);

        assertNotNull(result);
        assertEquals("Tarefa Nova", result.name());
        assertEquals("Desc Nova", result.description());
        verify(repository).findById(1L);
        verify(repository).save(any(Tarefa.class));
    }

    @Test
    public void deletedTarefaSucess() {
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
        Tarefa currentTarefa = new Tarefa(1L,
                "Tarefa atual",
                "Desc atual",
                LocalDateTime.now(),
                TarefaStatus.TO_DO);

        TarefaResponse newTarefaResponse = new TarefaResponse(1L,
                "Tarefa alter",
                "Desc alter",
                TarefaStatus.DOING,
                LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(currentTarefa));
        when(repository.save(currentTarefa)).thenReturn(currentTarefa);
        when(mapper.toTarefaResponse(currentTarefa)).thenReturn(newTarefaResponse);

        TarefaResponse result = service.alterStatus(1L, TarefaStatus.DOING);

        assertNotNull(result);
        assertEquals(TarefaStatus.DOING, result.status());
        verify(repository).findById(1L);
        verify(repository).save(any(Tarefa.class));
    }
}
