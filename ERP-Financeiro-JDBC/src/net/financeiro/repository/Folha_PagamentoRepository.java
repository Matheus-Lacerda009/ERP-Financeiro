package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Categoria_Item;
import net.financeiro.model.Folha_Pagamento;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Folha_PagamentoRepository {
    public Folha_Pagamento inserir(Folha_Pagamento ins){
        String sql = "INSERT INTO Folha_Pagamento (descontos, data_entrada, horas_trabalhadas, valor_hora, id_funcionario)\n" +
                "VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setLong(1, ins.getId_funcionario());
            pr.setDouble(2, ins.getDescontos());
            pr.setDouble(3, ins.getValor_hora());
            pr.setInt(4, ins.getHoras_trabalhadas());
            pr.setString(5, ins.getData_entrada());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            ins.setId_folha_pagamento(rs.getLong("GENERATED_KEY"));
            return ins;
        } catch(SQLException e){
            System.out.println("Erro ao inserir dados: " + e.getMessage());
            return null;
        }
    }

    public Folha_Pagamento atualizar(Folha_Pagamento atl){
        String sql = "UPDATE Folha_Pagamento SET descontos = ?, data_entrada = ?, horas_trabalhadas = ?, valor_hora = ?, id_funcionario = ? WHERE id_folha_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, atl.getId_funcionario());
            pr.setDouble(2, atl.getDescontos());
            pr.setDouble(3, atl.getValor_hora());
            pr.setInt(4, atl.getHoras_trabalhadas());
            pr.setString(5, atl.getData_entrada());
            pr.setLong(6, atl.getId_folha_pagamento());
            pr.executeUpdate();
            return atl;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar dados: " + e.getMessage());
            return null;
        }
    }

    public boolean deletar(Long id){
        String sql = "DELETE FROM Folha_Pagamento WHERE id_folha_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id);
            pr.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    public List<Folha_Pagamento> listarInfo(){
        String sql = "SELECT * FROM Folha_Pagamento";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Folha_Pagamento> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Folha_Pagamento(rs.getLong("id_folha_pagamento"), rs.getLong("id_funcionario"), rs.getDouble("descontos"), rs.getDouble("valor_hora"), rs.getInt("horas_trabalhadas"), rs.getString("data_entrada")));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public Folha_Pagamento buscarPorId(Long id_folha_pagamento){
        String sql = "SELECT * FROM Folha_Pagamento where id_folha_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_folha_pagamento);
            ResultSet rs = pr.executeQuery();
            rs.next();
            return new Folha_Pagamento(id_folha_pagamento, rs.getLong("id_funcionario"), rs.getDouble("descontos"), rs.getDouble("valor_hora"), rs.getInt("horas_trabalhadas"), rs.getString("data_entrada"));
        } catch(SQLException e){
            System.out.println("Erro ao buscar por ID: " + e.getMessage());
            return null;
        }
    }


}
