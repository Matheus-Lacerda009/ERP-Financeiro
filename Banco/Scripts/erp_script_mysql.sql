--Cria o banco "ERP_Financeiro":
create database ERP_Financeiro;


--Seleciona para uso o banco "ERP_Financeiro":
use ERP_Financeiro;

-------------------------------------------------------------------------------------------------------------------------------------

--Criação de tabelas/entidadades e entidades associativas:

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

-------------------------------------------------------------------------------------------------------------------------------------

--Modificações realizados no banco conforme necessidade ao decorrer do tempo:

alter table Fornecedor_Cliente add column fornecedor bool not null;


set SQL_SAFE_UPDATES = 0;


update Fornecedor_Cliente set fornecedor = true where length(cnpj_cpf) > 11;


alter table Funcionario add column ativo bool default false;


update Funcionario set ativo = true;


alter table Folha_Pagamento add column ativo bool default true;


alter table Fornecedor_Cliente add column ativo bool default true;


alter table Categoria_Item add column ativo bool default true;


alter table Conta_Bancaria add column ativo bool default true;


alter table Forma_Pagamento add column ativo bool default true;


alter table Produto add column ativo bool default true;


alter table Operacao add column ativo bool default true;


alter table Itens_Operacao add column ativo bool default true;


alter table Fluxo_Caixa add column ativo bool default true;

-------------------------------------------------------------------------------------------------------------------------------------

--Yago:
--Views:

--View para ver o saldo atual:
CREATE VIEW SaldoAtual AS
SELECT
    (
        SELECT SUM(p.valor * io.quantidade_produtos)
        FROM Produto p
        JOIN Itens_Operacao io ON p.id_produto = io.id_produto
        JOIN Operacao o ON o.id_operacao = io.id_operacao
        WHERE o.status_operacao = 'Concluída'
    ) - (
        SELECT SUM(fp.horas_trabalhadas * fp.valor_hora)
        FROM Folha_Pagamento fp
    ) AS Saldo_Total;


--View para valor de venda por produto :
CREATE VIEW Valor_pVenda AS
SELECT
    io.id_operacao,
    SUM(io.quantidade_produtos * p.valor) AS valor_total
FROM Itens_Operacao io
JOIN Produto p ON io.id_produto = p.id_produto
GROUP BY io.id_operacao;


--View das notas fiscais :
CREATE VIEW ViewNotaFiscal AS
SELECT
    fc.id_fluxo_caixa,
    o.id_operacao AS numero_nota_fiscal,

    o.data_operacao AS data_emissao, -- Adicionei a data que é fundamental para NF
    fc_cli.id_fornecedor_cliente AS id_parceiro,
    fc_cli.razao_social_nome AS nome_empresa_parceira,
    fc_cli.cnpj_cpf AS documento_parceiro,
    fc_cli.email AS email_parceiro,
    p.id_produto,
    p.nome AS nome_produto,
    io.quantidade_produtos AS quantidade,
    p.valor AS valor_unitario,
    (io.quantidade_produtos * p.valor) AS valor_total_item,
    fp.nome AS forma_pagamento,
    fc.parcelas,
    cb.nome_banco AS banco_recebimento

FROM Itens_Operacao io
JOIN Produto p ON io.id_produto = p.id_produto
JOIN Operacao o ON io.id_operacao = o.id_operacao
JOIN Fluxo_Caixa fc ON o.id_operacao = fc.id_operacao
JOIN Fornecedor_Cliente fc_cli ON o.id_fornecedor_cliente = fc_cli.id_fornecedor_cliente
JOIN Funcionario f ON o.id_funcionario = f.id_funcionario
JOIN Forma_Pagamento fp ON fc.id_forma_pagamento = fp.id_forma_pagamento
JOIN Conta_Bancaria cb ON fc.id_caixa = cb.id_caixa;

-------------------------------------------------------------------------------------------------------------------------------------

--Queries:

--Fluxo de caixa :

--Entradas realizadas:
SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda
FROM Fluxo_Caixa fc
JOIN Operacao o ON o.id_operacao = fc.id_operacao
JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao

WHERE fc.tipo_operacao = 'Venda'
  AND o.status_operacao = 'Concluída'
  AND o.data_operacao >= NOW() - INTERVAL ? DAY;


--Entradas previstas:
SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda
FROM Fluxo_Caixa fc
JOIN Operacao o ON o.id_operacao = fc.id_operacao
JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao

WHERE fc.tipo_operacao = 'Venda'
  AND o.data_operacao >= NOW() - INTERVAL ? DAY;


--Saídas realizadas:
SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda
FROM Fluxo_Caixa fc
JOIN Operacao o ON o.id_operacao = fc.id_operacao
JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao

WHERE fc.tipo_operacao = 'Compra'
  AND o.status_operacao = 'Concluída'
  AND o.data_operacao >= NOW() - INTERVAL ? DAY;


--Saídas previstas:
SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda
FROM Fluxo_Caixa fc
JOIN Operacao o ON o.id_operacao = fc.id_operacao
JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao

WHERE fc.tipo_operacao = 'Compra'
  AND o.data_operacao >= NOW() - INTERVAL ? DAY;


--Saldo inicial:
SELECT * FROM SaldoAtual;


--Gerador de notas fiscais:
SELECT * FROM ViewNotaFiscal;


--Lista da compra/venda -> deve conter id do item, nome e quantidade, valor produto:
SELECT
    io.id_itens_operacao AS id_item,
    p.nome AS nome_produto,
    io.quantidade_produtos AS quantidade,
    p.valor AS valor_produto
FROM Itens_Operacao io
JOIN Produto p ON io.id_produto = p.id_produto;

-------------------------------------------------------------------------------------------------------------------------------------

--Letícia
--Queries

--Tabela geral -> ver as principais informações por: categoria, funcionário, produto e fornecedor:

--Categoria:
SELECT * FROM `Categoria_Item`;


SELECT nome FROM `Categoria_Item`;

--Funcionário:
SELECT * FROM `Funcionario`;


select nome from `Funcionario`;


select cpf from `Funcionario`;


select telefone from `Funcionario`;


select email from `Funcionario`;

--Produto:
SELECT * FROM `Produto`;


SELECT nome FROM `Produto`;


SELECT valor FROM `Produto`;


SELECT descricao FROM `Produto`;


SELECT quantidade_estoque FROM `Produto`;

--Fornecedor_cliente:
SELECT * FROM `Fornecedor_Cliente`;


SELECT razao_social_nome FROM `Fornecedor_Cliente`;


SELECT cnpj_cpf FROM `Fornecedor_Cliente`;


SELECT telefone FROM `Fornecedor_Cliente`;


SELECT email FROM `Fornecedor_Cliente`;

--Maior venda -> ver as maiores vendas por: categoria, funcionário, produto e fornecedor:

--Categoria:
select sum(
        p.valor * io.quantidade_produtos
    ) as 'Venda por categoria', ci.nome as 'Nome categoria'
from
    Produto as p
    join Itens_Operacao as io on io.id_produto = p.id_produto
    join `Categoria_Item` as ci on ci.id_categoria_item = p.id_categoria_item
group by
    ci.id_categoria_item
ORDER BY sum(
        p.valor * io.quantidade_produtos
    ) desc;


--Funcionarios:
SELECT sum(
        p.valor * i.quantidade_produtos
    ) as 'Venda por funcionários', f.nome as 'Nome funcionario'
from
    Produto as p
    join `Itens_Operacao` as i on i.id_produto = p.id_produto
    join `Operacao` as op on op.id_operacao = i.id_operacao
    join `Funcionario` as f on f.id_funcionario = op.id_funcionario
GROUP BY
    f.id_funcionario
ORDER BY sum(
        p.valor * i.quantidade_produtos
    ) desc;


--Produto:
SELECT sum(
        p.valor * i.quantidade_produtos
    ) as 'Venda por produto', p.nome as 'Nome produto'
from
    Produto as p
    join `Itens_Operacao` as i on i.id_produto = p.id_produto
GROUP BY
    p.id_produto
ORDER BY sum(
        p.valor * i.quantidade_produtos
    ) desc;


--Fornecedor_cliente:
SELECT sum(
        p.valor * i.quantidade_produtos
    ) as 'Venda por Fornecedores_Clientes', fc.razao_social_nome as 'Nome Fornecedor_Cliente '
from
    Produto as p
    join `Itens_Operacao` as i on i.id_produto = p.id_produto
    join `Operacao` as op on op.id_operacao = i.id_operacao
    join `Fornecedor_Cliente` as fc on fc.id_fornecedor_cliente = op.id_fornecedor_cliente
GROUP BY
    fc.id_fornecedor_cliente
ORDER BY sum(
        p.valor * i.quantidade_produtos
    ) desc;

--Menor venda -> ver as menores vendas por: categoria, funcionário, produto e fornecedor:

--Categoria:
select sum(
        p.valor * io.quantidade_produtos
    ) as 'Venda por categoria', ci.nome as 'Nome categoria'
from
    Produto as p
    join Itens_Operacao as io on io.id_produto = p.id_produto
    join `Categoria_Item` as ci on ci.id_categoria_item = p.id_categoria_item
group by
    ci.id_categoria_item
ORDER BY sum(
        p.valor * io.quantidade_produtos
    ) asc;


--Funcionarios:
SELECT sum(
        p.valor * i.quantidade_produtos
    ) as 'Venda por funcionários', f.nome as 'Nome funcionario'
from
    Produto as p
    join `Itens_Operacao` as i on i.id_produto = p.id_produto
    join `Operacao` as op on op.id_operacao = i.id_operacao
    join `Funcionario` as f on f.id_funcionario = op.id_funcionario
GROUP BY
    f.id_funcionario
ORDER BY sum(
        p.valor * i.quantidade_produtos
    ) asc;


--Produto:
SELECT sum(
        p.valor * i.quantidade_produtos
    ) as 'Venda por produto', p.nome as 'Nome produto'
from
    Produto as p
    join `Itens_Operacao` as i on i.id_produto = p.id_produto
GROUP BY
    p.id_produto
ORDER BY sum(
        p.valor * i.quantidade_produtos
    ) asc;


--Fornecedor_cliente:
SELECT sum(
        p.valor * i.quantidade_produtos
    ) as 'Venda por Fornecedores_Clientes', fc.razao_social_nome as 'Nome Fornecedor_Cliente '
from
    Produto as p
    join `Itens_Operacao` as i on i.id_produto = p.id_produto
    join `Operacao` as op on op.id_operacao = i.id_operacao
    join `Fornecedor_Cliente` as fc on fc.id_fornecedor_cliente = op.id_fornecedor_cliente
GROUP BY
    fc.id_fornecedor_cliente
ORDER BY sum(
        p.valor * i.quantidade_produtos
    ) asc;

--Média venda -> ver a média das vendas por: categoria, funcionário, produto e fornecedor:

--Categoria:
select avg(
        p.valor * io.quantidade_produtos
    ) as 'Média Venda por categoria', ci.nome as 'Nome categoria'
from
    Produto as p
    join Itens_Operacao as io on io.id_produto = p.id_produto
    join `Categoria_Item` as ci on ci.id_categoria_item = p.id_categoria_item
group by
    ci.id_categoria_item
ORDER BY sum(
        p.valor * io.quantidade_produtos
    ) asc;


--Funcionarios:
SELECT avg(
        p.valor * i.quantidade_produtos
    ) as 'Média Venda por funcionários', f.nome as 'Nome funcionario'
from
    Produto as p
    join `Itens_Operacao` as i on i.id_produto = p.id_produto
    join `Operacao` as op on op.id_operacao = i.id_operacao
    join `Funcionario` as f on f.id_funcionario = op.id_funcionario
GROUP BY
    f.id_funcionario
ORDER BY sum(
        p.valor * i.quantidade_produtos
    ) asc;


--Produto:
SELECT avg(
        p.valor * i.quantidade_produtos
    ) as 'Média Venda por produto', p.nome as 'Nome produto'
from
    Produto as p
    join `Itens_Operacao` as i on i.id_produto = p.id_produto
GROUP BY
    p.id_produto
ORDER BY sum(
        p.valor * i.quantidade_produtos
    ) asc;


--Fornecedor_cliente:
SELECT avg(
        p.valor * i.quantidade_produtos
    ) as 'Média Venda por Fornecedores_Clientes', fc.razao_social_nome as 'Nome Fornecedor_Cliente '
from
    Produto as p
    join `Itens_Operacao` as i on i.id_produto = p.id_produto
    join `Operacao` as op on op.id_operacao = i.id_operacao
    join `Fornecedor_Cliente` as fc on fc.id_fornecedor_cliente = op.id_fornecedor_cliente
GROUP BY
    fc.id_fornecedor_cliente
ORDER BY sum(
        p.valor * i.quantidade_produtos
    ) asc;

--Pagamento -> calcula o salário líquido do funcionário, (pode ser adiocionado algo para dar uma folha de pagamento mais completa do funcionário):

create VIEW valor_salario as
select fp.horas_trabalhadas * fp.valor_hora as salario, fp.id_funcionario
from Folha_Pagamento as fp;


SELECT * from valor_salario;


select valor_salario.salario, f.nome
from
    Funcionario as f
    join valor_salario on valor_salario.id_funcionario = f.id_funcionario
order by valor_salario.salario desc;

-------------------------------------------------------------------------------------------------------------------------------------

--Usuários:

--Cria a tabela para guardar os usuários:
CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(64) NOT NULL,
    permissao ENUM('R', 'RW', 'RWD') NOT NULL
);


insert into Usuarios(nome, senha, permissao) values ('', sha2('', 256), 3);


--Criação dos usuários do banco:
create user 'erp-financeiro'@'%' identified by 'valentim123';
grant select, insert, update, delete, create on ERP_Financeiro.* to 'erp-financeiro'@'%' ;


create user 'leticia'@'%' identified by 'valentim123';
grant all privileges on ERP_Financeiro.* to 'leticia'@'%' ;


create user 'matheus'@'%' identified by 'valentim123';
grant select, insert, update, create on ERP_Financeiro.* to 'matheus'@'%' ;


create user 'ryan'@'%' identified by 'valentim123';
grant select on ERP_Financeiro.* to 'ryan'@'%' ;


create user 'kaue'@'%' identified by 'valentim123';
grant select on ERP_Financeiro.* to 'kaue'@'%' ;


create user 'yago'@'%' identified by 'valentim123';
grant select on ERP_Financeiro.* to 'yago'@'%' ;


flush privileges;
------------------------------------------------------------------------------------------------------------------------------------


-- index trigger e transações/lock

CREATE INDEX idx_operacao_data ON Operacao(data_operacao);
CREATE INDEX idx_operacao_status ON Operacao(status_operacao);
CREATE INDEX idx_fornecedor_nome ON Fornecedor_Cliente(razao_social_nome);
CREATE INDEX idx_produto_nome ON Produto(nome);
CREATE INDEX idx_itens_produto_qtd ON Itens_Operacao(id_produto, quantidade_produtos);






DELIMITER //

CREATE TRIGGER trg_valida_email_fornecedor
BEFORE INSERT ON Fornecedor_Cliente
FOR EACH ROW
BEGIN
    IF NEW.email IS NOT NULL AND NEW.email NOT LIKE '%_@_%._%' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Erro: formato do e-mail inválido';
    END IF;
END//

CREATE TRIGGER trg_ajusta_estoque
AFTER INSERT ON Fluxo_Caixa
FOR EACH ROW
BEGIN
    IF NEW.tipo_operacao = 'Venda' THEN
        UPDATE Produto p
        JOIN Itens_Operacao io ON io.id_produto = p.id_produto
        SET p.quantidade_estoque = p.quantidade_estoque - io.quantidade_produtos
        WHERE io.id_operacao = NEW.id_operacao;
    ELSEIF NEW.tipo_operacao = 'Compra' THEN
        UPDATE Produto p
        JOIN Itens_Operacao io ON io.id_produto = p.id_produto
        SET p.quantidade_estoque = p.quantidade_estoque + io.quantidade_produtos
        WHERE io.id_operacao = NEW.id_operacao;
    END IF;
END//

DELIMITER ;








DELIMITER //

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
END//

DELIMITER ;