package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Valor_Salario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Valor_SalarioRepository {
    public List<Valor_Salario> listarInfo() {
        String sql = "select valor_salario.salario, f.nome\n" +
                "from\n" +
                "    Funcionario as f\n" +
                "    join valor_salario on valor_salario.id_funcionario = f.id_funcionario\n" +
                "order by valor_salario.salario desc;";

        try (PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            List<Valor_Salario> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new Valor_Salario(rs.getLong("id_funcionario"), rs.getDouble("valor_salario")));
            }
            return lista;
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }
}