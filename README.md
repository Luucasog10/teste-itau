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
- GET /api/users/{id}
- POST /api/users
- PUT /api/users/{id}
- DELETE /api/users/{id}
- POST /api/users/{id}/report

## Execução via Docker
- Executar comando: docker compose up

## Testar aplicação local via Swagger
- http://localhost:8082/swagger-ui/index.html