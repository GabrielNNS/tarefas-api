CREATE TABLE tarefa (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    completed BOOLEAN DEFAULT FALSE
);