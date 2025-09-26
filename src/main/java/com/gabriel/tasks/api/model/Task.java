package com.gabriel.tasks.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.gabriel.tasks.api.model.TaskStatus.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Builder.Default
    private LocalDateTime createDate = LocalDateTime.now();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private TaskStatus status = TO_DO;
}
