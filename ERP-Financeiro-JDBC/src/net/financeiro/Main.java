package net.financeiro;

import com.williamcallahan.tui4j.compat.bubbletea.Program;
import com.williamcallahan.tui4j.compat.bubbletea.ProgramOption;
import net.financeiro.menus.Orquestrador;
import net.financeiro.repository.FuncionarioRepository;
import net.financeiro.repository.ProdutosRepository;
import net.financeiro.service.Categoria_ItemService;
import net.financeiro.service.ClienteService;
import net.financeiro.service.FornecedorService;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        Categoria_ItemService categoriaItemService = new Categoria_ItemService();
        ClienteService clienteService = new ClienteService();
        FornecedorService fornecedorService = new FornecedorService();
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository();
        ProdutosRepository produtosRepository = new ProdutosRepository();
        Scanner sc = new Scanner(System.in);
        int opcao = -1;

        do{
            System.out.println("\nInsira qual tipo de menu você deseja:");
            System.out.println("1 - top");
            System.out.println("2 - marromenos");
            opcao = sc.nextInt();

            switch(opcao){
                case 1:
                    new Program(new Orquestrador(), ProgramOption.withAltScreen()).run();
                    break;
                case 2:
                    System.out.println("\nInsira qual classe você deseja utilizar:");
                    System.out.println("1 - Categoria_item");
                    System.out.println("2 - Cliente");
                    System.out.println("3 - Fornecedor");
                    System.out.println("4 - Funcionario");
                    System.out.println("5 - Produtos");
                    System.out.println("0 - Sair");
                    opcao = sc.nextInt();

                    switch(opcao){
                        case 1:
                            System.out.println("\n1 - Maior venda");
                            System.out.println("2 - Menor venda");
                            System.out.println("3 - Média venda");
                            opcao = sc.nextInt();

                            switch(opcao){
                                case 1:
                                    HashMap<String, List<String>> catItemMaiorVenda = categoriaItemService.maiorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < catItemMaiorVenda.get("VendaCategoria").size(); i++){
                                        System.out.println("Categoria: " + catItemMaiorVenda.get("NomeCategoria").get(i) + " Maior venda: " + catItemMaiorVenda.get("VendaCategoria").get(i));
                                    }
                                    break;
                                case 2:
                                    HashMap<String, List<String>> catItemMenorVenda = categoriaItemService.menorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < catItemMenorVenda.get("VendaCategoria").size(); i++){
                                        System.out.println("Categoria: " + catItemMenorVenda.get("NomeCategoria").get(i) + " Menor venda: " + catItemMenorVenda.get("VendaCategoria").get(i));
                                    }
                                    break;
                                case 3:
                                    HashMap<String, List<String>> catItemMediaVenda = categoriaItemService.menorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < catItemMediaVenda.get("VendaCategoria").size(); i++){
                                        System.out.println("Categoria: " + catItemMediaVenda.get("NomeCategoria").get(i) + " Média venda: " + catItemMediaVenda.get("VendaCategoria").get(i));
                                    }
                                    break;
                                default:
                                    System.out.println("\nInsira uma opção válida");
                            }
                            break;
                        case 2:
                            System.out.println("\n1 - Maior venda");
                            System.out.println("2 - Menor venda");
                            System.out.println("3 - Média venda");
                            opcao = sc.nextInt();

                            switch(opcao){
                                case 1:
                                    HashMap<String, List<String>> cliMaiorVenda = clienteService.maiorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < cliMaiorVenda.get("VendaFornecedorCliente").size(); i++){
                                        System.out.println("Cliente: " + cliMaiorVenda.get("NomeFornecedorCliente").get(i) + " Maior venda: " + cliMaiorVenda.get("VendaFornecedorCliente").get(i));
                                    }
                                    break;
                                case 2:
                                    HashMap<String, List<String>> cliMenorVenda = clienteService.maiorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < cliMenorVenda.get("VendaFornecedorCliente").size(); i++){
                                        System.out.println("Cliente: " + cliMenorVenda.get("NomeFornecedorCliente").get(i) + " Menor venda: " + cliMenorVenda.get("VendaFornecedorCliente").get(i));
                                    }
                                    break;
                                case 3:
                                    HashMap<String, List<String>> cliMediaVenda = clienteService.maiorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < cliMediaVenda.get("VendaFornecedorCliente").size(); i++){
                                        System.out.println("Cliente: " + cliMediaVenda.get("NomeFornecedorCliente").get(i) + " Media venda: " + cliMediaVenda.get("VendaFornecedorCliente").get(i));
                                    }
                                    break;
                                default:
                                    System.out.println("\nInsira uma opção válida!");
                            }
                            break;
                        case 3:
                            System.out.println("\n1 - Maior venda");
                            System.out.println("2 - Menor venda");
                            System.out.println("3 - Média venda");
                            opcao = sc.nextInt();

                            switch(opcao){
                                case 1:
                                    HashMap<String, List<String>> fornMaiorVenda = fornecedorService.maiorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < fornMaiorVenda.get("VendaFornecedorCliente").size(); i++){
                                        System.out.println("Fornecedor: " + fornMaiorVenda.get("NomeFornecedorCliente").get(i) + " Maior venda: " + fornMaiorVenda.get("VendaFornecedorCliente").get(i));
                                    }
                                    break;
                                case 2:
                                    HashMap<String, List<String>> fornMenorVenda = fornecedorService.maiorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < fornMenorVenda.get("VendaFornecedorCliente").size(); i++){
                                        System.out.println("Fornecedor: " + fornMenorVenda.get("NomeFornecedorCliente").get(i) + " Menor venda: " + fornMenorVenda.get("VendaFornecedorCliente").get(i));
                                    }
                                    break;
                                case 3:
                                    HashMap<String, List<String>> fornMediaVenda = fornecedorService.maiorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < fornMediaVenda.get("VendaFornecedorCliente").size(); i++){
                                        System.out.println("Fornecedor: " + fornMediaVenda.get("NomeFornecedorCliente").get(i) + " Media venda: " + fornMediaVenda.get("VendaFornecedorCliente").get(i));
                                    }
                                    break;
                                default:
                                    System.out.println("\nInsira uma opção válida!");
                            }
                            break;
                        case 4:
                            System.out.println("\n1 - Maior venda");
                            System.out.println("2 - Menor venda");
                            System.out.println("3 - Média venda");
                            opcao = sc.nextInt();

                            switch(opcao){
                                case 1:
                                    HashMap<String, List<String>> funcMaiorVenda = funcionarioRepository.maiorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < funcMaiorVenda.get("VendaCategoria").size(); i++){
                                        System.out.println("Funcionario: " + funcMaiorVenda.get("NomeCategoria").get(i) + " Maior venda: " + funcMaiorVenda.get("VendaCategoria").get(i));
                                    }
                                    break;
                                case 2:
                                    HashMap<String, List<String>> funcMenorVenda = funcionarioRepository.menorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < funcMenorVenda.get("VendaCategoria").size(); i++){
                                        System.out.println("Funcionario: " + funcMenorVenda.get("NomeCategoria").get(i) + " Menor venda: " + funcMenorVenda.get("VendaCategoria").get(i));
                                    }
                                    break;
                                case 3:
                                    HashMap<String, List<String>> funcMediaVenda = funcionarioRepository.maiorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < funcMediaVenda.get("VendaCategoria").size(); i++){
                                        System.out.println("Funcionario: " + funcMediaVenda.get("NomeCategoria").get(i) + " Media venda: " + funcMediaVenda.get("VendaCategoria").get(i));
                                    }
                                    break;
                                default:
                                    System.out.println("\nInsira uma opção válida!");
                            }
                            break;
                        case 5:
                            System.out.println("\n1 - Maior venda");
                            System.out.println("2 - Menor venda");
                            System.out.println("3 - Média venda");
                            opcao = sc.nextInt();

                            switch(opcao){
                                case 1:
                                    HashMap<String, List<String>> prodMaiorVenda = produtosRepository.maiorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < prodMaiorVenda.get("VendaProduto").size(); i++){
                                        System.out.println("Produto: " + prodMaiorVenda.get("NomeProduto").get(i) + " Maior venda: " + prodMaiorVenda.get("VendaProduto").get(i));
                                    }
                                    break;
                                case 2:
                                    HashMap<String, List<String>> prodMenorVenda = produtosRepository.menorVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < prodMenorVenda.get("VendaProduto").size(); i++){
                                        System.out.println("Produto: " + prodMenorVenda.get("NomeProduto").get(i) + " Menor venda: " + prodMenorVenda.get("VendaProduto").get(i));
                                    }
                                    break;
                                case 3:
                                    HashMap<String, List<String>> prodMediaVenda = produtosRepository.mediaVenda();

                                    System.out.println("\n");
                                    for(int i = 0; i < prodMediaVenda.get("VendaProduto").size(); i++){
                                        System.out.println("Produto: " + prodMediaVenda.get("NomeProduto").get(i) + " Media venda: " + prodMediaVenda.get("VendaProduto").get(i));
                                    }
                                    break;
                                default:
                                    System.out.println("\nInsira uma opção válida!");
                            }
                            break;
                    }
                    break;
                case 0:
                    System.out.println("\nEncerrando...");
                    break;
                default:
                    System.out.println("\nInsira uma opção válida / implementada!");
            }
        }while(opcao != 0);
    }
}