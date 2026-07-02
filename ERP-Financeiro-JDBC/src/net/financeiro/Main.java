package net.financeiro;

import net.financeiro.exceptions.NomeInvalidoException;
import net.financeiro.model.Categoria_Item;
import net.financeiro.service.Categoria_ItemService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws NomeInvalidoException {
        Scanner sc = new Scanner(System.in);
        Categoria_ItemService categoria_itemService = new Categoria_ItemService();
        int opc;
        do{
            System.out.println("Insira a opção desejada:" +
                    "\n1 - Inserir uma categoria de produto" +
                    "\n2 - Listar todas as categorias de produto" +
                    "\n3 - Atualizar uma categoria de produto" +
                    "\n4 - Deletar uma categoria de produto"
            );
            opc = sc.nextInt();
            sc.nextLine();
            String categoriaNome;
            Long id_categoria_item;
            switch(opc){
                case 1:
                    System.out.println("Insira o nome da categoria:");
                    categoriaNome = sc.nextLine();
                    categoria_itemService.inserir(new Categoria_Item(categoriaNome));
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
                    categoria_itemService.atualizar(new Categoria_Item(id_categoria_item, categoriaNome));
                    break;
                case 4:
                    System.out.println("Insira o ID da categoria:");
                    id_categoria_item = sc.nextLong();
                    sc.nextLine();
                    categoria_itemService.deletar(id_categoria_item);
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