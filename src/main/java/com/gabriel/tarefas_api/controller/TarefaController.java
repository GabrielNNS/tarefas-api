package com.gabriel.tarefas_api.controller;

import com.gabriel.tarefas_api.dto.TarefaRequest;
import com.gabriel.tarefas_api.dto.TarefaResponse;
import com.gabriel.tarefas_api.model.TarefaStatus;
import com.gabriel.tarefas_api.service.ITarefaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    private final ITarefaService service;

    public TarefaController(ITarefaService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TarefaResponse> criar(@RequestBody @Valid TarefaRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarTarefa(dto));
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponse>> listar() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponse> buscarId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponse> atualizar(@PathVariable Long id, @RequestBody @Valid TarefaRequest dto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/alterarStatus")
    public ResponseEntity<TarefaResponse> atualizar(@PathVariable Long id,
                                                    @RequestParam TarefaStatus novoStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(service.alternarConclusao(id, novoStatus));
    }
}
