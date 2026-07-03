package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.SaldoAtual;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaldoAtualRepository {
    public SaldoAtual visualizar() {
        String sql = "SELECT * FROM SaldoAtual";

        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            if(rs.next())
                return new SaldoAtual(rs.getDouble("Saldo_Total"));
            else
                return null;
        } catch(SQLException e) {
            System.out.println("Erro ao visualizar dados: " + e.getMessage());
            return null;
        }
    }
}
