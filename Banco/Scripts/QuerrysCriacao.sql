

--View para ver o saldo atual :
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

--QUERRYS :


--Fluxo de caixa :

-- entradas realizadas
SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda
FROM Fluxo_Caixa fc
JOIN Operacao o ON o.id_operacao = fc.id_operacao
JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao

WHERE fc.tipo_operacao = 'Venda'
  AND o.status_operacao = 'Concluída'
  AND o.data_operacao >= NOW() - INTERVAL ? DAY;



-- entradas previstas
SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda
FROM Fluxo_Caixa fc
JOIN Operacao o ON o.id_operacao = fc.id_operacao
JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao

WHERE fc.tipo_operacao = 'Venda'
  AND o.data_operacao >= NOW() - INTERVAL ? DAY;


-- saídas realizada
SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda
FROM Fluxo_Caixa fc
JOIN Operacao o ON o.id_operacao = fc.id_operacao
JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao

WHERE fc.tipo_operacao = 'Compra'
  AND o.status_operacao = 'Concluída'
  AND o.data_operacao >= NOW() - INTERVAL ? DAY;

-- saídas previstas
SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda
FROM Fluxo_Caixa fc
JOIN Operacao o ON o.id_operacao = fc.id_operacao
JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao

WHERE fc.tipo_operacao = 'Compra'
  AND o.data_operacao >= NOW() - INTERVAL ? DAY;


-- saldo inicial
SELECT * FROM SaldoAtual;


-- Gerador de notas fiscais:
SELECT * FROM ViewNotaFiscal;



-- Lista da compra/venda -> deve conter id do item, nome e quantidade, valor produto1
SELECT
    io.id_itens_operacao AS id_item,
    p.nome AS nome_produto,
    io.quantidade_produtos AS quantidade,
    p.valor AS valor_produto
FROM Itens_Operacao io
JOIN Produto p ON io.id_produto = p.id_produto;

