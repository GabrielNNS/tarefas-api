package com.gabriel.tasks.api.repository;

import com.gabriel.tasks.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
