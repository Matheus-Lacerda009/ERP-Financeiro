package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Categoria_Item;
import net.financeiro.model.Fluxo_Caixa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fluxo_CaixaRepository {

    public Fluxo_Caixa inserir(Fluxo_Caixa ins){
        String sql = "INSERT INTO Fluxo_Caixa (id_caixa, id_forma_pagamento, tipo_operacao, parcelas, id_folha_pagamento)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setLong(1, ins.getId_caixa());
            pr.setLong(2, ins.getId_forma_pagamento());
            pr.setString(3, ins.getTipo_operacao());
            pr.setInt(4, ins.getParcelas());
            pr.setLong(5, ins.getId_folha_pagamento());
            pr.setLong(6, ins.getId_operacao());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            ins.setId_fluxo_caixa(rs.getLong("GENERATED_KEY"));
            return ins;
        } catch(SQLException e){
            System.out.println("Erro ao inserir dados: " + e.getMessage());
            return null;
        }
    }

    public Fluxo_Caixa atualizar(Fluxo_Caixa atl){
        String sql = "UPDATE Fluxo_Caixa SET id_caixa = ?, id_forma_pagamento = ?, tipo_operacao  = ?, parcelas  = ?, id_folha_pagamento = ?, id_operacao = ? WHERE id_fluxo_caixa = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, atl.getId_caixa());
            pr.setLong(2, atl.getId_forma_pagamento());
            pr.setString(3, atl.getTipo_operacao());
            pr.setInt(4, atl.getParcelas());
            pr.setLong(5, atl.getId_folha_pagamento());
            pr.setLong(6, atl.getId_operacao());
            pr.setLong(7, atl.getId_fluxo_caixa());
            pr.executeUpdate();
            return atl;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar dados: " + e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id){
        String sql = "DELETE FROM Fluxo_Caixa WHERE id_fluxo_caixa = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            pr.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao deletar: " + e.getMessage());
            return false;
        }
    }


    public List<Fluxo_Caixa> listarInfo(){
        String sql = "SELECT * FROM Fluxo_Caixa";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Fluxo_Caixa> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Fluxo_Caixa(rs.getLong("id_fluxo_caixa"),  rs.getLong("id_caixa"),
                rs.getLong("id_forma_pagamento"),
                rs.getString("tipo_operacao"),
                rs.getInt("parcelas"),
                rs.getLong("id_folha_pagamento"),
                rs.getLong("id_operacao")));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public Fluxo_Caixa buscarPorId(Long id_fluxo_caixa ){
        String sql = "SELECT * FROM Fluxo_Caixa where id_fluxo_caixa  = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_fluxo_caixa );
            ResultSet rs = pr.executeQuery();
            rs.next();
            return new Fluxo_Caixa(id_fluxo_caixa , rs.getLong("id_caixa"), rs.getLong("id_forma_pagamento"), rs.getString("tipo_operacao"), rs.getInt("parcelas"), rs.getLong("id_folha_pagamento"), rs.getLong("id_operacao"));
        } catch(SQLException e){
            System.out.println("Erro ao buscar por ID: " + e.getMessage());
            return null;
        }
    }


    /*MÉTODOS QUERIES8*/

    public HashMap<String, List<String>> entradas_realizadas(int dias){

        String sql = "SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda\n" +
                "FROM Fluxo_Caixa fc\n" +
                "JOIN Operacao o ON o.id_operacao = fc.id_operacao\n" +
                "JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao\n" +
                "\n" +
                "WHERE fc.tipo_operacao = 'Venda'\n" +
                "  AND o.status_operacao = 'Concluída'\n" +
                "  AND o.data_operacao >= NOW() - INTERVAL ? DAY;\n";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setInt(1, dias);
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> data = new ArrayList<>();
            List<String> valorVenda = new ArrayList<>();
            lista.put("Data", data);
            lista.put("ValorVenda", valorVenda);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.get("Data").add(rs.getString("Data"));
                lista.get("ValorVenda").add(rs.getString("ValorVenda"));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> entradas_previstas (int dias){
        String sql = "SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda\n" +
                "FROM Fluxo_Caixa fc\n" +
                "JOIN Operacao o ON o.id_operacao = fc.id_operacao\n" +
                "JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao\n" +
                "\n" +
                "WHERE fc.tipo_operacao = 'Venda'\n" +
                "  AND o.data_operacao >= NOW() - INTERVAL ? DAY;";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setInt(1, dias);
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> data = new ArrayList<>();
            List<String> valorVenda = new ArrayList<>();
            lista.put("Data", data);
            lista.put("ValorVenda", valorVenda);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.get("Data").add(rs.getString("Data"));
                lista.get("ValorVenda").add(rs.getString("ValorVenda"));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }


    public HashMap<String, List<String>> saidas_realizadas (int dias){
        String sql = "SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda\n" +
                "FROM Fluxo_Caixa fc\n" +
                "JOIN Operacao o ON o.id_operacao = fc.id_operacao\n" +
                "JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao\n" +
                "\n" +
                "WHERE fc.tipo_operacao = 'Compra'\n" +
                "  AND o.status_operacao = 'Concluída'\n" +
                "  AND o.data_operacao >= NOW() - INTERVAL ? DAY;";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setInt(1, dias);
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> data = new ArrayList<>();
            List<String> valorVenda = new ArrayList<>();
            lista.put("Data", data);
            lista.put("ValorVenda", valorVenda);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.get("Data").add(rs.getString("Data"));
                lista.get("ValorVenda").add(rs.getString("ValorVenda"));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public HashMap<String, List<String>> saidas_previstas  (int dias){
        String sql = "SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda\n" +
                "FROM Fluxo_Caixa fc\n" +
                "JOIN Operacao o ON o.id_operacao = fc.id_operacao\n" +
                "JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao\n" +
                "\n" +
                "WHERE fc.tipo_operacao = 'Compra'\n" +
                "  AND o.status_operacao = 'Concluída'\n" +
                "  AND o.data_operacao >= NOW() - INTERVAL ? DAY;";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setInt(1, dias);
            HashMap<String, List<String>> lista = new HashMap<>();
            List<String> data = new ArrayList<>();
            List<String> valorVenda = new ArrayList<>();
            lista.put("Data", data);
            lista.put("ValorVenda", valorVenda);
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.get("Data").add(rs.getString("Data"));
                lista.get("ValorVenda").add(rs.getString("ValorVenda"));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }








}
