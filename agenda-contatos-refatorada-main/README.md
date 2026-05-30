# 📘 Agenda de Contatos API - Refatoração (Clean Code & SOLID)

Este projeto é o resultado da refatoração completa de uma API REST legada e "propositalmente ruim". O objetivo principal foi transformar um código construído sob *anti-patterns* (como "God Class", falta de encapsulamento e validações manuais) em uma aplicação de nível Enterprise, aplicando os princípios do **SOLID**, **Clean Code** e **Design Patterns**.

## 🛠️ Tecnologias Utilizadas

* **Java 26**
* **Spring Boot 4.0.6**
* **Spring Data JPA** (com *Specifications* para buscas dinâmicas)
* **Bean Validation** (Jakarta Validation)
* **Banco de Dados H2** (Em memória)
* **Maven**

---

## 🚀 O que foi refatorado? (Antes vs Depois)

### 1. Separação de Responsabilidades (SRP)
* **Antes:** O `ContatoController` era uma "God Class" de 300 linhas que fazia roteamento, validação de dados, acesso ao banco, simulação de envio de e-mails e regras de negócio.
* **Depois:** A arquitetura foi dividida em camadas claras: `Controller` (apenas rotas HTTP) ➔ `Service` (regras de negócio) ➔ `Repository` (acesso a dados).

### 2. Blindagem da Entidade com DTOs e Encapsulamento
* **Antes:** A entidade JPA `Contato` estava exposta diretamente à web, com atributos `public` e recebendo dados primitivos ruins (`String` para data e `String` para booleanos).
* **Depois:** Criação de `ContatoRequestDTO` e `ContatoResponseDTO` usando *Java Records*. A entidade agora possui encapsulamento (`private`), `LocalDateTime` para datas, `boolean` para o status e um `Enum` (`TipoContato`) limitando os valores de categoria.

### 3. Validação Elegante (Bean Validation)
* **Antes:** Dezenas de blocos `if/else` espalhados pelo Controller para validar e-mails vazios, sem '@', ou nomes curtos.
* **Depois:** Uso de anotações limpas no DTO (`@NotBlank`, `@Email`, `@Size`, `@NotNull`).

### 4. Tratamento Global de Exceções
* **Antes:** Uso de `try/catch(Exception e)` genéricos que engoliam erros e devolviam *Strings* concatenadas no corpo do HTTP 500.
* **Depois:** Implementação do `@RestControllerAdvice` (`GlobalExceptionHandler`) para capturar exceções customizadas (`RegraNegocioException`) e erros de validação, retornando um JSON padronizado e limpo.

### 5. Buscas Dinâmicas Avançadas
* **Antes:** O sistema trazia **todo** o banco de dados para a memória (`repo.findAll()`) apenas para fazer um `for` e filtrar strings.
* **Depois:** Implementação de **Spring Data Specifications** (`JpaSpecificationExecutor`), gerando as consultas diretamente no SQL de forma otimizada, eliminando o acoplamento de `if/else`.

---

## ⚙️ Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone [https://github.com/SEU_USUARIO/NOME_DO_REPOSITORIO.git](https://github.com/SEU_USUARIO/NOME_DO_REPOSITORIO.git)

2. Entre na pasta do projeto:
   ```bash
   cd NOME_DO_REPOSITORIO

3. Execute com o Maven:
   ```bash
   mvn spring-boot:run

4. A API estará disponível em `http://localhost:8080`.

O banco de dados H2 (em memória) pode ser acessado em `http://localhost:8080/h2` (Usuário: `sa`, Senha: em branco).

---

## 📡 Endpoints da API

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **POST** | `/contatos/incluir` | Cria um novo contato. |
| **GET** | `/contatos/listar` | Retorna todos os contatos. |
| **GET** | `/contatos/pesquisar?tipoBusca=nome&valor=joao` | Busca dinâmica por `nome`, `email`, `tel`, `tipo` ou `id`. |
| **PUT** | `/contatos/editar/{id}` | Atualiza os dados de um contato existente. |
| **DELETE** | `/contatos/excluir/{id}` | Remove um contato (impede exclusão de familiares). |

### Exemplo de Payload (POST e PUT)

```json
{
  "nome": "João Silva",
  "tel": "11999998888",
  "email": "joao@email.com",
  "endereco": "Rua A, 100",
  "idade": 30,
  "tipo": "AMIGO",
  "ativo": true
}
```

---
## 👨‍💻 Autor

**João Gabriel Ferreira Seixas**
* LinkedIn: https://www.linkedin.com/in/joaogabrielferreiraseixas
* GitHub: https://github.com/noahcollin
