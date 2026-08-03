package net.financeiro.repository;

import net.financeiro.model.Forma_Pagamento;
import net.financeiro.connection.Conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Forma_PagamentoRepository {

    public Forma_Pagamento inserir(Forma_Pagamento ins) throws SQLException{
        String sql = "INSERT INTO Forma_Pagamento (nome) VALUES (?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setString(1, ins.getNome());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if(rs.next()){
                ins.setId_forma_pagamento(rs.getLong(1));
            }
            return  ins;
        }
    }

    public  Forma_Pagamento atualizar(Forma_Pagamento alt, Long id) throws SQLException{
        String sql = "UPDATE Forma_Pagamento SET nome = ? WHERE id_forma_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setString(1, alt.getNome());
            pr.setLong(2, id);
            pr.executeUpdate();
            return  alt;
        }
    }

    public  boolean deletar(Long id) throws SQLException{
        String sql = "update Forma_Pagamento set ativo = false WHERE id_forma_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() != 0;
        }
    }

    public  boolean reativar(Long id) throws SQLException{
        String sql = "update Forma_Pagamento set ativo = true WHERE id_forma_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id);
            return pr.executeUpdate() != 0;
        }
    }

    public List<Forma_Pagamento> listarInfo() throws SQLException{
        String sql = "SELECT * FROM Forma_Pagamento where ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Forma_Pagamento> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();

            while (rs.next()){
                lista.add(new Forma_Pagamento(rs.getLong("id_forma_pagamento") , rs.getString("nome")));
            }
            return  lista;
        }
    }

    public Forma_Pagamento buscarPorId(Long id_Forma_Pagamento) throws SQLException{
        String sql = "SELECT * FROM Forma_Pagamento where id_forma_pagamento = ? and ativo = true";
        try (PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_Forma_Pagamento);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                return new Forma_Pagamento(id_Forma_Pagamento, rs.getString("nome"));
            }
            return null;
        }
    }
}