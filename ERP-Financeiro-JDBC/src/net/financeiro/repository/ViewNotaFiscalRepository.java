package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.ViewNotaFiscal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewNotaFiscalRepository {
    public List<ViewNotaFiscal> listarInfo() throws SQLException {
        String sql = "SELECT * FROM ViewNotaFiscal";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            List<ViewNotaFiscal> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new ViewNotaFiscal(rs.getLong("numero_nota_fiscal"), rs.getLong("id_fluxo_caixa"), rs.getLong("id_parceiro"), rs.getString("data_emissao"), rs.getString("nome_empresa_parceira"), rs.getString("documento_parceiro"), rs.getString("email_parceiro"), rs.getString("forma_pagamento"), rs.getString("banco_recebimento"), rs.getInt("parcelas")));
            }
            return lista;
        }
    }
}
