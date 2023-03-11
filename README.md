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

### Avaliador:
| Método   | EndPoint                     | Resposta               |
|----------|------------------------------|------------------------|
| [GET]    | `/api/v1/avaliador`          | Retorna os avaliadores |
| [POST]   | `/api/v1/avaliador`          | Adiciona o avaliador   |
| [PUT]    | `/api/v1/avaliador/{codigo}` | Edita o avaliador      |
| [DELETE] | `/api/v1/avaliador/{codigo}` | Exclui o avaliador     |


### Trabalho:
| Método    | EndPoint                     | Resposta                                      |
|-----------|------------------------------|-----------------------------------------------|
| [GET]     | `/api/v1/trabalhos`          | Retorna os trabalhos e os respectivos autores |
| [POST]    | `/api/v1/trabalhos`          | Adiciona o trabalho                           |
| [PUT]     | `/api/v1/trabalhos/{codigo}` | Edita o trabalho                              |
| [DELETE]  | `/api/v1/trabalhos/{codigo}` | Exclui o trabalho                             |


### Autor:
| Método   | EndPoint                  | Resposta           |
|----------|---------------------------|--------------------|
| [GET]    | `/api/v1/autor`           | Retorna os autores |
| [POST]   | `/api/v1/autor/`          | Adiciona o autor   |
| [PUT]    | `/api/v1/autor/{codigo}`  | Edita o autor      |
| [DELETE] | `/api/v1/autor/{codigo}`  | Deleta o autor     |


