# Tech Challenge FIAP - Galega Burger

Este projeto é uma aplicação Java usando Gradle para automação de builds e Flyway para migração de banco de dados. Ele é configurado para rodar em um ambiente Docker, utilizando `docker compose` para orquestrar os contêineres do banco de dados PostgreSQL e da aplicação.

## Pré-requisitos

- Docker instalado
- Docker Compose instalado


## Estrutura do Projeto

- `build.gradle` - Configuração do Gradle.
- `settings.gradle` - Configuração dos projetos Gradle.
- `src/main/java` - Código fonte da aplicação.
- `src/main/resources` - Recursos da aplicação, incluindo scripts de migração Flyway.
- `Dockerfile` - Instruções para criar a imagem Docker da aplicação.
- `docker-compose.yml` - Configuração para iniciar os serviços Docker.

## Configuração e Execução do Projeto

### Passo 1: Construir a Imagem Docker

Antes de executar a aplicação, você precisa construir a imagem Docker da aplicação Java. Navegue até o diretório do projeto e execute:

```sh
docker compose build
```

### Passo 2: Iniciar os Serviços

Depois de construir a imagem, você pode iniciar os serviços definidos no `docker-compose.yml`:

```sh
docker compose up -d
```
_O "-d" significa "detached mode". Assim o docker compose inicia os contêineres em segundo plano._

Este comando iniciará dois contêineres:

- `postgres`: um contêiner rodando PostgreSQL.
- `tech-challenge`: um contêiner rodando a aplicação Java.

### Passo 3: Verificar a Aplicação

Após iniciar os serviços, você pode verificar se a aplicação está funcionando corretamente acessando a rota de HealthCheck em `http://localhost:8080/healthcheck`. A resposta esperada é:

```plaintext
A API está funcionando!
```

Você também pode verificar os logs para garantir que a aplicação e o banco de dados iniciaram corretamente:

```sh
docker compose logs -f
```

## Configurações

### Banco de Dados

O banco de dados PostgreSQL está configurado com as seguintes credenciais (definidas no `docker-compose.yml`):

- **Nome do Banco de Dados**: `galega_burguer`
- **Usuário**: `postgres`
- **Senha**: `postgres`

### Variáveis de Ambiente

As variáveis de ambiente para a configuração da fonte de dados do Spring Boot estão definidas no `docker-compose.yml`:

```yaml
environment:
  SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/galega_burguer
  SPRING_DATASOURCE_USERNAME: postgres
  SPRING_DATASOURCE_PASSWORD: postgres
```

### Flyway

Os scripts de migração do Flyway devem ser colocados no diretório `src/main/resources/db/migration`. O Flyway irá automaticamente detectar esses scripts e aplicá-los ao banco de dados ao iniciar a aplicação.

## Limpeza e Parada dos Serviços

Para parar os serviços e remover os contêineres, volumes e redes criados pelo `docker compose`, execute:

```sh
docker compose down -v
```

---

Se você tiver qualquer dúvida ou problema, não hesite em abrir uma issue no repositório. Feliz codificação!