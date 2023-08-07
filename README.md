# josifHub-api

## Banco de dados:


Servidor:

`josifhub-database.postgres.database.azure.com`

Usuário:

`josifHubAdm`

Banco de dados:

`josifhub-database`

Senha:

`4U6g2hBEJDK#@U`

URL:

`jdbc:postgresql://josifhub-database.postgres.database.azure.com/josifHub`

## Lista de EndPoints

Documentação da API:

```http://localhost:8080/swagger-ui/index.html/```

### Avaliador:
| Método      | EndPoint                       | Resposta                                                                                                                                              |
|-------------|--------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|
| [GET]       | `/api/v1/avaliadores`          | Retorna os avaliadores <br/><br/>(Status 200 OK)                                                                                                      |
| [POST]      | `/api/v1/avaliadores`          | Adiciona o avaliador e cadastra as suas areas de atuação <br/><br/> Ok:<br/>(Status 201 Created)  <br/><br/>CPF já existe:<br/>(Status 409 Conflict)  |
| [PUT]       | `/api/v1/avaliadores/{codigo}` | Edita o avaliador  e as suas areas de atuação      <br/><br/> Ok:<br/>(Status 200 OK)<br/><br/> Codigo inexistente:<br/>(Status 404 Not Found)        |
| [DELETE]*** | `/api/v1/avaliador/{codigo}`   | Exclui o avaliador (não exclui se tiver relacionamento)                                                                                               |
*** Precisa de correção
### Trabalho:
| Método      | EndPoint                     | Resposta                                                                         |
|-------------|------------------------------|----------------------------------------------------------------------------------|
| [GET]       | `/api/v1/trabalhos`          | Retorna os trabalhos e os respectivos autores <br/><br/> Ok:<br/>(Status 200 OK) |
| [POST]      | `/api/v1/trabalhos`          | Adiciona o trabalho <br/><br/> Ok:<br/>(Status 201 Created)                      |
| [PUT]       | `/api/v1/trabalhos/{codigo}` | Edita o trabalho <br/><br/> Ok:<br/>(Status 200 OK)<br/><br/> Codigo inexistente:<br/>(Status 404 Not Found)                                                                |
| [DELETE]*** | `/api/v1/trabalhos/{codigo}` | Exclui o trabalho                                                                |
*** Precisa de correção
### Autor:
| Método   | EndPoint                  | Resposta           |
|----------|---------------------------|--------------------|
| [GET]    | `/api/v1/autor`           | Retorna os autores |
| [POST]   | `/api/v1/autor/`          | Adiciona o autor   |
| [PUT]    | `/api/v1/autor/{codigo}`  | Edita o autor      |
| [DELETE] | `/api/v1/autor/{codigo}`  | Deleta o autor     |

### Área:
| Método   | EndPoint                | Resposta         |
|----------|-------------------------|------------------|
| [GET]    | `/api/v1/area`          | Retorna as áreas |
| [POST]   | `/api/v1/area`          | Adiciona a área  |
| [PUT]    | `/api/v1/area/{codigo}` | Edita a área     |
| [DELETE] | `/api/v1/area/{codigo}`  | Deleta a área    |


