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
    public ResponseEntity<TarefaResponse> create(@RequestBody @Valid TarefaRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponse>> listAll() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponse> update(@PathVariable Long id, @RequestBody @Valid TarefaRequest dto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/alterarStatus")
    public ResponseEntity<TarefaResponse> alterStatus(@PathVariable Long id,
                                                      @RequestParam TarefaStatus newStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(service.alterStatus(id, newStatus));
    }
}
