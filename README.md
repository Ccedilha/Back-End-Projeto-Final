# 📍 Gestão de Endereços - API REST

API REST em Java (Spring Boot) para consulta e gerenciamento de endereços via CEP, integrada com a **BrasilAPI** para busca de CEPs e persistindo os dados em um banco de dados relacional.

---

## 🧱 Stack Utilizada

- Java + Spring Boot
- Spring Data JPA (Hibernate)
- MySQL (banco relacional)
- Maven

---

## 🗄️ Modelagem de Dados

O projeto usa **um único banco de dados**, dividido em **duas tabelas independentes** (sem relacionamento entre elas):

### Tabela 1: Histórico de Consultas (`tb_historico`)
Registra toda busca de CEP feita pelo usuário, como uma trilha de auditoria/log.

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | Chave primária, gerada pelo banco |
| `cep` | String | CEP pesquisado |
| `dataConsulta` | LocalDateTime | Data/hora da consulta, gerada pelo **backend** |

### Tabela 2: Endereços Salvos (`tb_enderecos`)
Quando o usuário decide salvar um CEP encontrado, dando um apelido a ele (ex: "Casa", "Trabalho"). Permite edição e exclusão.

| Campo | Tipo | Descrição |
|---|---|---|
| `id` | Long | Chave primária, gerada pelo banco |
| `apelido` | String | Nome dado pelo usuário |
| `cep` | String | CEP do endereço |
| `rua` | String | Retornado pela BrasilAPI |
| `bairro` | String | Retornado pela BrasilAPI |
| `cidade` | String | Retornado pela BrasilAPI |
| `estado` | String | Retornado pela BrasilAPI |

---

## 🏗️ Arquitetura

O backend segue arquitetura em camadas, padrão em projetos Spring Boot:

```
Controller  →  Service  →  Repository  →  Banco de Dados
```

- **Controller**: recebe as requisições HTTP e devolve respostas. Não tem lógica de negócio.
- **Service**: contém as regras de negócio (ex: gerar a data da consulta, validar se um endereço existe antes de atualizar).
- **Repository**: interface `JpaRepository` responsável por conversar com o banco.

Estrutura de pastas:

```
src/main/java/com/example/BackEndProjetoFinal/
├── entity/
│   ├── HistoricoEntity.java
│   └── EnderecoEntity.java
├── repository/
│   ├── HistoricoRepository.java
│   └── EnderecoRepository.java
├── service/
│   ├── HistoricoService.java
│   └── EnderecoService.java
└── controller/
    ├── HistoricoController.java
    └── EnderecoController.java
```

---

## 🔌 Endpoints da API

Base URL: `http://localhost:8080/api`

### Histórico

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/historico` | Lista todo o histórico de consultas |
| `POST` | `/historico` | Registra uma nova consulta (`{ "cep": "01001000" }`) |
| `DELETE` | `/historico` | Limpa todo o histórico |

### Endereços

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/enderecos` | Lista todos os endereços salvos |
| `POST` | `/enderecos` | Salva um novo endereço |
| `PUT` | `/enderecos/{id}` | Atualiza o apelido de um endereço (`{ "apelido": "Casa" }`) |
| `DELETE` | `/enderecos/{id}` | Remove um endereço |

> ⚠️ Por decisão de design, o `POST /historico` e o `PUT /enderecos/{id}` aceitam **apenas os campos necessários** (`cep` e `apelido`, respectivamente), em vez da entidade inteira. Isso evita que o cliente envie ou sobrescreva campos que deveriam ser controlados pelo servidor (como `id` e `dataConsulta`).

---

## ⚙️ Como Tudo Funciona (Fluxo da Aplicação)

1. **Registro no histórico**: a cada consulta de CEP bem-sucedida, um `POST /api/historico` é feito enviando só o CEP. O **backend** gera o `id` e a `dataConsulta` (nunca se confia em data vinda do cliente).
2. **Salvamento de endereço**: quando o usuário decide salvar um endereço com apelido, um `POST /api/enderecos` é feito com os dados completos.
3. **Atualização**: o apelido de um endereço salvo pode ser alterado via `PUT /api/enderecos/{id}`.
4. **Remoção**: endereços podem ser excluídos via `DELETE /api/enderecos/{id}`, e o histórico inteiro pode ser limpo via `DELETE /api/historico`.
5. **Listagem**: `GET /api/historico` e `GET /api/enderecos` retornam os dados já persistidos no banco.

---

## 🌐 CORS

Os controllers usam `@CrossOrigin(origins = "*")` para liberar requisições de outras origens (por exemplo, um frontend rodando em outra porta) durante o desenvolvimento.

> Em produção, o ideal é restringir `origins` para o domínio real do cliente que vai consumir a API, em vez de usar `"*"`.

---

## 🚀 Como Executar o Projeto

Configure o banco de dados criando (ou ajustando) o `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

> Esse arquivo não vem versionado no repositório (está no `.gitignore`/variáveis de ambiente), então cada pessoa que for rodar o projeto precisa criar o seu localmente.

Depois, na raiz do projeto:

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. As tabelas `tb_historico` e `tb_enderecos` são criadas automaticamente pelo Hibernate na primeira execução.

---