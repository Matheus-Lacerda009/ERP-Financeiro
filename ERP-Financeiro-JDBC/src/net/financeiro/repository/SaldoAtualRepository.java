package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.SaldoAtual;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaldoAtualRepository {
    public SaldoAtual visualizar() throws SQLException {
        String sql = "SELECT * FROM SaldoAtual";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);
        ResultSet rs = pr.executeQuery();

        rs.next();
        return new SaldoAtual(rs.getDouble("Saldo_Total"));
    }
}
