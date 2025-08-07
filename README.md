# 📌 Tarefas API

API RESTful para gerenciamento de tarefas, desenvolvida com Spring Boot. Permite criar, listar, atualizar e deletar tarefas (CRUD).

---

## 🔧 Tecnologias

- Java 24
- Spring Boot 3.5.4
- Spring Data JPA
- PostgreSQL
- Maven

---

## 🚀 Endpoints principais

| Método | Rota             | Descrição               |
|--------|------------------|-------------------------|
| GET    | `/tarefas`       | Lista todas as tarefas |
| GET    | `/tarefas/{id}`  | Busca uma tarefa por ID |
| POST   | `/tarefas`       | Cria uma nova tarefa    |
| PUT    | `/tarefas/{id}`  | Atualiza uma tarefa     |
| DELETE | `/tarefas/{id}`  | Remove uma tarefa       |

---

## ▶️ Como rodar localmente

### Pré-requisitos

- Java 24
- Maven