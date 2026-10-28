package dao;
//bibliotecas sql, arraylist e list (List é a interface que contem as regras para implementar o Arraylist)
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Dono;
//É praticamente a mesma coisa do AnimalDAO
public class DonoDAO {

    // CREATE - Inserir um novo Dono no banco
    public Dono cadastrar(Dono dono) {
        String sql = "INSERT INTO dono (nome, telefone) VALUES (?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, dono.getNome());
            stmt.setString(2, dono.getTelefone());
            stmt.executeUpdate();

            // Recupera o ID que o PostgreSQL gerou automaticamente
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                dono.setId(rs.getInt(1));
            }

            return dono;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar dono: " + e.getMessage());
        }
    }

    // READ - Listar todos os Donos salvos
    public List<Dono> listar() {
        List<Dono> donos = new ArrayList<>();
        String sql = "SELECT * FROM dono ORDER BY id";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Dono dono = new Dono(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("telefone")
                );
                donos.add(dono);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar donos: " + e.getMessage());
        }

        return donos;
    }

    // UPDATE - Atualizar dados de um Dono existente
    public void atualizar(Dono dono) {
        String sql = "UPDATE dono SET nome = ?, telefone = ? WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dono.getNome());
            stmt.setString(2, dono.getTelefone());
            stmt.setInt(3, dono.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dono: " + e.getMessage());
        }
    }

    // DELETE - Remover um Dono pelo ID
    public void excluir(int id) {
        String sql = "DELETE FROM dono WHERE id = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir dono: " + e.getMessage());
        } 
    }
}