package com.gabriel.tarefas_api.mapper;

import com.gabriel.tarefas_api.dto.TaskRequest;
import com.gabriel.tarefas_api.dto.TaskResponse;
import com.gabriel.tarefas_api.model.Task;
import com.gabriel.tarefas_api.model.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("unit")
public class TaskMapperTest {

    private final TaskMapper mapper = new TaskMapper();

    @Test
    public void toEntityTest() {
        TaskRequest taskRequest = new TaskRequest("Teste1", "Vai dar bom");

        Task task = mapper.toEntity(taskRequest);

        assertNotNull(task);
        assertEquals("Teste1", task.getName());
        assertEquals("Vai dar bom", task.getDescription());
        assertEquals(TaskStatus.TO_DO, task.getStatus());
    }

    @Test
    public void toTarefaResponseTest() {
        Task task = Task.builder()
                .name("Teste2")
                .description("Text")
                .build();

        TaskResponse taskResponse = mapper.toTarefaResponse(task);

        assertNotNull(taskResponse);
        assertEquals(task.getId(), taskResponse.id());
        assertEquals(task.getName(), taskResponse.name());
        assertEquals(task.getDescription(), taskResponse.description());
        assertEquals(task.getCreateDate(), taskResponse.createDate());
        assertEquals(task.getStatus(), taskResponse.status());
    }

    @Test
    public void tarefaResponseListTest() {
        Task task = Task.builder()
                .name("Teste2")
                .description("Text")
                .build();

        Task task2 = Task.builder()
                .name("Teste2")
                .description("Text2")
                .build();

        List<Task> tarefasList = List.of(task, task2);

        List<TaskResponse> responseList = mapper.tarefaResponseList(tarefasList);

        assertNotNull(responseList.get(0));
        assertNotNull(responseList.get(1));
        assertEquals(task.getId(), responseList.get(0).id());
        assertEquals(task2.getId(), responseList.get(1).id());
    }
}
