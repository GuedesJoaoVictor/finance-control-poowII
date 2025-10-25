# Finance Control

Projeto de controle financeiro pessoal desenvolvido em Spring Boot.

## Tecnologias

*   **Backend**: Java 21, Spring Boot 3.x
*   **Banco de Dados**: PostgreSQL
*   **Containerização**: Docker e Docker Compose

## Pré-requisitos

Para executar este projeto, você precisa ter instalado em sua máquina:

1.  **Docker**
2.  **Docker Compose** (geralmente incluído nas instalações recentes do Docker)

## Como Rodar o Projeto

O projeto está configurado para ser executado facilmente utilizando o Docker Compose, que irá gerenciar a inicialização do banco de dados e da aplicação.

### 1. Navegue até o Diretório

Abra o terminal e navegue até o diretório raiz do projeto, onde o arquivo `docker-compose.yml` está localizado:

```bash
cd finance-control-poowII
```

### 2. Inicie os Serviços

Execute o comando `docker compose up` para construir a imagem da aplicação (caso ainda não exista) e iniciar os containers do banco de dados (`db`) e da aplicação (`app`).

```bash
docker compose up
```

### 3. Acesso à API

Após a inicialização bem-sucedida, a API estará acessível.

*   **Porta da Aplicação**: Verifique a porta configurada no seu `application.properties` ou `application.yml`. Por padrão, aplicações Spring Boot rodam na porta `8080`.
*   **Documentação (Swagger/OpenAPI)**: Se a documentação estiver configurada, ela estará disponível em um caminho como `http://localhost:8080/swagger-ui.html` ou `http://localhost:8080/v3/api-docs`.

O banco de dados PostgreSQL estará acessível na porta `5433` do seu host.

### 4. Parar os Serviços

Para parar e remover os containers definidos no `docker-compose.yml`, execute:

```bash
docker compose down
```

### 5. Configuração do Banco de Dados

O banco de dados é configurado com as seguintes credenciais (conforme `docker-compose.yml`):

| Parâmetro | Valor |
| :--- | :--- |
| **Host** | `db` (para a aplicação) ou `localhost:5433` (para acesso externo) |
| **Porta** | `5432` (no container) |
| **Usuário** | `postgres` |
| **Senha** | `postgres` |
| **Database** | `finance-control` |

As migrações do banco de dados (provavelmente via Flyway ou Liquibase) devem ser executadas automaticamente pelo Spring Boot na inicialização.

