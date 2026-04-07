# TP2 Legado - API de Gerenciamento de Missões e Aventureiros

API Spring Boot para gestão de missões, aventureiros e auditoria.

## Requisitos
*   Java 21
*   Maven
*   Docker e Docker Compose

## Tecnologias
*   Spring Boot 3.4.3
*   Spring Data JPA
*   PostgreSQL
*   Lombok
*   Docker

## Configuração do Banco de Dados
A aplicação utiliza PostgreSQL. As configurações de conexão estão em `src/main/resources/application.yaml`.

Para iniciar o banco de dados:
```bash
docker-compose up -d
```
Este comando inicia um container PostgreSQL na porta `5432` e executa o script de inicialização `script/aventura.sql`.

## Execução da Aplicação
Após subir o banco de dados, execute a aplicação:
```bash
./mvnw spring-boot:run
```
A API estará disponível em `http://localhost:8080`.

## Testes
Para executar os testes de integração (necessário banco de dados ativo):
```bash
./mvnw test
```

## Endpoints Principais

### Aventureiros
*   `POST /api/aventureiros`: Cadastrar aventureiro.
*   `GET /api/aventureiros`: Listar com filtros e paginação.
*   `GET /api/aventureiros/{id}`: Detalhes do aventureiro.
*   `DELETE /api/aventureiros/{id}`: Remover aventureiro.
*   `POST /api/aventureiros/{id}/companheiro`: Adotar companheiro.
*   `GET /api/aventureiros/ranking`: Ranking de aventureiros.

### Missões
*   `POST /api/missoes`: Criar nova missão.
*   `GET /api/missoes`: Listar com filtros e paginação.
*   `GET /api/missoes/{id}`: Detalhes da missão.
*   `GET /api/missoes/relatorios/metricas`: Relatório de métricas de missões.

### Participações
*   `POST /api/participacoes`: Registrar participação de aventureiro em missão.

## Estrutura de Dados
O schema padrão utilizado é `aventura`. O banco de dados é validado na inicialização (`hibernate.ddl-auto: validate`).

## Como Testar (Postman)
Na pasta `/postman` deste repositório, encontra-se o arquivo `TP2_Aventura.postman_collection.json`.
1. Importe este arquivo no Postman.
2. Certifique-se de que a variável `base_url` está apontando para `http://localhost:8080`.
3. Execute os requests em ordem (cadastros antes de consultas).