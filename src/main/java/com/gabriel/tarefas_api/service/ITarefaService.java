package com.gabriel.tarefas_api.service;

import com.gabriel.tarefas_api.dto.TarefaInputDTO;
import com.gabriel.tarefas_api.dto.TarefaOutputDTO;

import java.util.List;

public interface ITarefaService {

    TarefaOutputDTO criarTarefa(TarefaInputDTO dto);
    List<TarefaOutputDTO> listar();
    TarefaOutputDTO buscarPorId(Long id);
    TarefaOutputDTO atualizar(Long id, TarefaInputDTO dto);
    void deletar(Long id);
    TarefaOutputDTO alternarConclusao(Long id);
}
