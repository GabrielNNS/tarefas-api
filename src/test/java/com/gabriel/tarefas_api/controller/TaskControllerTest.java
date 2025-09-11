package com.gabriel.tarefas_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.tarefas_api.dto.TaskRequest;
import com.gabriel.tarefas_api.model.Task;
import com.gabriel.tarefas_api.model.TaskStatus;
import com.gabriel.tarefas_api.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class TaskControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final TaskRepository repository;

    @Autowired
    public TaskControllerTest(MockMvc mockMvc,
                              ObjectMapper objectMapper,
                              TaskRepository repository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @Test
    public void shouldCreateTaskAndReturnTaskResponseWhenHttpPostWithValidTaskRequest() throws Exception {
        TaskRequest request = new TaskRequest("Test", "Desc");
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test"));

        List<Task> tasks = repository.findAll();
        Task savedTask = tasks.getFirst();

        assertEquals(1, tasks.size());
        assertEquals("Test", savedTask.getName());
    }

    @Test
    public void shouldReturnBadRequestAndErrorResponseWhenHttpPostWithInvalidTaskRequest() throws Exception {
        TaskRequest request = new TaskRequest("", "Desc");
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(400));

        List<Task> tasks = repository.findAll();

        assertEquals(0, tasks.size());
    }

    @Test
    public void shouldReturnOkAndListTaskResponseWhenHttpGet() throws Exception {
        repository.save(Task.builder()
                .name("Test")
                .description("Desc")
                .build());

        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void shouldReturnOkAndTaskResponseWhenHttpGetWithValidTaskId() throws Exception {
        Task task = repository.save(Task.builder()
                .name("Test")
                .description("Desc")
                .build());

        mockMvc.perform(get("/tasks/{id}", task.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.name").value(task.getName()));
    }

    @Test
    public void shouldReturnNotFoundAndErrorResponseWhenHttpGetWithIdNotExisting() throws Exception {
        mockMvc.perform(get("/tasks/{id}", 20L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404));

        assertFalse(repository.existsById(20L));
    }

    @Test
    public void shouldReturnOkAndTaskResponseWhenHttpPutWithValidTaskRequest() throws Exception {
        TaskRequest request = new TaskRequest("Test Updated", "Desc");
        String json = objectMapper.writeValueAsString(request);
        Task task = repository.save(Task.builder()
                .name("Test")
                .description("Desc")
                .build());

        mockMvc.perform(put("/tasks/{id}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.name").value(request.name()));

        Task savedTask = repository.findById(task.getId())
                .orElseThrow();

        assertEquals(request.name(), savedTask.getName());
    }

    @Test
    public void shouldReturnNotFoundAndErrorResponseWhenHttpPutWithIdNotExisting() throws Exception {
        TaskRequest request = new TaskRequest("Test Updated", "Desc");
        String json = objectMapper.writeValueAsString(request);
        Long fakeId = 50L;
        mockMvc.perform(put("/tasks/{id}", fakeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404));

        assertFalse(repository.existsById(fakeId));
    }

    @Test
    public void shouldReturnNoContentWhenHttpDeleteWithValidId() throws Exception {
        Task task = repository.save(Task.builder()
                .name("Test")
                .description("Desc")
                .build());

        mockMvc.perform(delete("/tasks/{id}", task.getId()))
                .andExpect(status().isNoContent());

        assertFalse(repository.existsById(task.getId()));
    }

    @Test
    public void shouldReturnNotFoundAndErrorResponseWhenHttpDeleteWithInvalidId() throws Exception {
        Long fakeId = 40L;
        mockMvc.perform(delete("/tasks/{id}", fakeId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404));

        assertFalse(repository.existsById(fakeId));
    }

    @Test
    public void shouldReturnOkAndTaskResponseWhenHttpPatchWithValidId() throws Exception {
        Task task = repository.save(Task.builder()
                .name("Test")
                .description("Desc")
                .status(TaskStatus.TO_DO)
                .build());

        mockMvc.perform(patch("/tasks/{id}/alter-status", task.getId())
                        .param("newStatus", TaskStatus.DOING.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(task.getName()))
                .andExpect(jsonPath("$.status").value("DOING"));
    }

    @Test
    public void shouldReturnNotFoundAndErrorResponseWhenHttpPatchWithInvalidId() throws Exception {
        Long fakeID = 40L;

        mockMvc.perform(patch("/tasks/{id}/alter-status", fakeID)
                        .param("newStatus", TaskStatus.DOING.name()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404));
    }
}