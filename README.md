# 💰 Financeiro

Sistema de gestão financeira em **Java puro** (sem framework), voltado para controle de operações de compra/venda, fluxo de caixa, contas bancárias, folha de pagamento, estoque de produtos e emissão de notas fiscais.

O projeto segue uma arquitetura em camadas (**Model → Repository → Service**), com acesso a banco de dados via **JDBC** e regras de negócio centralizadas na camada de serviço.

---

## 📑 Sumário

- [Arquitetura](#-arquitetura)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Domínio da aplicação](#-domínio-da-aplicação)
- [Tratamento de erros](#-tratamento-de-erros)
- [Segurança](#-segurança)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração](#-configuração)
- [Banco de dados](#-banco-de-dados)
- [Como executar](#-como-executar)
- [Roadmap / pontos de atenção](#-roadmap--pontos-de-atenção)

---

## 🏗 Arquitetura

O projeto segue o padrão **em camadas**, sem uso de frameworks como Spring — toda a orquestração (conexão, injeção das dependências, tratamento de exceções) é feita manualmente:

```
Model  →  representa as entidades do domínio (POJOs)
Repository  →  executa as consultas SQL via JDBC (PreparedStatement)
Service  →  concentra as regras de negócio e validações antes de chamar o Repository
```

Fluxo típico de uma operação:

```
Main.java → Service → Repository → Conexao (JDBC) → Banco de Dados
```

As exceções de negócio (dado inválido, FK inexistente, permissão negada etc.) são lançadas na camada de **Service**, mantendo o **Repository** responsável apenas pela execução das queries.

---

## 📂 Estrutura do projeto

```
financeiro/
├── Main.java                  # Ponto de entrada da aplicação
├── connection/
│   ├── Conexao.java           # Abertura de conexão JDBC via variáveis de ambiente (.env)
│   └── RateLimiting.java      # Limitador de tentativas de login (proteção contra brute-force)
├── model/                     # Entidades do domínio
│   ├── Usuario.java
│   ├── Funcionario.java
│   ├── Fornecedor_Cliente.java
│   ├── Produto.java
│   ├── Categoria_Item.java
│   ├── Operacao.java
│   ├── Itens_Operacao.java
│   ├── Conta_Bancaria.java
│   ├── Forma_Pagamento.java
│   ├── Fluxo_Caixa.java
│   ├── Folha_Pagamento.java
│   ├── Valor_Salario.java
│   ├── Valor_pVenda.java
│   ├── SaldoAtual.java
│   └── ViewNotaFiscal.java
├── repository/                # Acesso a dados (JDBC / SQL puro)
│   └── ...um repository por entidade, incluindo ClienteRepository e
│      FornecedorRepository (ambos sobre a tabela Fornecedor_Cliente)
├── service/                   # Regras de negócio e validações
│   └── ...um service por entidade
└── exceptions/                # Exceções de negócio customizadas
    ├── AtributoInvalidoException.java
    ├── AvisoException.java
    ├── FkNaoEncontradaException.java
    ├── IdNaoEncontradoException.java
    ├── NadaInseridoException.java
    ├── PermissaoNegadaException.java
    └── ValorInvalidoException.java
```

---

## 🧾 Domínio da aplicação

| Entidade | Responsabilidade |
|---|---|
| **Usuario** | Login e cadastro de usuários, com níveis de permissão (1 a 3) e senha protegida com hash |
| **Funcionario** | Cadastro de colaboradores da empresa |
| **Fornecedor_Cliente** | Cadastro único de fornecedores e clientes (diferenciados por flag `fornecedor`), com repositories separados (`FornecedorRepository` / `ClienteRepository`) para cada visão |
| **Produto** / **Categoria_Item** | Catálogo de produtos, estoque e categorização |
| **Operacao** | Representa uma operação de compra ou venda (vinculada a fornecedor/cliente e funcionário), com status `Pendente` ou `Concluída` |
| **Itens_Operacao** | Itens (produtos e quantidades) que compõem uma operação |
| **Conta_Bancaria** | Contas bancárias da empresa (caixas) |
| **Forma_Pagamento** | Formas de pagamento aceitas |
| **Fluxo_Caixa** | Movimentações financeiras (entradas/saídas), vinculando conta bancária, forma de pagamento, folha de pagamento e operação |
| **Folha_Pagamento** / **Valor_Salario** | Cálculo e histórico de pagamento de funcionários |
| **Valor_pVenda** | Valor de venda associado a uma operação |
| **SaldoAtual** | Consulta do saldo consolidado da empresa (com alerta quando negativo) |
| **ViewNotaFiscal** | Visão consolidada para emissão de nota fiscal (dados do parceiro, forma de pagamento, parcelas etc.) |

O `Fluxo_CaixaService` também expõe consultas analíticas de fluxo de caixa por período:
`entradas_realizadas`, `entradas_previstas`, `saidas_realizadas`, `saidas_previstas`.

### Exclusão lógica (soft delete)

Registros não são removidos fisicamente do banco. As entidades principais possuem uma coluna `ativo`, controlada pelos métodos `deletar()` (marca como inativo) e `reativar()` (reverte a inativação) presentes nos repositories/services.

---

## ⚠️ Tratamento de erros

O projeto usa exceções de negócio dedicadas em vez de mensagens genéricas:

- **`NadaInseridoException`** — campo obrigatório vazio/nulo
- **`ValorInvalidoException`** — valor fora do intervalo permitido (ex.: permissão de usuário)
- **`AtributoInvalidoException`** — atributo com valor inesperado (ex.: status de operação diferente de `Pendente`/`Concluída`)
- **`FkNaoEncontradaException`** — chave estrangeira referenciada não existe (ex.: id de fornecedor inexistente)
- **`IdNaoEncontradoException`** — registro não encontrado pelo id informado
- **`PermissaoNegadaException`** — usuário sem permissão de administrador para a operação
- **`AvisoException`** — aviso de negócio não bloqueante (ex.: saldo negativo)

> ⚠️ **Nota de manutenção:** algumas validações de status usam `!a.equals(x) || !a.equals(y)` (ex.: em `OperacaoService`), o que sempre resulta em `true` e bloqueia valores válidos. Vale revisar essa lógica para `!a.equals(x) && !a.equals(y)`.

---

## 🔐 Segurança

- Senhas de usuário são armazenadas com hash **BCrypt** (`org.mindrot.jbcrypt`), nunca em texto puro.
- Todas as consultas usam **`PreparedStatement`**, prevenindo SQL Injection.
- Login possui **rate limiting** (`RateLimiting.java`): no máximo **5 tentativas** a cada **5 minutos** por instância.
- Operações administrativas (ex.: cadastro de novos usuários) exigem validação de que o usuário solicitante é administrador (`isAdm`).
- Credenciais de banco de dados **não ficam hardcoded** — são carregadas de variáveis de ambiente via `.env` (biblioteca `java-dotenv`).

---

## 🧰 Pré-requisitos

- **JDK 17+**
- Um **banco de dados relacional** (o driver deve ser adicionado às dependências; o padrão de queries — `boolean`/`ativo`, `Statement.RETURN_GENERATED_KEYS` — é compatível com **MySQL/MariaDB**)
- Gerenciador de dependências: **Maven** ou **Gradle** (o projeto ainda não inclui `pom.xml`/`build.gradle` — veja [Roadmap](#-roadmap--pontos-de-atenção))

### Dependências utilizadas no código

| Biblioteca | Uso |
|---|---|
| [`io.github.cdimascio:dotenv-java`](https://github.com/cdimascio/dotenv-java) | Carregar variáveis de ambiente do arquivo `.env` |
| [`org.mindrot:jbcrypt`](https://github.com/jeremyh/jBCrypt) | Hash e verificação de senhas |
| Driver JDBC do seu banco (ex.: `mysql-connector-j`) | Conexão com o banco de dados |

---

## ⚙️ Configuração

Crie um arquivo `.env` na raiz do projeto com as credenciais de acesso ao banco:

```env
URL=jdbc:mysql://localhost:3306/financeiro
USER=seu_usuario
PASSWORD=sua_senha
```

Essas variáveis são lidas por `Conexao.java` através do `Dotenv.load()`.

> **Importante:** nunca faça commit do arquivo `.env`. Adicione-o ao `.gitignore`.

---

## 🗄 Banco de dados

O schema não está incluído neste repositório. Com base nas queries do código, as principais tabelas esperadas são:

```
Usuarios, Funcionario, Fornecedor_Cliente, Produto, Categoria_Item,
Operacao, Itens_Operacao, Conta_Bancaria, Forma_Pagamento,
Fluxo_Caixa, Folha_Pagamento
```

A maioria das tabelas principais possui uma coluna booleana `ativo` (usada na exclusão lógica) e chave primária autoincremento (usada com `Statement.RETURN_GENERATED_KEYS`).

> Recomenda-se criar um script `schema.sql` (DDL) versionado junto ao projeto para reprodutibilidade do ambiente.

---

## ▶️ Como executar

1. Clone o repositório e importe-o na sua IDE (IntelliJ, Eclipse, VS Code com extensão Java).
2. Adicione as dependências (`dotenv-java`, `jbcrypt`, driver JDBC) ao classpath — ou configure um `pom.xml`/`build.gradle`, se preferir migrar para um gerenciador de dependências.
3. Crie o banco de dados e as tabelas correspondentes (ver seção acima).
4. Configure o arquivo `.env` na raiz do projeto.
5. Execute `Main.java`.

```bash
# Exemplo de compilação/execução manual (ajustando classpath para os .jar das dependências)
javac -cp ".:libs/*" -d out $(find financeiro -name "*.java")
java  -cp "out:libs/*" net.financeiro.Main
```

> `Main.java` atualmente está vazio — é o ponto de partida para implementar o menu/CLI (ou outra camada de apresentação) que consome os `Service`s.

---
## 📄 Licença

Não definida — adicione uma licença (ex.: MIT) caso pretenda distribuir ou abrir o projeto publicamente.
