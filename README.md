# TP3 - API de Gerenciamento de Missões e Aventureiros
API Spring Boot para gestão de missões, aventureiros, auditoria e marketplace.

## Requisitos
*   Java 21
*   Maven
*   Docker e Docker Compose

## Tecnologias
*   Spring Boot 3.4.3
*   Spring Data JPA
*   Spring Data Elasticsearch
*   PostgreSQL
*   Redis
*   Elasticsearch
*   Kibana
*   Lombok
*   Docker

## Configuração do Ambiente e Serviços
A aplicação requer instâncias ativas do PostgreSQL, Redis, Elasticsearch e Kibana. A orquestração desses serviços é gerenciada pelo Docker Compose.

Para iniciar toda a infraestrutura:
```bash
docker-compose up -d
```

Este comando inicia:
- **PostgreSQL** (porta `5432`): Com os schemas `audit`, `aventura` e `operacoes`, executando o script de inicialização.
- **Redis** (porta `6379`): Utilizado como mecanismo de cache da aplicação.
- **Elasticsearch** (porta `9200`): Mecanismo de busca e análise, contendo o índice `guilda_loja`.
- **Kibana** (porta `5601`): Interface administrativa para consulta direta no Elasticsearch (Dev Tools).

## Execução da Aplicação
Após subir os containers via Docker Compose, inicie a aplicação:
```bash
./mvnw spring-boot:run
```
A API estará disponível em `http://localhost:8080`.

## Testes
Para executar a bateria de testes unitários e de integração:
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

### Painel Tático (Operações / Cache)
*   `GET /api/operacoes/painel-tatico`: Listar todas as missões consolidadas da view estratégica.
*   `GET /api/operacoes/painel-tatico/missoes/top15dias`: Top 10 missões mais relevantes dos últimos 15 dias.

### Marketplace (Elasticsearch)
Buscas implementadas sobre o índice `guilda_loja`.

**Buscas Textuais:**
*   `GET /produtos/busca/nome?termo={termo}`: Busca textual padrão no campo nome.
*   `GET /produtos/busca/descricao?termo={termo}`: Busca textual padrão no campo descricao.
*   `GET /produtos/busca/frase?termo={termo}`: Busca por frase exata no campo descricao.
*   `GET /produtos/busca/fuzzy?termo={termo}`: Busca tolerante a erro de digitação no campo nome.
*   `GET /produtos/busca/multicampos?termo={termo}`: Busca o termo simultaneamente nos campos nome e descricao.

**Buscas com Filtros e Análises:**
*   `GET /produtos/busca/com-filtro?termo={termo}&categoria={categoria}`: Busca textual na descrição combinada com filtro exato por categoria.
*   `GET /produtos/busca/faixa-preco?min={min}&max={max}`: Busca estruturada por intervalo numérico de preço.
*   `GET /produtos/busca/avancada?categoria={categoria}&raridade={raridade}&min={min}&max={max}`: Busca combinada utilizando filtros dinâmicos múltiplos.

## Estrutura de Dados (JPA e Elasticsearch)
A estrutura de tabelas SQL (schema `aventura` e `audit`) é validada na inicialização do JPA (`hibernate.ddl-auto: validate`). 
A estrutura do catálogo de produtos utiliza mapeamento de leitura (`createIndex = false`) no Elasticsearch (índice `guilda_loja`) para evitar modificações acidentais na infraestrutura preexistente.

## Como Testar (Postman)
Na pasta `/postman` deste repositório, encontra-se a collection de requisições.
1. Importe o arquivo JSON no Postman.
2. Configure a variável de ambiente apontando para `http://localhost:8080`.
3. Execute as requisições conforme a necessidade de validação.