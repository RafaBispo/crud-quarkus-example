# crud-quarkus

Projeto de crud de usuários criado para estudos do Quarkus - Framework Java Supersônico/Subatômico.

Neste projeto foi utilizado o padrão Repository do Quarkus.

Para saber mais sobre o Quarkus, visite: <https://quarkus.io/>.

## - Tecnologias Utilizadas

- **Java**: Versão 25
- **Quarkus**: Framework Java para desenvolvimento de aplicações nativas em nuvem
- **Gradle**: Ferramenta de build
- **PostgreSQL**: Banco de dados relacional

## - Bibliotecas e Dependências

- **Quarkus REST**: Para criação de APIs REST
- **Quarkus REST Jackson**: Para serialização JSON
- **Quarkus Hibernate ORM Panache**: Para persistência de dados com ORM
- **Quarkus JDBC PostgreSQL**: Driver JDBC para PostgreSQL
- **Quarkus ARC**: Injeção de dependências
- **MapStruct**: Para mapeamento de objetos
- **Lombok**: Para redução de boilerplate em Java
- **JUnit e REST Assured**: Para testes

## - Estrutura de Pastas

```
crud-quarkus-example/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/rboliveira/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── entity/
│   │   │       ├── exception/
│   │   │       ├── mapper/
│   │   │       ├── repository/
│   │   │       └── service/
```

### Padrão de Arquitetura

O projeto segue o padrão em camadas:

- **Controller**: Camada de apresentação, responsável por receber requisições HTTP e responder
- **Service**: Camada de lógica de negócio, contém as regras da aplicação
- **Repository**: Camada de acesso a dados, utiliza Hibernate Panache para operações CRUD
- **Entity**: Representação das tabelas do banco de dados
- **DTO**: Objetos de transferência de dados entre camadas
- **Mapper**: Conversão entre DTOs e Entities
- **Exception**: Tratamento customizado de exceções

## - Endpoints da API

A aplicação expõe os seguintes endpoints para gerenciamento de usuários:

- **GET /users**: Lista todos os usuários com paginação (parâmetros: `page` e `pageSize`)
- **POST /users**: Cria um novo usuário (corpo: UserDTO)
- **GET /users/{id}**: Busca um usuário por ID
- **PUT /users/{id}**: Atualiza um usuário existente (corpo: UserDTO)
- **DELETE /users/{id}**: Deleta um usuário por ID

#### Exemplo de UserDTO:

```json
{
  "username": "username",
  "password": "password",
  "email": "email"
}
```

## - Como executar o projeto
### 1 - Clonar o repositório:

```shell script
git clone https://github.com/RafaBispo/crud-quarkus-example.git
```
### 2 - Acessar o diretório do projeto:

```shell script
cd crud-quarkus-example
```

## 3 - Execute o comando via terminal:

```shell script
./gradlew quarkusDev
```
## - Testes
### Arquivos de Teste

- **IntegrationTests.java** - Testes de integração dos endpoints REST
- **UserServiceTest.java** - Testes unitários da lógica de negócio
- **UserControllerTest.java** - Testes unitários do controlador

### Executar Testes

```shell script
# Executar todos os testes
./gradlew test

# Executar apenas testes de integração
./gradlew test --tests IntegrationTests

# Executar apenas testes do service
./gradlew test --tests UserServiceTest

# Executar apenas testes do controller
./gradlew test --tests UserControllerTest
```

## - Melhorias Futuras
- [X] Adicionar testes unitários e de integração
- [] Implementar validação de dados
- [] Implementar cache
- [] Implementar autenticação e autorização
- [] Criar uma interface gráfica para gerenciamento de usuários