package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;
import com.gabriel.tarefas_api.model.TarefaStatus;

import java.util.List;

public interface ITarefaService {

    TarefaResponse create(TarefaRequest dto);
    List<TarefaResponse> listAll();
    TarefaResponse findById(Long id);
    TarefaResponse update(Long id, TarefaRequest dto);
    void delete(Long id);
    TarefaResponse alterStatus(Long id, TarefaStatus newStatus);
}
