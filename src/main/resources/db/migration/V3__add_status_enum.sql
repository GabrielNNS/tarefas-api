ALTER TABLE tarefa
    ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'TO_DO'
    CHECK (status IN ('TO_DO', 'DOING', 'DONE'));

UPDATE tarefa
SET status = CASE
    WHEN completed = TRUE THEN 'DONE'
    ELSE 'TO_DO'
END;

ALTER TABLE tarefa
    DROP COLUMN completed;