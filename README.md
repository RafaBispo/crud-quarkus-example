# crud-quarkus

Projeto criado para estudos do Quarkus - Framework Java Supersônico/Subatômico.

Para saber mais sobre o Quarkus, visite: <https://quarkus.io/>.

## Tecnologias Utilizadas

- **Java**: Versão 25
- **Quarkus**: Framework Java para desenvolvimento de aplicações nativas em nuvem
- **Gradle**: Ferramenta de build
- **PostgreSQL**: Banco de dados relacional

## Bibliotecas e Dependências

- **Quarkus REST**: Para criação de APIs REST
- **Quarkus REST Jackson**: Para serialização JSON
- **Quarkus Hibernate ORM Panache**: Para persistência de dados com ORM
- **Quarkus JDBC PostgreSQL**: Driver JDBC para PostgreSQL
- **Quarkus ARC**: Injeção de dependências
- **MapStruct**: Para mapeamento de objetos
- **Lombok**: Para redução de boilerplate em Java
- **JUnit e REST Assured**: Para testes

## Endpoints da API

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

## Executando a aplicação em dev mode (usando o gradle)

Execute o comando via terminal:

```shell script
./gradlew quarkusDev
```
