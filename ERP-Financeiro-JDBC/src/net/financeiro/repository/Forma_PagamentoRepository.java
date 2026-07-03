package net.financeiro.repository;

import net.financeiro.model.Forma_Pagamento;
import net.financeiro.connection.Conexao;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Forma_PagamentoRepository {

    public Forma_Pagamento inserir(Forma_Pagamento ins){
        String sql = "INSERT INTO Forma_Pagamento (nome) VALUES (?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setString(1, ins.getNome());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            ins.setId_forma_pagamento(rs.getLong("GENERATED_KEY"));
            return  ins;
        }catch (SQLException e){
            System.out.println("ERRO ao inserir : " + e.getMessage());
            return  null;
        }
    }

    public  Forma_Pagamento atualizar(Forma_Pagamento alt){
        String sql = "UPDATE Forma_Pagamento SET nome = ? WHERE id_forma_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setString(1, alt.getNome());
            pr.setLong(2, alt.getId_forma_pagamento());
            pr.executeUpdate();
            return  alt;
        }catch (SQLException e ){
            System.out.println("ERRO ao atualizar : " + e.getMessage());
            return  null;
        }
    }

    public  boolean deletar(Long id){
        String sql = "DELETE FROM Forma_Pagamento WHERE = id_forma_pagamento = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)) {
            pr.setLong(1, id);
            pr.executeUpdate();
            return  true;
        }catch (SQLException e){
            System.out.println("ERRO ao deletar : " + e.getMessage());
            return  false;
        }
    }

    public List<Forma_Pagamento> listarInfo(){
        String sql = "SELECT * FROM Forma_Pagamento";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Forma_Pagamento> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();

            while (rs.next()){
                lista.add(new Forma_Pagamento(rs.getLong("id_forma_pagamento") , rs.getString("nome")));
            }
            return  lista;
        }catch (SQLException e){
            System.out.println("ERRO ao listar : " + e.getMessage());
            return  null;
        }
    }

    public Forma_Pagamento buscarPorId(Long id_Forma_Pagamento){
        String sql = "SELECT * FROM Categoria_Item where id_categoria_item = ?";
        try (PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_Forma_Pagamento);
            ResultSet rs = pr.executeQuery();
            rs.next();
            return  new Forma_Pagamento(id_Forma_Pagamento , rs.getString("nome"));
        }catch (SQLException e){
            System.out.println("ERRO de busca : " + e.getMessage());
            return  null;
        }
    }
}
