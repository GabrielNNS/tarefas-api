package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;

import java.util.List;

public interface ITarefaService {

    TarefaResponse criarTarefa(TarefaRequest dto);
    List<TarefaResponse> listar();
    TarefaResponse buscarPorId(Long id);
    TarefaResponse atualizar(Long id, TarefaRequest dto);
    void deletar(Long id);
    TarefaResponse alternarConclusao(Long id);
}
