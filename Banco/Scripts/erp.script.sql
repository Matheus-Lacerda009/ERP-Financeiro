-- Active: 1782855798917@@erp-financeiro-estudante-0530.a.aivencloud.com@26374@ERP_Financeiro
create database ERP_Financeiro;
use ERP_Financeiro;

create table Funcionario(
	id_funcionario int auto_increment primary key,
    nome varchar(100) not null,
    cpf varchar(11) not null,
    telefone varchar(11) not null,
    email varchar(100) not null
);

create table Folha_Pagamento(
	id_folha_pagamento int auto_increment primary key,
    descontos decimal(10, 2) not null,
    data_entrada datetime not null,
    horas_trabalhadas decimal(5, 2) not null,
    valor_hora decimal(6, 2) not null,
    id_funcionario int not null,
    foreign key(id_funcionario) references Funcionario(id_funcionario)
);

create table Fornecedor_Cliente(
	id_fornecedor_cliente int auto_increment primary key,
    razao_social_nome varchar(100) not null,
    cnpj_cpf varchar(14) not null,
    telefone varchar(11) not null,
    email varchar(100) not null
);

create table Categoria_Item(
	id_categoria_item int auto_increment primary key,
    nome varchar(100) not null
);

create table Conta_Bancaria(
	id_caixa int auto_increment primary key,
    nome_banco varchar(100) not null,
    numero_conta int
);

create table Forma_Pagamento(
	id_forma_pagamento int auto_increment primary key,
    nome varchar(100) not null
);

create table Produto(
	id_produto int auto_increment primary key,
    nome varchar(100) not null,
    valor decimal(8, 2) not null,
    descricao text,
    quantidade_estoque int not null,
    id_categoria_item int not null,
    foreign key(id_categoria_item) references Categoria_Item(id_categoria_item)
);

create table Operacao(
	id_operacao int auto_increment primary key,
    data_operacao datetime not null,
    status_operacao enum('Pendente', 'Concluída') not null,
    id_fornecedor_cliente int not null,
    foreign key(id_fornecedor_cliente) references Fornecedor_Cliente(id_fornecedor_cliente),
    id_funcionario int not null,
    foreign key(id_funcionario) references Funcionario(id_funcionario)
);

create table Itens_Operacao(
	id_itens_operacao int auto_increment primary key,
    quantidade_produtos int not null,
    id_produto int not null,
    foreign key(id_produto) references Produto(id_produto),
    id_operacao int not null,
    foreign key(id_operacao) references Operacao(id_operacao)
);

create table Fluxo_Caixa(
	id_fluxo_caixa int auto_increment primary key,
    tipo_operacao enum('Compra', 'Venda') not null,
    parcelas int not null,
    id_caixa int not null,
    foreign key(id_caixa) references Conta_Bancaria(id_caixa),

    id_forma_pagamento int not null,
    foreign key(id_forma_pagamento) references Forma_Pagamento(id_forma_pagamento),

    id_operacao int not null,
    foreign key(id_operacao) references Operacao(id_operacao),

    id_folha_pagamento int not null,
    foreign key(id_folha_pagamento) references Folha_Pagamento(id_folha_pagamento)
);
