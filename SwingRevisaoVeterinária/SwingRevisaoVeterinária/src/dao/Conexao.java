package dao;
//só precisou de bibliotecas sql
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:postgresql://localhost:5432/clinica_db"; //link do postgres
    private static final String USUARIO = "postgres"; // "Usuário padrão do Postgres"
    private static final String SENHA = "dev123"; // "A senha que você definiu na instalação"

    public static Connection getConnection() { //metodo para tentar conectar
        try {
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}