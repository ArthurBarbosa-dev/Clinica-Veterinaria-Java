package dao;
//importação de bibliotecas sql, Arraylist e List (List é uma interface e Arraylist a implementação dessa interface.)
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Animal; //só está informando em qual package estão as classes
import model.Dono;

public class AnimalDAO {
   
    public Animal cadastrar(Animal animal) { //metodo para cadastrar no banco de dados sql
        String sql = "INSERT INTO animal (nome, especie, raca, id_dono) VALUES (?, ?, ?, ?)"; //Essas interrogações seram substituidas pela informação do animal posteriormente

        try (Connection conn = Conexao.getConnection(); //assim que as operações com o banco de dados terminarem a conexão será encerrada
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { //Assim que for gerado o ID do tipo serial no BD, esse id será retornado para a String sql

            stmt.setString(1, animal.getNome()); //numero da interrogação que vai ser subistituida por um atributo
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setInt(4, animal.getDono().getId());

            stmt.executeUpdate(); //após qualquer alteração feita, ele retorna um número inteiro de quantas linhas da tabela foram alteradas

            ResultSet rs = stmt.getGeneratedKeys(); //acredito que seja para gerar chaves para cada animal
            if (rs.next()) { //rs = ResultSet -> nasce posicionada antes do primeiro resultado e se move com o .next, se achar algum resultado retorna true
                animal.setId(rs.getInt(1));
            }

            return animal;
        } catch (SQLException e) { //caso algo dê errado, será enviado a mensagem abaixo
            throw new RuntimeException("Erro ao cadastrar animal: " + e.getMessage()); 
        }
    }

    // "READ - Listar todos os Animais com os dados do Dono (JOIN)"
    public List<Animal> listar() {
        List<Animal> animais = new ArrayList<>();
        String sql = "SELECT a.id, a.nome, a.especie, a.raca, a.id_dono, d.nome AS dono_nome, d.telefone AS dono_telefone " +
                     "FROM animal a " +
                     "INNER JOIN dono d ON a.id_dono = d.id " +
                     "ORDER BY a.id"; //estrutura em sql, porém utilizando as informações de cada atributo dos objetos aqui do java

        try (Connection conn = Conexao.getConnection(); 	
             PreparedStatement stmt = conn.prepareStatement(sql); //
             ResultSet rs = stmt.executeQuery()) { //Faz a busca e retorna uma tabela cheia de dados

            while (rs.next()) {
                Dono dono = new Dono( //retorna as informações da tabela do sql para a variavel dono do java
                    rs.getInt("id_dono"),
                    rs.getString("dono_nome"),
                    rs.getString("dono_telefone")
                );

                Animal animal = new Animal( //retorna as informações da tabela do sql para a variavel animal do java
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("especie"),
                    rs.getString("raca"),
                    dono
                );

                animais.add(animal); //adiciona na arraylist animais
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar animais: " + e.getMessage());
        }

        return animais;
    }

    //UPDATE
    public void atualizar(Animal animal) {
    	String sql = "UPDATE animal SET nome = ?, especie = ?, raca = ?, id_dono = ? WHERE id = ?";
    	
    	try(Connection conn = Conexao.getConnection();
    		PreparedStatement stmt = conn.prepareStatement(sql)){
    		
    		stmt.setString(1, animal.getNome());
    		stmt.setString(2, animal.getEspecie());
    		stmt.setString(3, animal.getRaca());
    		stmt.setInt(4, animal.getDono().getId());
    		stmt.setInt(5, animal.getId());
    		
    		stmt.executeUpdate();
    	} catch (SQLException e) {
    		throw new RuntimeException("Erro ao atualizar animal: " + e.getMessage());
    	}
    }
    
    // "DELETE - Remover um Animal pelo ID"
    public void excluir(int id) {
        String sql = "DELETE FROM animal WHERE id = ?"; //deletar da tabela animal pelo id que ainda vai ser informado

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); //numero da coluna, o valor do id foi passado no parametro do metodo
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir animal: " + e.getMessage());
        } 
    }
}