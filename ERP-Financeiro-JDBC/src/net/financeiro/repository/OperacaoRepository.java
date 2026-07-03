package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Operacao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OperacaoRepository {
    public Operacao inserir(Operacao op){
        String sql = "INSERT INTO Operacao (id_operacao, data_operacao, status_operacao, id_fornecedor_cliente, id_funcionario) VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setLong(1, op.getId_operacao());
            pr.setString(2, op.getData_operacao());
            pr.setString(3, op.getStatus_operacao());
            pr.setLong(4, op.getId_fornecedor_cliente());
            pr.setLong(5, op.getId_funcionario());
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            op.setId_operacao(rs.getLong("GENERATED_KEY"));
            return op;
        }catch(SQLException e){
            System.out.println("Erro ao inserir dados: " + e.getMessage());
            return null;
        }
    }

    public Operacao atualizar(Operacao op){
        String sql = "UPDATE Operacao SET id_operacao = ?, data_operacao = ?, status_operacao = ?, id_fornecedor_cliente = ?, id_funcionario = ? WHERE id_operacao = ?";

        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, op.getId_operacao());
            pr.setString(2, op.getData_operacao());
            pr.setString(3, op.getStatus_operacao());
            pr.setLong(4, op.getId_fornecedor_cliente());
            pr.setLong(5, op.getId_funcionario());
            pr.setLong(6, op.getId_operacao());
            pr.executeUpdate();
            return op;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar dados: " + e.getMessage());
            return null;
        }
    }
}
