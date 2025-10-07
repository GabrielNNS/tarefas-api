package com.gabriel.tasks.api.controller;

import com.gabriel.tasks.api.dto.TaskRequest;
import com.gabriel.tasks.api.dto.TaskResponse;
import com.gabriel.tasks.api.dto.TaskUpdateRequest;
import com.gabriel.tasks.api.model.Task;
import com.gabriel.tasks.api.model.TaskStatus;
import com.gabriel.tasks.api.service.ITaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final ITaskService service;

    public TaskController(ITaskService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid TaskRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> listAll(@RequestParam(required = false) TaskStatus status) {
        List<TaskResponse> tasks = (status != null)
            ? service.listAll(status)
            : service.listAll();

        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id, @RequestBody @Valid TaskUpdateRequest dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/alter-status")
    public ResponseEntity<TaskResponse> alterStatus(@PathVariable Long id,
                                                    @RequestParam TaskStatus newStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(service.alterStatus(id, newStatus));
    }
}
