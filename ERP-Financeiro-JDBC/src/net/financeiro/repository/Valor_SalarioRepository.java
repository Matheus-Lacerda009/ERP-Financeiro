package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Valor_Salario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Valor_SalarioRepository {
    public List<Valor_Salario> listarInfo() throws SQLException {
        String sql = "select valor_salario.salario, f.nome\n" +
                "from\n" +
                "    Funcionario as f\n" +
                "    join valor_salario on valor_salario.id_funcionario = f.id_funcionario\n" +
                "order by valor_salario.salario desc;";

        PreparedStatement pr = Conexao.connecting().prepareStatement(sql);
        List<Valor_Salario> lista = new ArrayList<>();
        ResultSet rs = pr.executeQuery();
        while (rs.next()) {
            lista.add(new Valor_Salario(rs.getLong("id_funcionario"), rs.getDouble("valor_salario"), rs.getString("nome_funcionario")));
        }
        pr.close();
        return lista;
    }
}