# TP3 - API de Gerenciamento de Missões e Aventureiros
API Spring Boot para gestão de missões, aventureiros e auditoria.

## Requisitos
*   Java 21
*   Maven
*   Docker e Docker Compose

## Tecnologias
*   Spring Boot 3.4.3
*   Spring Data JPA
*   PostgreSQL
*   Redis
*   Lombok
*   Docker

## Configuração do Banco de Dados
A aplicação utiliza PostgreSQL e Redis. As configurações de conexão estão em `src/main/resources/application.yaml`.

Para iniciar o banco de dados e o Redis:
```bash
docker-compose up -d
```

Este comando inicia:
- Um container PostgreSQL na porta `5432` com os schemas `audit`, `aventura` e `operacoes`, executando o script de inicialização `script/aventura.sql`.
- Um container Redis na porta `6379`, utilizado como cache da aplicação.

## Execução da Aplicação
Após subir os containers, execute a aplicação:
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

### Painel Tático (Operações)
*   `GET /api/operacoes/painel-tatico`: Listar todas as missões consolidadas da view estratégica.
*   `GET /api/operacoes/painel-tatico/missoes/top15dias`: Retornar o Top 10 missões mais relevantes dos últimos 15 dias, ordenadas por índice de prontidão de forma decrescente.

## Cache com Redis (Questão 2)

### Problema
O endpoint `/api/operacoes/painel-tatico/missoes/top15dias` consulta a materialized view `operacoes.vw_painel_tatico_missao`, que executa operações pesadas de agregação (`COUNT`, `AVG`, `SUM`, `MAX`) com múltiplos `JOINs`. Chamadas repetidas ao endpoint geravam carga desnecessária no banco de dados, com tempo de resposta em torno de **4 segundos** por requisição.

### Solução
Foi implementado cache com **Redis** utilizando Spring Cache (`@Cacheable`). Na primeira chamada, o resultado é buscado no banco e armazenado no Redis. Nas chamadas seguintes, o resultado é retornado diretamente do cache, sem nenhuma consulta ao banco.

O cache expira automaticamente após **10 minutos** (`time-to-live: 600000ms`), garantindo que os dados se mantenham atualizados em um intervalo aceitável.

### Resultado
| Chamada | Tempo de resposta |
|---|---|
| 1ª (sem cache) | ~4 segundos |
| 2ª em diante (com cache) | ~4ms |

Redução de aproximadamente **1000x** no tempo de resposta.

## Estrutura de Dados
O schema padrão utilizado é `aventura`. O banco de dados é validado na inicialização (`hibernate.ddl-auto: validate`).

## Como Testar (Postman)
Na pasta `/postman` deste repositório, encontra-se o arquivo `TP2_Aventura.postman_collection.json`.
1. Importe este arquivo no Postman.
2. Certifique-se de que a variável `base_url` está apontando para `http://localhost:8080`.
3. Execute os requests em ordem (cadastros antes de consultas).
