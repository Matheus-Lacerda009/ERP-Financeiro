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
            System.out.println("Insira a opção desejada:\n" +
                    "1 - Inserir uma categoria de produto\n");
            opc = sc.nextInt();
            sc.nextLine();
            switch(opc){
                case 1:
                    System.out.println("Insira o nome da categoria:");
                    String catNome = sc.nextLine();
                    categoria_itemService.inserir(new Categoria_Item(catNome));
                    break;
            }
        } while(opc != 0);
    }
}