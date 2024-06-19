# Report Generator Application

## Tecnologias Utilizadas
- Java 11
- Spring Boot
- PostgreSQL
- AWS S3
- Docker
- Swagger

## Setup
1. Clone o repositório.
2. Configure o banco de dados e as credenciais da AWS no `application.properties`.
3. Execute a aplicação com `mvn spring-boot:run`.

## Endpoints
- GET /api/users
  - Coletar todos os usuários da base de dados
- GET /api/users/{id}
  - Coletar usuário apartir de um ID específico
- POST /api/users
  - Inserir um novo usuário na base de dados
- PATCH /api/users/{id}
  - Atualizar as informações de um usuário na base de dados apartir de um id
- DELETE /api/users/{id}
  - Deletar um usuário na base de dados apartir de um id
- POST /api/users/{id}/report
  - Salvar relatório de um usuário no s3

## Execução via Docker
- Executar comando: docker compose up

## Testar aplicação local via Swagger
- http://localhost:8082/swagger-ui/index.html