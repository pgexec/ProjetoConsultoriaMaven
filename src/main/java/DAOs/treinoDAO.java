package DAOs;

import java.util.ArrayList;
import java.util.List;

import DTOs.TreinoTO;
import Enum.Intensidade;
import Enum.NivelDificuldade;
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
	    String query = "INSERT INTO treino (data, aluno_id, tipo_treino, intensidade, nivel_dificuldade) " +
	                   "VALUES (?, ?, ?, ?, ?) RETURNING id";

	    try (Connection con = Conexao.getConexao();
	         PreparedStatement pstm = con.prepareStatement(query)) {

	        con.setAutoCommit(false); // Inicia transação

	        // Preenchimento dos parâmetros
	        pstm.setDate(1, Date.valueOf(treino.getData()));
	        pstm.setInt(2, treino.getAlunoId());
	        pstm.setString(3, treino.getTipoTreino().name());
	        pstm.setString(4, treino.getIntensidade().name());
	        pstm.setString(5, treino.getNivelDificuldade().name());

	        try (ResultSet result = pstm.executeQuery()) {
	            if (result.next()) {
	                int idGerado = result.getInt("id");
	                treino.setId(idGerado); // Atualiza o ID no objeto
	                con.commit(); // Confirma transação
	                return true;
	            } else {
	                throw new SQLException("Falha ao recuperar o ID gerado.");
	            }
	        } catch (SQLException e) {
	            con.rollback(); // Reverte transação em caso de erro
	            throw new RuntimeException("Erro ao executar a query: " + e.getMessage(), e);
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao inserir treino: " + e.getMessage(), e);
	    }
	}

	@Override
	public boolean update(TreinoTO treino) {
		
        String query = "UPDATE treino SET tipo_treino = ?, intensidade = ?, nivel_dificuldade = ? WHERE id = ?";
		
		try(Connection con = Conexao.getConexao();
			PreparedStatement pstm = con.prepareStatement(query);) {
			
			
			  pstm.setString(1, treino.getTipoTreino().name());
	            pstm.setString(2, treino.getIntensidade().name());
	            pstm.setString(3, treino.getNivelDificuldade().name());
	            pstm.setInt(4, treino.getId());
			pstm.executeUpdate();
			return true;
			
		}catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}

	
	@Override
	public boolean delete(int id) {
        String query = "DELETE FROM treino WHERE id = ?";

        try (Connection con = Conexao.getConexao();
             PreparedStatement pstm = con.prepareStatement(query)) {

            pstm.setInt(1, id);
            pstm.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar treino: " + e.getMessage(), e);
        }
    }




	 @Override
	    public TreinoTO buscarPorId(int id) {
	        String query = "SELECT * FROM treino WHERE id = ?";
	        TreinoTO treino = null;

	        try (Connection con = Conexao.getConexao();
	             PreparedStatement pstm = con.prepareStatement(query)) {

	            pstm.setInt(1, id);

	            try (ResultSet result = pstm.executeQuery()) {
	                if (result.next()) {
	                    treino = mapResultSetToTreinoTO(result);
	                }
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException("Erro ao buscar treino por ID: " + e.getMessage(), e);
	        }

	        return treino;
	    }

	@Override
	public List<TreinoTO> list(int limit, int offset) {
        String query = "SELECT * FROM treino LIMIT ? OFFSET ?";
        List<TreinoTO> lista = new ArrayList<>();

        try (Connection con = Conexao.getConexao();
             PreparedStatement pstm = con.prepareStatement(query)) {

            pstm.setInt(1, limit);
            pstm.setInt(2, offset);

            try (ResultSet result = pstm.executeQuery()) {
                while (result.next()) {
                    TreinoTO treino = mapResultSetToTreinoTO(result);
                    lista.add(treino);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar treinos: " + e.getMessage(), e);
        }

        return lista;
    }
	
	 public TreinoTO buscarTreinoPorAlunoId(int idAluno) {
	        String query = "SELECT * FROM treino WHERE aluno_id = ?";
	        TreinoTO treino = null;

	        try (Connection con = Conexao.getConexao();
	             PreparedStatement pstm = con.prepareStatement(query)) {

	            pstm.setInt(1, idAluno);

	            try (ResultSet result = pstm.executeQuery()) {
	                if (result.next()) {
	                    treino = mapResultSetToTreinoTO(result);
	                }
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException("Erro ao buscar treino por ID do aluno: " + e.getMessage(), e);
	        }

	        return treino;
	    }
	
	 private TreinoTO mapResultSetToTreinoTO(ResultSet result) throws SQLException {
	        TreinoTO treino = new TreinoTO();

	        treino.setId(result.getInt("id"));
	        treino.setAlunoId(result.getInt("aluno_id"));
	        treino.setData(result.getDate("data").toLocalDate());
	        treino.setTipoTreino(TipoTreino.valueOf(result.getString("tipo_treino")));
	        treino.setIntensidade(Intensidade.valueOf(result.getString("intensidade")));
	        treino.setNivelDificuldade(NivelDificuldade.valueOf(result.getString("nivel_dificuldade")));

	        return treino;
	    }
}
