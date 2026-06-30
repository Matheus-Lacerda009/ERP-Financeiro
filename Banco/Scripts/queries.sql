use ERP_Financeiro;

-- WEG ERP VALENTIM

-- tabela geral -> ver as principais informações por: categoria, funcionário, produto e fornecedor


-- Categoria 
SELECT * FROM `Categoria_Item`;
SELECT nome FROM `Categoria_Item`;


-- Funcionário
SELECT * FROM `Funcionario`;
select nome from `Funcionario`;
select cpf from `Funcionario`;
select telefone from `Funcionario`;
select email from `Funcionario`;



-- Produto 
SELECT * FROM `Produto`;
SELECT nome FROM `Produto`;
SELECT valor FROM `Produto`;
SELECT descricao FROM `Produto`;
SELECT quantidade_estoque FROM `Produto`;





-- Fornecedor_cliente 
SELECT * FROM `Fornecedor_Cliente`;
SELECT razao_social_nome FROM `Fornecedor_Cliente`;
SELECT cnpj_cpf FROM `Fornecedor_Cliente`;
SELECT telefone FROM `Fornecedor_Cliente`;
SELECT email FROM `Fornecedor_Cliente`;











-- maior venda -> ver as maiores vendas por: categoria, funcionário, produto e fornecedor

-- categoria
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

-- funcionarios
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

-- produto
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

-- fornecedor_cliente
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






-- menor venda -> ver as menores vendas por: categoria, funcionário, produto e fornecedor

-- categoria
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

-- funcionarios
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

-- produto
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

-- fornecedor_cliente
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





-- média venda -> ver a média das vendas por: categoria, funcionário, produto e fornecedor

-- categoria
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

-- funcionarios
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

-- produto
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

-- fornecedor_cliente
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





-- pagamento -> calcula o salário líquido do funcionário, (pode ser adiocionado algo para dar uma folha de pagamento mais completa do funcionário)

create VIEW valor_salario as
select fp.horas_trabalhadas * fp.valor_hora as salario, fp.id_funcionario
from Folha_Pagamento as fp;

SELECT * from valor_salario;

select valor_salario.salario, f.nome
from
    Funcionario as f
    join valor_salario on valor_salario.id_funcionario = f.id_funcionario
order by valor_salario.salario desc;