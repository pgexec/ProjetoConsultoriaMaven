package DAOs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTOs.AlunoTO;
import Interface.CrudRepository;
import conexao.Conexao;

public class alunoDAO implements CrudRepository<AlunoTO>{
	
	@Override
	public boolean insert(AlunoTO aluno) {
		
		String query = "INSERT INTO aluno (nome, cpf, dataNascimento, peso, altura) " +
                "VALUES ( ?, ?, ?, ?, ?) RETURNING id";

 try (Connection con = Conexao.getConexao();
      PreparedStatement pstm = con.prepareStatement(query)) {

     // Configura os parâmetros
     pstm.setString(1, aluno.getNome());
     pstm.setString(2, aluno.getCpf());
     pstm.setDate(3, Date.valueOf(aluno.getDataNascimento()));
     pstm.setDouble(4, aluno.getPeso());
     pstm.setDouble(5, aluno.getAltura());

     // Executa a query e obtém o ID gerado diretamente
     try (ResultSet result = pstm.executeQuery()) {
         if (result.next()) {
             int idGerado = result.getInt("id");
             aluno.setId(idGerado);
             System.out.println("Aluno inserido com ID: " + idGerado);
             return true;
         } else {
             System.out.println("Falha ao recuperar o ID gerado.");
             throw new SQLException("Falha ao recuperar o ID gerado.");
         }
     }
 } catch (SQLException e) {
     throw new RuntimeException("Erro ao inserir aluno: " + e.getMessage(), e);
 }
	}

	@Override
	public boolean update(AlunoTO aluno) {
	    String query = "UPDATE aluno SET nome = ?, cpf = ?, datanascimento = ?, peso = ?, altura = ? WHERE cpf = ?";
	    
	    try (Connection con = Conexao.getConexao(); 
	         PreparedStatement pstm = con.prepareStatement(query)) {
	        
	        
	        pstm.setString(1, aluno.getNome());
	        pstm.setString(2, aluno.getCpf());
	        pstm.setDate(3, Date.valueOf(aluno.getDataNascimento()));
	        pstm.setDouble(4, aluno.getPeso());
	        pstm.setDouble(5, aluno.getAltura());
	        pstm.setString(6, aluno.getCpf());
	        pstm.executeUpdate();
	        
	    } catch (SQLException e) {
	        
	        throw new RuntimeException(e.getMessage()); 
	    }
	    return true;   
	}


	@Override
	public boolean delete(int id) {
		String query = "DELETE FROM aluno WHERE id=?";
		try {
			Connection con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, id);
			pstm.executeUpdate();
			pstm.close();
			con.close();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	@Override
	public AlunoTO buscarPorId(int id) {
	
		String query = "SELECT id, nome, cpf, datanascimento, peso, altura FROM Aluno WHERE id = ?;";
		AlunoTO aluno = new AlunoTO();
		
		try{
			
			Connection con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			pstm.setInt(1, id);
			ResultSet result = pstm.executeQuery();
			
			if(result.next()) {
				aluno.setId(result.getInt("id"));
				String nome = result.getString("nome");
				System.out.println(nome + "nome buscado do banco");
				aluno.setNome(result.getString("nome"));
				aluno.setCpf(result.getString("cpf"));
				aluno.setDataNascimento(result.getDate("datanascimento").toLocalDate());
				aluno.setPeso(result.getDouble("peso"));
				aluno.setAltura(result.getDouble("altura"));
			}
			
			con.close();
			pstm.close();
			result.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
			return aluno;
	}

	@Override
	public List<AlunoTO> list() {
		
		String query = "SELECT * FROM Aluno";
		ArrayList<AlunoTO> listaAlunos = new ArrayList<>();
		
		try {
			
			Connection con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			ResultSet res = pstm.executeQuery();
			
			while(res.next()) {
				AlunoTO aluno = new AlunoTO();
				aluno.setId(res.getInt("id"));
				aluno.setNome(res.getString("nome"));
				aluno.setCpf(res.getString("cpf"));
				aluno.setDataNascimento(res.getDate("datanascimento").toLocalDate());
				aluno.setPeso(res.getDouble("peso"));
				aluno.setAltura(res.getDouble("altura"));
				
				listaAlunos.add(aluno);
			}
			con.close();
			pstm.close();
			res.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return listaAlunos;
	}
    }
