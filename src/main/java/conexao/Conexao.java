package conexao;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	
	private static String UrlCon = "jdbc:postgresql://localhost:5432/projeto";
	private static String user = "postgres";
	private static String Senha = "123";
	 
	public static Connection getConexao() {
		 try {
	            // Registrar o driver PostgreSQL
	            Class.forName("org.postgresql.Driver");
	            // Estabelecer a conexão
	            return DriverManager.getConnection(UrlCon, user, Senha);
	        } catch (ClassNotFoundException e) {
	            throw new RuntimeException("Driver JDBC não encontrado!", e);
	        } catch (SQLException e) {
	            throw new RuntimeException("Erro na conexão com o banco de dados.", e);
	        }
		 	
	    }
}
