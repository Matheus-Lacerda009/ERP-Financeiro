-- Chamada de teste: vende 3 unidades do produto 5
CALL registrar_venda_com_lock(1, 1, 5, 3, 1, 1, 1);

-- Confere se o estoque desse produto realmente baixou
SELECT id_produto, nome, quantidade_estoque FROM Produto WHERE id_produto = 5;

-- Tenta vender mais do que existe em estoque (deve dar erro "Estoque insuficiente")
CALL registrar_venda_com_lock(1, 1, 5, 999999, 1, 1, 1);