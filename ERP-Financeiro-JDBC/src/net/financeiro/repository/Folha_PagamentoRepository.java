package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Folha_Pagamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Folha_PagamentoRepository {
    public Folha_Pagamento inserir(Folha_Pagamento ins){
        String sql = "INSERT INTO Folha_Pagamento (descontos, data_entrada, horas_trabalhadas, valor_hora, id_funcionario)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setDouble(1, ins.getDescontos());
            pr.setString(2, ins.getData_entrada());
            pr.setInt(3, ins.getHoras_trabalhadas());
            pr.setDouble(4, ins.getValor_hora());
            pr.setLong(5, ins.getId_funcionario());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            ins.setId_folha_pagamento(rs.getLong("GENERATED_KEY"));
            return ins;
        } catch(SQLException e){
            System.out.println("Erro ao inserir dados: " + e.getMessage());
            return null;
        }
    }

    public Folha_Pagamento atualizar(Folha_Pagamento atl){
        String sql = "UPDATE Folha_Pagamento SET descontos = ?, data_entrada = ?, horas_trabalhadas = ?, valor_hora = ?, id_funcionario = ? WHERE id_folha_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setDouble(1, atl.getDescontos());
            pr.setString(2, atl.getData_entrada());
            pr.setInt(3, atl.getHoras_trabalhadas());
            pr.setDouble(4, atl.getValor_hora());
            pr.setLong(5, atl.getId_funcionario());
            pr.setLong(6, atl.getId_folha_pagamento());
            pr.executeUpdate();
            return atl;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar dados: " + e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id){
        String sql = "update Folha_Pagamento set ativo = false WHERE id_folha_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            pr.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    public boolean reativar(Long id){
        String sql = "update Folha_Pagamento set ativo = true WHERE id_folha_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            pr.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao reativar: " + e.getMessage());
            return false;
        }
    }

    public List<Folha_Pagamento> listarInfo(){
        String sql = "SELECT Folha_Pagamento.*, Funcionario.nome " +
                "FROM Folha_Pagamento " +
                "join Funcionario on Funcionario.id_funcionario = Folha_Pagamento.id_funcionario" +
                "where Folha_Pagamento.ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Folha_Pagamento> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Folha_Pagamento(
                        rs.getLong("id_folha_pagamento"),
                        rs.getLong("id_funcionario"),
                        rs.getDouble("descontos"),
                        rs.getDouble("valor_hora"),
                        rs.getInt("horas_trabalhadas"),
                        rs.getString("data_entrada"),
                        rs.getString("Funcionario.nome")));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public Folha_Pagamento buscarPorId(Long id_folha_pagamento){
        String sql = "SELECT Folha_Pagamento.*, Funcionario.nome " +
                "FROM Folha_Pagamento " +
                "join Funcionario on Funcionario.id_funcionario = Folha_Pagamento.id_funcionario " +
                "where Folha_Pagamento.id_folha_pagamento = ? and Folha_Pagamento.ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_folha_pagamento);
            ResultSet rs = pr.executeQuery();
            rs.next();
            return new Folha_Pagamento(
                    id_folha_pagamento,
                    rs.getLong("id_funcionario"),
                    rs.getDouble("descontos"),
                    rs.getDouble("valor_hora"),
                    rs.getInt("horas_trabalhadas"),
                    rs.getString("data_entrada"),
                    rs.getString("Funcionario.nome"));
        } catch(SQLException e){
            System.out.println("Erro ao buscar por ID: " + e.getMessage());
            return null;
        }
    }


}
