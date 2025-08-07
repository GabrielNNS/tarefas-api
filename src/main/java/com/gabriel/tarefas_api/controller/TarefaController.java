package com.gabriel.tarefas_api.controller;

import com.gabriel.tarefas_api.dto.TarefaInputDTO;
import com.gabriel.tarefas_api.dto.TarefaOutputDTO;
import com.gabriel.tarefas_api.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService service;

    @PostMapping
    public ResponseEntity<TarefaOutputDTO> criar(@RequestBody @Valid TarefaInputDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarTarefa(dto));
    }

    @GetMapping
    public ResponseEntity<List<TarefaOutputDTO>> listar() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaOutputDTO> buscarId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaOutputDTO> atualizar(@PathVariable Long id, @RequestBody @Valid TarefaInputDTO dto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/alterarStatus")
    public ResponseEntity<TarefaOutputDTO> atualizar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.alternarConclusao(id));
    }
}
