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

    public Fluxo_Caixa inserir(Fluxo_Caixa ins) throws SQLException {
        String sql = "INSERT INTO Fluxo_Caixa (id_caixa, id_forma_pagamento, tipo_operacao, parcelas, id_folha_pagamento, id_operacao)\n" +
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
        }
    }

    public Fluxo_Caixa atualizar(Fluxo_Caixa atl, Long id) throws SQLException {
        String sql = "UPDATE Fluxo_Caixa SET id_caixa = ?, id_forma_pagamento = ?, tipo_operacao  = ?, parcelas  = ?, id_folha_pagamento = ?, id_operacao = ? WHERE id_fluxo_caixa = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, atl.getId_caixa());
            pr.setLong(2, atl.getId_forma_pagamento());
            pr.setString(3, atl.getTipo_operacao());
            pr.setInt(4, atl.getParcelas());
            pr.setLong(5, atl.getId_folha_pagamento());
            pr.setLong(6, atl.getId_operacao());
            pr.setLong(7, id);
            pr.executeUpdate();
            return atl;
        }
    }

    public boolean deletar(Long id) throws SQLException {
        String sql = "update Fluxo_Caixa set ativo = false WHERE id_fluxo_caixa = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            pr.executeUpdate();
            return true;
        }
    }

    public boolean reativar(Long id) throws SQLException {
        String sql = "update Fluxo_Caixa set ativo = true WHERE id_fluxo_caixa = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {

            pr.setLong(1, id);
            return pr.executeUpdate() != 0;
        }
    }

    public List<Fluxo_Caixa> listarInfo() throws SQLException {
        String sql =  "SELECT Fluxo_Caixa.*, " +
                "Conta_Bancaria.nome_banco AS nome_banco, " +
                "Forma_Pagamento.nome AS nome_forma_pagamento, " +
                "Funcionario.nome AS nome_funcionario, " +
                "Operacao.status_operacao AS status_operacao " +
                "FROM Fluxo_Caixa " +
                "join Conta_Bancaria on Conta_Bancaria.id_caixa = Fluxo_Caixa.id_caixa " +
                "join Forma_Pagamento on Forma_Pagamento.id_forma_pagamento = Fluxo_Caixa.id_forma_pagamento " +
                "join Folha_Pagamento on Folha_Pagamento.id_folha_pagamento = Fluxo_Caixa.id_folha_pagamento " +
                "join Funcionario on Funcionario.id_funcionario = Folha_Pagamento.id_funcionario " +
                "join Operacao on Operacao.id_operacao = Fluxo_Caixa.id_operacao " +
                "where Fluxo_Caixa.ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Fluxo_Caixa> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Fluxo_Caixa(
                        rs.getLong("id_fluxo_caixa"),
                        rs.getLong("id_caixa"),
                        rs.getLong("id_forma_pagamento"),
                        rs.getString("tipo_operacao"),
                        rs.getInt("parcelas"),
                        rs.getLong("id_folha_pagamento"),
                        rs.getLong("id_operacao"),
                        rs.getString("nome_banco"),
                        rs.getString("nome_forma_pagamento"),
                        rs.getString("nome_funcionario"),
                        rs.getString("status_operacao")));
            }
            return lista;
        }
    }

    public Fluxo_Caixa buscarPorId(Long id_fluxo_caixa ) throws SQLException {
        String sql = "SELECT Fluxo_Caixa.*, " +
                "Conta_Bancaria.nome_banco AS nome_banco, " +
                "Forma_Pagamento.nome AS nome_forma_pagamento, " +
                "Funcionario.nome AS nome_funcionario, " +
                "Operacao.status_operacao AS status_operacao " +
                "FROM Fluxo_Caixa " +
                "join Conta_Bancaria on Conta_Bancaria.id_caixa = Fluxo_Caixa.id_caixa " +
                "join Forma_Pagamento on Forma_Pagamento.id_forma_pagamento = Fluxo_Caixa.id_forma_pagamento " +
                "join Folha_Pagamento on Folha_Pagamento.id_folha_pagamento = Fluxo_Caixa.id_folha_pagamento " +
                "join Funcionario on Funcionario.id_funcionario = Folha_Pagamento.id_funcionario " +
                "join Operacao on Operacao.id_operacao = Fluxo_Caixa.id_operacao " +
                "where Fluxo_Caixa.id_fluxo_caixa = ? and Fluxo_Caixa.ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_fluxo_caixa );
            ResultSet rs = pr.executeQuery();
            rs.next();
            return new Fluxo_Caixa(id_fluxo_caixa, rs.getLong("id_caixa"), rs.getLong("id_forma_pagamento"),
                    rs.getString("tipo_operacao"), rs.getInt("parcelas"), rs.getLong("id_folha_pagamento"),
                    rs.getLong("id_operacao"), rs.getString("nome_banco"),
                    rs.getString("nome_forma_pagamento"), rs.getString("nome_funcionario"),
                    rs.getString("status_operacao"));
        }
    }

    /*MÉTODOS QUERIES8*/

    public HashMap<String, List<String>> entradas_realizadas(int dias) throws SQLException {

        String sql = "SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda\n" +
                "FROM Fluxo_Caixa fc\n" +
                "JOIN Operacao o ON o.id_operacao = fc.id_operacao\n" +
                "JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao\n" +
                "\n" +
                "WHERE fc.tipo_operacao = 'Venda'\n" +
                "  AND o.status_operacao = 'Concluída'\n" +
                "  AND o.data_operacao >= NOW() - INTERVAL ? DAY\n" +
                "  and fc.ativo = true";
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
        }
    }

    public HashMap<String, List<String>> entradas_previstas (int dias) throws SQLException {
        String sql = "SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda\n" +
                "FROM Fluxo_Caixa fc\n" +
                "JOIN Operacao o ON o.id_operacao = fc.id_operacao\n" +
                "JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao\n" +
                "\n" +
                "WHERE fc.tipo_operacao = 'Venda'\n" +
                "  AND o.data_operacao >= NOW() - INTERVAL ? DAY" +
                "  and fc.ativo = true";
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
        }
    }

    public HashMap<String, List<String>> saidas_realizadas (int dias) throws SQLException {
        String sql = "SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda\n" +
                "FROM Fluxo_Caixa fc\n" +
                "JOIN Operacao o ON o.id_operacao = fc.id_operacao\n" +
                "JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao\n" +
                "\n" +
                "WHERE fc.tipo_operacao = 'Compra'\n" +
                "  AND o.status_operacao = 'Concluída'\n" +
                "  AND o.data_operacao >= NOW() - INTERVAL ? DAY" +
                "  and fc.ativo = true";
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
        }
    }

    public HashMap<String, List<String>> saidas_previstas  (int dias) throws SQLException {
        String sql = "SELECT o.data_operacao AS Data, vv.valor_total AS ValorVenda\n" +
                "FROM Fluxo_Caixa fc\n" +
                "JOIN Operacao o ON o.id_operacao = fc.id_operacao\n" +
                "JOIN Valor_pVenda vv ON o.id_operacao = vv.id_operacao\n" +
                "\n" +
                "WHERE fc.tipo_operacao = 'Compra'\n" +
                "  AND o.status_operacao = 'Concluída'\n" +
                "  AND o.data_operacao >= NOW() - INTERVAL ? DAY" +
                "  and fc.ativo = true";
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
        }
    }
}