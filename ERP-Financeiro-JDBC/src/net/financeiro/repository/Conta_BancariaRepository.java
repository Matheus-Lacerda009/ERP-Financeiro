package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Categoria_Item;
import net.financeiro.model.Conta_Bancaria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Conta_BancariaRepository {


    public Conta_Bancaria inserir(Conta_Bancaria ins) throws SQLException {
        String sql = "INSERT INTO Conta_Bancaria (nome_banco, numero_conta) VALUES (?, ?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setString(1, ins.getNome_banco());
            pr.setInt(2, ins.getNumero_conta());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            ins.setId_caixa(rs.getLong("GENERATED_KEY"));
            return ins;
        }
    }

    public Conta_Bancaria atualizar(Conta_Bancaria atl, Long id) throws SQLException {
        String sql = "UPDATE Conta_Bancaria SET nome_banco = ?, numero_conta = ? WHERE id_caixa = ? ";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setString(1, atl.getNome_banco());
            pr.setInt(2, atl.getNumero_conta());
            pr.setLong(3, id);
            pr.executeUpdate();
            return atl;
        }
    }


    public boolean deletar(Long id_caixa) throws SQLException {
        String sql = "update Conta_Bancaria set ativo = false WHERE id_caixa = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_caixa);
            pr.executeUpdate();
            return true;
        }
    }

    public boolean reativar(Long id_caixa) throws SQLException{
        String sql = "update Conta_Bancaria set ativo = true WHERE id_caixa = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_caixa);
            return pr.executeUpdate() != 0;
        }
    }

    public List<Conta_Bancaria> listarInfo() throws SQLException {
        String sql = "SELECT * FROM Conta_Bancaria where ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Conta_Bancaria> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Conta_Bancaria(rs.getLong("id_caixa"), rs.getString("nome_banco"), rs.getInt("numero_conta")));
            }
            return lista;
        }
    }

    public Conta_Bancaria buscarPorId(Long id_caixa) throws SQLException {
        String sql = "SELECT * FROM Conta_Bancaria where id_caixa = ? where ativo = true";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_caixa);
            ResultSet rs = pr.executeQuery();
            rs.next();
            return new Conta_Bancaria(id_caixa, rs.getString("nome_banco"), rs.getInt("numero_conta"));
        }
    }

}
