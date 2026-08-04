create database ERP_Financeiro;
use ERP_Financeiro;

create table Funcionario(
	id_funcionario int auto_increment primary key,
    nome varchar(100) not null,
    cpf varchar(11) unique not null,
    telefone varchar(11) unique not null,
    email varchar(100) unique not null
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
    cnpj_cpf varchar(14) unique not null,
    telefone varchar(11) unique not null,
    email varchar(100) unique not null
);

create table Categoria_Item(
	id_categoria_item int auto_increment primary key,
    nome varchar(100) not null
);

create table Conta_Bancaria(
	id_caixa int auto_increment primary key,
    nome_banco varchar(100) not null,
    numero_conta int unique
);

create table Forma_Pagamento(
	id_forma_pagamento int auto_increment primary key,
    nome varchar(100) unique not null
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







-- (depois de todas as CREATE TABLE)

CREATE INDEX idx_operacao_data ON Operacao(data_operacao);
CREATE INDEX idx_operacao_status ON Operacao(status_operacao);
CREATE INDEX idx_fornecedor_nome ON Fornecedor_Cliente(razao_social_nome);
CREATE INDEX idx_produto_nome ON Produto(nome);
CREATE INDEX idx_itens_produto_qtd ON Itens_Operacao(id_produto, quantidade_produtos);



DELIMITER $$

CREATE PROCEDURE registrar_venda_com_lock (
    IN p_id_fornecedor_cliente INT,
    IN p_id_funcionario INT,
    IN p_id_produto INT,
    IN p_quantidade INT,
    IN p_id_caixa INT,
    IN p_id_forma_pagamento INT,
    IN p_id_folha_pagamento INT
)
BEGIN
    DECLARE v_id_operacao INT;
    DECLARE v_estoque_atual INT;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;

    START TRANSACTION;

    SELECT quantidade_estoque INTO v_estoque_atual
    FROM Produto
    WHERE id_produto = p_id_produto
    FOR UPDATE;

    IF v_estoque_atual < p_quantidade THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Estoque insuficiente para concluir esta venda';
    END IF;

    INSERT INTO Operacao (data_operacao, status_operacao, id_fornecedor_cliente, id_funcionario)
    VALUES (NOW(), 'Concluída', p_id_fornecedor_cliente, p_id_funcionario);

    SET v_id_operacao = LAST_INSERT_ID();

    INSERT INTO Itens_Operacao (quantidade_produtos, id_produto, id_operacao)
    VALUES (p_quantidade, p_id_produto, v_id_operacao);

    INSERT INTO Fluxo_Caixa (tipo_operacao, parcelas, id_caixa, id_forma_pagamento, id_operacao, id_folha_pagamento)
    VALUES ('Venda', 1, p_id_caixa, p_id_forma_pagamento, v_id_operacao, p_id_folha_pagamento);

    COMMIT;
END$$

DELIMITER ;