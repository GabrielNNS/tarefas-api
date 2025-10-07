package com.gabriel.tasks.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.tasks.api.dto.TaskRequest;
import com.gabriel.tasks.api.dto.TaskResponse;
import com.gabriel.tasks.api.dto.TaskUpdateRequest;
import com.gabriel.tasks.api.model.Task;
import com.gabriel.tasks.api.model.TaskStatus;
import com.gabriel.tasks.api.repository.TaskRepository;
import com.gabriel.tasks.api.utils.TaskFactory;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
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
@ActiveProfiles("inte")
@Transactional
public class TaskControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final TaskRepository repository;

    private static final Long FIXED_ID = 1L;
    private static final Long INVALID_ID = 99L;
    private static final String NAME = "Task";
    private static final String DESC = "Desc";

    private Task task;
    private TaskRequest request;
    private TaskResponse response;


    @Autowired
    public TaskControllerTest(MockMvc mockMvc,
                              ObjectMapper objectMapper,
                              TaskRepository repository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @BeforeEach
    public void setUp() {
        task = TaskFactory.buildTask(NAME, DESC);
        request = TaskFactory.buildTaskRequest(NAME, DESC);
        response = TaskFactory.buildTaskResponse(FIXED_ID, NAME, DESC);
    }

    @Test
    public void shouldCreateTaskAndReturnTaskResponseWhenHttpPostWithValidTaskRequest() throws Exception {
        String requestJson = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.description").value(DESC));

        List<Task> tasks = repository.findAll();
        Task savedTask = tasks.getFirst();

        assertEquals(1, tasks.size());
        assertEquals(NAME, savedTask.getName());
        assertEquals(DESC, savedTask.getDescription());
    }

    @Test
    public void shouldReturnBadRequestAndErrorResponseWhenHttpPostWithInvalidTaskRequest() throws Exception {
        TaskRequest invalidRequest = TaskFactory.buildTaskRequest("", DESC);
        String requestJson = objectMapper.writeValueAsString(invalidRequest);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(400));

        List<Task> tasks = repository.findAll();

        assertEquals(0, tasks.size());
    }

    @Test
    public void shouldReturnOkAndListTaskResponseWhenHttpGet() throws Exception {
        repository.save(task);

        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].name").value(NAME))
                .andExpect(jsonPath("$.[0].description").value(DESC));
    }

    @Test
    public void shouldReturnOkAndListTaskResponseByStatusWhenHttpGet() throws Exception {
        Task task = TaskFactory.buildTask(NAME, DESC, TaskStatus.DOING);

        repository.save(task);

        mockMvc.perform((get("/tasks?status=DOING"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").exists())
                .andExpect(jsonPath("$.[0].name").value(NAME))
                .andExpect(jsonPath("$.[0].description").value(DESC))
                .andExpect(jsonPath("$.[0].status").value("DOING"));
    }

    @Test
    public void shouldReturnOkAndTaskResponseWhenHttpGetWithValidTaskId() throws Exception {
        Task savedTask = repository.save(task);

        mockMvc.perform(get("/tasks/{id}", savedTask.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedTask.getId()))
                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.description").value(DESC));
    }

    @Test
    public void shouldReturnNotFoundAndErrorResponseWhenHttpGetWithIdNotExisting() throws Exception {
        mockMvc.perform(get("/tasks/{id}", INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404));

        assertFalse(repository.existsById(INVALID_ID));
    }

    @Test
    public void shouldReturnOkAndTaskResponseWhenHttpPutWithValidTaskRequest() throws Exception {
        TaskUpdateRequest updateRequest = TaskFactory.buildTaskUpdateRequest("Task Update", "");
        String requestJson = objectMapper.writeValueAsString(updateRequest);
        Task currentTask = repository.save(task);

        mockMvc.perform(put("/tasks/{id}", currentTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.name").value(updateRequest.name()));

        Task savedTask = repository.findById(task.getId())
                .orElseThrow();

        assertEquals(updateRequest.name(), savedTask.getName());
    }

    @Test
    public void shouldReturnNotFoundAndErrorResponseWhenHttpPutWithIdNotExisting() throws Exception {
        String requestJson = objectMapper.writeValueAsString(request);
        mockMvc.perform(put("/tasks/{id}", INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404));

        assertFalse(repository.existsById(INVALID_ID));
    }

    @Test
    public void shouldReturnBadRequestAndErrorResponseWhenHttpPutWithJsonAllFieldIsBlank() throws Exception {
        TaskUpdateRequest updateRequest = TaskFactory.buildTaskUpdateRequest("", "");
        String requestJson = objectMapper.writeValueAsString(updateRequest);
        Task currentTask = repository.save(task);

        mockMvc.perform(put("/tasks/{id}", currentTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(400));
    }

    @Test
    public void shouldReturnBadRequestAndErrorResponseWhenHttpPutWithJsonAllFieldIsNull() throws Exception {
        TaskUpdateRequest updateRequest = TaskFactory.buildTaskUpdateRequest(null, null);
        String requestJson = objectMapper.writeValueAsString(updateRequest);
        Task currentTask = repository.save(task);

        mockMvc.perform(put("/tasks/{id}", currentTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(400));
    }

    @Test
    public void shouldReturnNoContentWhenHttpDeleteWithValidId() throws Exception {
        Task taskToDelete = TaskFactory.buildTask(NAME, DESC);
        repository.save(taskToDelete);

        mockMvc.perform(delete("/tasks/{id}", taskToDelete.getId()))
                .andExpect(status().isNoContent());

        assertFalse(repository.existsById(taskToDelete.getId()));
    }

    @Test
    public void shouldReturnNotFoundAndErrorResponseWhenHttpDeleteWithInvalidId() throws Exception {
        mockMvc.perform(delete("/tasks/{id}", INVALID_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404));

        assertFalse(repository.existsById(INVALID_ID));
    }

    @Test
    public void shouldReturnOkAndTaskResponseWhenHttpPatchWithValidId() throws Exception {
        repository.save(task);

        mockMvc.perform(patch("/tasks/{id}/alter-status", task.getId())
                        .param("newStatus", TaskStatus.DOING.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(task.getName()))
                .andExpect(jsonPath("$.status").value("DOING"));
    }

    @Test
    public void shouldReturnNotFoundAndErrorResponseWhenHttpPatchWithInvalidId() throws Exception {
        mockMvc.perform(patch("/tasks/{id}/alter-status", INVALID_ID)
                        .param("newStatus", TaskStatus.DOING.name()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value(404));
    }
}