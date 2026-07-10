package net.financeiro;

import net.financeiro.exceptions.NomeInvalidoException;
import net.financeiro.model.Categoria_Item;
import net.financeiro.model.Produto;
import net.financeiro.service.Categoria_ItemService;
import net.financeiro.service.ProdutosService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NomeInvalidoException {
        Scanner sc = new Scanner(System.in);
        Categoria_ItemService categoria_itemService = new Categoria_ItemService();
        ProdutosService produtosService = new ProdutosService();
        int opc;
        do{
            System.out.println("Insira a opção desejada:" +
                    "\n1 - Inserir uma categoria de produto" +
                    "\n2 - Listar todas as categorias de produto" +
                    "\n3 - Atualizar uma categoria de produto" +
                    "\n4 - Deletar uma categoria de produto" +
                    "\n5 - Maiores vendas por categoria de produto" +
                    "\n6 - Menores vendas por categoria de produto" +
                    "\n7 - Média vendas por categoria de produto" +
                    "\n8 - Inserir um produto" +
                    "\n9 - Listar todos os produtos" +
                    "\n10 - Atualizar um produto" +
                    "\n11 - Deletar um produto" +
                    "\n12 - Maiores vendas por produto" +
                    "\n13 - Menores vendas por produto" +
                    "\n14 - Média vendas por produto" +
                    "\n0 - Sair"
            );
            opc = sc.nextInt();
            sc.nextLine();
            String categoriaNome, produtoNome, produtoDescricao = null;
            Long id_categoria_item, id_produto;
            double produtoValor;
            int produtoQuantidade, descOpc;
            switch(opc){
                case 1:
                    System.out.println("Insira o nome da categoria:");
                    categoriaNome = sc.nextLine();
                    System.out.println(categoria_itemService.inserir(new Categoria_Item(categoriaNome)));
                    break;
                case 2:
                    for(Categoria_Item cat : categoria_itemService.listarInfo()){
                        System.out.println(cat);
                    }
                    break;
                case 3:
                    System.out.println("Insira o ID da categoria:");
                    id_categoria_item = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Insira o nome da categoria:");
                    categoriaNome = sc.nextLine();
                    System.out.println(categoria_itemService.atualizar(new Categoria_Item(id_categoria_item, categoriaNome)));
                    break;
                case 4:
                    System.out.println("Insira o ID da categoria:");
                    id_categoria_item = sc.nextLong();
                    sc.nextLine();
                    categoria_itemService.deletar(id_categoria_item);
                    break;
                case 5:
                    System.out.println(categoria_itemService.maiorVenda());
                    break;
                case 6:
                    System.out.println(categoria_itemService.menorVenda());
                    break;
                case 7:
                    System.out.println(categoria_itemService.mediaVendas());
                    break;
                case 8:
                    System.out.println("Insira o nome do produto:");
                    produtoNome = sc.nextLine();
                    System.out.println("Você deseja inserir uma descrição? (1 - Sim, Outro número - Não)");
                    descOpc = sc.nextInt();
                    sc.nextLine();
                    if(descOpc == 1){
                        System.out.println("Insira a descrição:");
                        produtoDescricao = sc.nextLine();
                    }
                    System.out.println("Insira o valor unitário do produto:");
                    produtoValor = sc.nextInt();
                    System.out.println("Insira a quantidade desses produtos em estoque:");
                    produtoQuantidade = sc.nextInt();
                    System.out.println("Insira o ID da categoria do produto:");
                    id_categoria_item = sc.nextLong();
                    System.out.println(produtosService.inserir(new Produto(id_categoria_item, produtoNome, produtoDescricao, produtoValor, produtoQuantidade)));
                    break;
                case 9:
                    for(Produto prod : produtosService.listarInfo()){
                        System.out.println(prod);
                    }
                    break;
                case 10:
                    System.out.println("Insira o ID do produto:");
                    id_produto = sc.nextLong();
                    sc.nextLine();
                    System.out.println("Insira o nome do produto:");
                    produtoNome = sc.nextLine();
                    System.out.println("Você deseja inserir uma descrição? (1 - Sim, Outro número - Não)");
                    descOpc = sc.nextInt();
                    sc.nextLine();
                    if(descOpc == 1){
                        System.out.println("Insira a descrição:");
                        produtoDescricao = sc.nextLine();
                    }
                    System.out.println("Insira o valor unitário do produto:");
                    produtoValor = sc.nextInt();
                    System.out.println("Insira a quantidade desses produtos em estoque:");
                    produtoQuantidade = sc.nextInt();
                    System.out.println("Insira o ID da categoria do produto:");
                    id_categoria_item = sc.nextLong();
                    System.out.println(produtosService.atualizar(new Produto(id_produto, id_categoria_item, produtoNome, produtoDescricao, produtoValor, produtoQuantidade)));
                    break;
                case 11:
                    System.out.println("Insira o ID do produto:");
                    id_produto = sc.nextLong();
                    sc.nextLine();
                    produtosService.deletar(id_produto);
                    break;
                case 12:
                    System.out.println(produtosService.maiorVenda());
                    break;
                case 13:
                    System.out.println(produtosService.menorVenda());
                    break;
                case 14:
                    System.out.println(produtosService.mediaVendas());
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção não encontrada!");
            }
        } while(opc != 0);
    }
}