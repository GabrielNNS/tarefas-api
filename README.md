# 📌 Tarefas API

API RESTful para gerenciamento de tasks, desenvolvida com Spring Boot. Permite criar, listar, atualizar e deletar tasks (CRUD).

---

## 🔧 Tecnologias

- Java 24
- Spring Boot 3.5.4
- Spring Data JPA
- PostgreSQL
- Maven
- Swagger
- Flyway
- Docker (PostgreSQL)

---

## 🚀 Endpoints principais

### Swagger: `/swagger-ui/index.html`

| Método | Rota             | Descrição               |
|--------|------------------|-------------------------|
| GET    | `/tasks`       | Lista todas as tasks |
| GET    | `/tasks/{id}`  | Busca uma task por ID |
| POST   | `/tasks`       | Cria uma nova task    |
| PUT    | `/tasks/{id}`  | Atualiza uma task     |
| DELETE | `/tasks/{id}`  | Remove uma task       |

---

## ▶️ Como rodar localmente

### Pré-requisitos

- Java 24
- Maven

### Tutorial

- Carregar as dependências do Maven no arquivo pom.xml
- criar o arquivo `.env` na raiz do projeto e definir as variáveis `DB_NAME`, `DB_PASSWORD` e `DB_USER`
- Ter o docker instalado e subir o container `docker-compose up -d`
- Configurar as variáveis de ambiente `DB_NAME`, `DB_PASSWORD` e `DB_USER` na IDE (ou outra forma que o Spring reconheça)
- Executar `TarefasApiApplication`