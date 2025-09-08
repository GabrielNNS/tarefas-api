package com.gabriel.tarefas_api.repository;

import com.gabriel.tarefas_api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
