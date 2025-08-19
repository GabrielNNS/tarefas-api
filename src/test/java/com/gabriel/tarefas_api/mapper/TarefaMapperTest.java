package com.gabriel.tarefas_api.mapper;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;
import com.gabriel.tarefas_api.model.Tarefa;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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
        assertEquals("Teste1", tarefa.getNome());
        assertEquals("Vai dar bom", tarefa.getDescricao());
        assertEquals(false, tarefa.getConcluida());
    }

    @Test
    public void toTarefaResponseTest() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setNome("Teste2");
        tarefa.setDescricao("Text");
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setConcluida(false);

        TarefaResponse tarefaResponse = mapper.toTarefaResponse(tarefa);

        assertNotNull(tarefaResponse);
        assertEquals(tarefa.getId(), tarefaResponse.id());
        assertEquals(tarefa.getNome(), tarefaResponse.nome());
        assertEquals(tarefa.getDescricao(), tarefaResponse.descricao());
        assertEquals(tarefa.getDataCriacao(), tarefaResponse.dataCriacao());
        assertEquals(tarefa.getConcluida(), tarefaResponse.concluida());
    }

    @Test
    public void tarefaResponseListTest() {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setNome("Teste2");
        tarefa.setDescricao("Text");
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setConcluida(false);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setId(2L);
        tarefa2.setNome("Teste3");
        tarefa2.setDescricao("Text");
        tarefa2.setDataCriacao(LocalDateTime.now());
        tarefa2.setConcluida(false);
        List<Tarefa> tarefasList = List.of(tarefa, tarefa2);

        List<TarefaResponse> responseList = mapper.tarefaResponseList(tarefasList);

        assertNotNull(responseList.get(0));
        assertNotNull(responseList.get(1));
        assertEquals(tarefa.getId(), responseList.get(0).id());
        assertEquals(tarefa2.getId(), responseList.get(1).id());
    }
}
