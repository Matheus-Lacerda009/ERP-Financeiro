package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Itens_Operacao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Itens_OperacaoRepository {
    public Itens_Operacao inserir(Itens_Operacao ins) {
        String sql = "INSERT INTO Itens_Operacao (quantidade_produtos, id_produto, id_operacao) VALUES (?, ?, ?)";

        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pr.setInt(1, ins.getQuantidade_produtos());
            pr.setLong(2, ins.getId_produto());
            pr.setLong(3, ins.getId_operacao());
            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            rs.next();

            ins.setId_itens_operacao(rs.getLong("GENERATED_KEY"));
            return ins;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir dados: " + e.getMessage());
            return null;
        }
    }

    public Itens_Operacao atualizar(Itens_Operacao atl) {
        String sql = "UPDATE Itens_Operacao SET quantidade_produtos = ?, id_produto = ?, id_operacao = ? WHERE id_itens_operacao = ?";

        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setInt(1, atl.getQuantidade_produtos());
            pr.setLong(2, atl.getId_produto());
            pr.setLong(3, atl.getId_operacao());
            pr.setLong(4, atl.getId_itens_operacao());
            pr.executeUpdate();

            return atl;
        } catch(SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id) {
        String sql = "DELETE FROM Itens_Operacao WHERE id_itens_operacao = ?";

        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id);
            pr.executeUpdate();

            return true;
        } catch(SQLException e) {
            System.out.println("Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    public List<Itens_Operacao> listarInfo() {
        String sql = "SELECT * FROM Itens_Operacao";

        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            List<Itens_Operacao> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();

            while(rs.next()) {
                lista.add(new Itens_Operacao(
                        rs.getLong("id_itens_operacao"),
                        rs.getLong("id_produto"),
                        rs.getLong("id_operacao"),
                        rs.getInt("quantidade_produtos")
                ));
            }

            return lista;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Itens_Operacao buscarPorId(Long id) {
        String sql = "SELECT * FROM Itens_Operacao WHERE id_itens_operacao = ?";

        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            rs.next();
            return new Itens_Operacao(id, rs.getLong("id_produto"), rs.getLong("id_operacao"), rs.getInt("quantidade_produtos"));
        } catch (SQLException e) {
            System.out.println("Erro ao buscar por ID: " + e.getMessage());
            return null;
        }
    }
}
