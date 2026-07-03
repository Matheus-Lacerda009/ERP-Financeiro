package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Valor_pVenda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Valor_pVendaRepository {
    public Valor_pVenda visualizar() {
        String sql = "SELECT * FROM Valor_pVenda";

        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();

            return new Valor_pVenda(rs.getDouble("valor_total"));
        } catch(SQLException e) {
            System.out.println("Erro ao visuaizar dados: " + e.getMessage());
            return null;
        }
    }
}
