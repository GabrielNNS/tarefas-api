package com.gabriel.tarefas_api.mapper;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;
import com.gabriel.tarefas_api.model.Tarefa;
import com.gabriel.tarefas_api.model.TarefaBuilder;
import com.gabriel.tarefas_api.model.TarefaStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TarefaMapperTest {

    private final TarefaMapper mapper = new TarefaMapper();

    @Test
    public void toEntityTest() {
        TarefaRequest tarefaRequest = new TarefaRequest("Teste1", "Vai dar bom");

        Tarefa tarefa = mapper.toEntity(tarefaRequest);

        assertNotNull(tarefa);
        assertEquals("Teste1", tarefa.getName());
        assertEquals("Vai dar bom", tarefa.getDescription());
        assertEquals(TarefaStatus.TO_DO, tarefa.getStatus());
    }

    @Test
    public void toTarefaResponseTest() {
        Tarefa tarefa = new TarefaBuilder().name("Teste2")
                .description("Text")
                .build();

        TarefaResponse tarefaResponse = mapper.toTarefaResponse(tarefa);

        assertNotNull(tarefaResponse);
        assertEquals(tarefa.getId(), tarefaResponse.id());
        assertEquals(tarefa.getName(), tarefaResponse.name());
        assertEquals(tarefa.getDescription(), tarefaResponse.description());
        assertEquals(tarefa.getCreateDate(), tarefaResponse.createDate());
        assertEquals(tarefa.getStatus(), tarefaResponse.status());
    }

    @Test
    public void tarefaResponseListTest() {
        Tarefa tarefa = new TarefaBuilder().name("Teste2")
                .description("Text")
                .build();

        Tarefa tarefa2 = new TarefaBuilder().name("Teste2")
                .description("Text2")
                .build();

        List<Tarefa> tarefasList = List.of(tarefa, tarefa2);

        List<TarefaResponse> responseList = mapper.tarefaResponseList(tarefasList);

        assertNotNull(responseList.get(0));
        assertNotNull(responseList.get(1));
        assertEquals(tarefa.getId(), responseList.get(0).id());
        assertEquals(tarefa2.getId(), responseList.get(1).id());
    }
}
