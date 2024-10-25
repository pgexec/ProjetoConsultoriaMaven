package DAOs;

import java.util.ArrayList;
import java.util.List;

import DTOs.TreinoTO;
import Enum.TipoTreino;
import Interface.CrudRepository;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class treinoDAO implements CrudRepository<TreinoTO> {

	@Override
	public boolean insert(TreinoTO treino) {
		
		String query = "INSERT INTO treino (descricao, data, aluno_id, tipo_treino) " +
                "VALUES ( ?, ?, ?, ?) RETURNING id";

 try (Connection con = Conexao.getConexao();
      PreparedStatement pstm = con.prepareStatement(query)) {

     
     pstm.setString(1, treino.getDescricao());
     pstm.setDate(2, Date.valueOf(treino.getData()));
     pstm.setInt(3, treino.getIdAluno());
     pstm.setString(4, treino.getTipoTreino().name());

     
     try (ResultSet result = pstm.executeQuery()) {
         if (result.next()) {
             int idGerado = result.getInt("id");
             treino.setId(idGerado);  // Atribui o ID ao objeto TreinoTO
             System.out.println("Treino inserido com ID: " + idGerado);
             return true;
         } else {
             throw new SQLException("Falha ao recuperar o ID gerado.");
         }
     }
 } catch (SQLException e) {
     throw new RuntimeException("Erro ao inserir treino: " + e.getMessage(), e);
 }
	}

	@Override
	public boolean update(TreinoTO treino) {
		
		String query = "UPDATE Treino SET descricao =?, tipo_treino =? WHERE id =?";
		try(Connection con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);) {
			
			pstm.setString(1,treino.getDescricao());
			pstm.setString(2,treino.getTipoTreino().name());
			pstm.setInt(3, treino.getId());
	
			return true;
		}catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}

	@Override
	public boolean delete(int id) {
		String query = "DELETE FROM Treino WHERE id = ?";
		try(Connection con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);){
			
			pstm.setInt(1, id);
			pstm.executeUpdate();
			return true;
			
		}catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}

	@Override
	public TreinoTO buscarPorId(int id) {
		
		String query = "SELECT * FROM Treino WHERE id=?";
		TreinoTO treino = new TreinoTO();
		
		try(Connection con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);
			ResultSet result = pstm.executeQuery();){
			
			
			treino.setId(result.getInt("id"));
			treino.setDescricao(result.getString("descricao"));
			treino.setIdAluno(result.getInt("id_aluno"));
			treino.setData(result.getDate("data").toLocalDate());
			String tipoTreinoStr = result.getString("tipo_treino");
			TipoTreino tipoTreino = TipoTreino.valueOf(tipoTreinoStr);
			treino.setTreinoTipo(tipoTreino);
			
			con.close();
			pstm.close();
			result.close();
			
		}catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		return treino;
	}

	@Override
	public List<TreinoTO> list(int limit,int offset) {
		
		String query = "SELECT * FROM Treino LIMIT ? OFFSET ?";
	    ArrayList<TreinoTO> lista = new ArrayList<>();

	    try (Connection con = Conexao.getConexao();
	         PreparedStatement pstm = con.prepareStatement(query)) {

	        // Definindo os valores de limit e offset no PreparedStatement
	        pstm.setInt(1, limit);
	        pstm.setInt(2, offset);

	        try (ResultSet result = pstm.executeQuery()) {
	            while (result.next()) {
	                TreinoTO treino = new TreinoTO();
	                treino.setId(result.getInt("id"));
	                treino.setIdAluno(result.getInt("aluno_id"));
	                treino.setDescricao(result.getString("descricao"));
	                treino.setData(result.getDate("data").toLocalDate());

	                // Convertendo a string para enum TipoTreino
	                String tipoTreinoStr = result.getString("tipo_treino");
	                TipoTreino tipoTreino = TipoTreino.valueOf(tipoTreinoStr);
	                treino.setTreinoTipo(tipoTreino);

	                lista.add(treino);
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e.getMessage());
	    }

	    return lista;
	}
	
	public TreinoTO buscarTreinoPorAlunoId(int idAluno) {
	    String query = "SELECT * FROM Treino WHERE aluno_id = ?";
        TreinoTO treino = new TreinoTO();
	    try (Connection con = Conexao.getConexao();
	         PreparedStatement pstm = con.prepareStatement(query)) {

	        pstm.setInt(1, idAluno);

	        try (ResultSet result = pstm.executeQuery()) {
	            if (result.next()) {

	                treino.setId(result.getInt("id"));
	                treino.setDescricao(result.getString("descricao"));
	                treino.setData(result.getDate("data").toLocalDate());
	                treino.setTreinoTipo(TipoTreino.valueOf(result.getString("tipo_treino")));
	                treino.setIdAluno(result.getInt("aluno_id"));
	                
	            }
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao buscar treino por ID do Aluno: " + e.getMessage(), e);
	    }
	    return treino;  
	}
}
