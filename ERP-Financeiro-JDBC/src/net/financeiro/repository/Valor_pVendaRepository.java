package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Valor_pVenda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Valor_pVendaRepository {
    public List<Valor_pVenda> visualizar() throws SQLException {
        String sql = "SELECT * FROM Valor_pVenda";

        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            List<Valor_pVenda> lista = new ArrayList<>();

            while(rs.next()) {
                lista.add(new Valor_pVenda(rs.getLong("id_operacao"), rs.getDouble("valor_total")));
            }

            return lista;
        }
    }
}
