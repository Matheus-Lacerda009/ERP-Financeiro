
CALL registrar_venda_com_lock(1, 1, 5, 3, 1, 1, 1);

SELECT id_produto, nome, quantidade_estoque FROM Produto WHERE id_produto = 5;

CALL registrar_venda_com_lock(1, 1, 5, 999999, 1, 1, 1);