package net.financeiro.repository;

import net.financeiro.connection.Conexao;
import net.financeiro.model.Categoria_Item;
import net.financeiro.model.Conta_Bancaria;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Conta_BancariaRepository {


    public Conta_Bancaria inserir(Conta_Bancaria ins){
        String sql = "INSERT INTO Conta_Bancaria (nome_banco, numero_conta) VALUES (?, ?)";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            pr.setString(1, ins.getNome_banco());
            pr.setInt(2, ins.getNumero_conta());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            rs.next();
            ins.setId_caixa(rs.getLong("GENERATED_KEY"));
            return ins;
        } catch(SQLException e){
            System.out.println("Erro ao inserir dados: " + e.getMessage());
            return null;
        }
    }

    public Conta_Bancaria atualizar(Conta_Bancaria atl){
        String sql = "UPDATE Conta_Bancaria SET nome_banco = ?, numero_conta = ? WHERE id_caixa = ? ";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setString(1, atl.getNome_banco());
            pr.setInt(2, atl.getNumero_conta());
            pr.setLong(3, atl.getId_caixa());
            pr.executeUpdate();
            return atl;
        } catch(SQLException e){
            System.out.println("Erro ao atualizar dados: " + e.getMessage());
            return null;
        }
    }


    public boolean deletar(Long id_caixa){
        String sql = "DELETE FROM Conta_Bancaria WHERE id_caixa = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_caixa);
            pr.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("Erro ao deletar: " + e.getMessage());
            return false;
        }
    }

    public List<Conta_Bancaria> listarInfo(){
        String sql = "SELECT * FROM Conta_Bancaria";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            List<Conta_Bancaria> lista = new ArrayList<>();
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                lista.add(new Conta_Bancaria(rs.getLong("id_caixa"), rs.getString("nome_banco"), rs.getInt("numero_conta")));
            }
            return lista;
        } catch(SQLException e){
            System.out.println("Erro ao listar: " + e.getMessage());
            return null;
        }
    }

    public Conta_Bancaria buscarPorId(Long id_caixa){
        String sql = "SELECT * FROM Conta_Bancaria where id_caixa = ?";
        try(PreparedStatement pr = Conexao.connecting().prepareStatement(sql)){
            pr.setLong(1, id_caixa);
            ResultSet rs = pr.executeQuery();
            rs.next();
            return new Conta_Bancaria(id_caixa, rs.getString("nome_banco"), rs.getInt("numero_conta"));
        } catch(SQLException e){
            System.out.println("Erro ao buscar por ID: " + e.getMessage());
            return null;
        }
    }

}
