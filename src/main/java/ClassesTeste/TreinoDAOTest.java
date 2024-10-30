package ClassesTeste;

import java.time.LocalDate;
import java.util.List;

import DAOs.treinoDAO;
import DTOs.TreinoTO;
import Enum.TipoTreino;


public class TreinoDAOTest {
	public static void main(String[] args) {
		
		
		 testeInserirTreino();
	     testeBuscarTreinoPorId();
	     testeAtualizarTreino();
	     testeRemoverTreino();
	}
	
	public static void testeInserirTreino() {
		treinoDAO treinoDAO = new treinoDAO();
		TreinoTO treino = new TreinoTO();
		
		treino.setDescricao("Treino de força");
		treino.setTreinoTipo(TipoTreino.PEITO);
		treino.setData(LocalDate.of(2024, 1, 1));
		treino.setIdAluno(6);
		if(treinoDAO.insert(treino)) {
			System.out.println("Inserido com SUCESSO!");
		}else {
			System.out.println("falha ao Inserir");
		}
	}
	
	public  static void testeBuscarTreinoPorId() {
	 treinoDAO dao = new treinoDAO();
        TreinoTO treino = dao.buscarPorId(1);

        if (treino != null) {
            System.out.println("Treino encontrado: " + treino.getDescricao());
        } else {
            System.out.println("Treino não encontrado.");
        }
	}
	
	public static void testeAtualizarTreino() {
		treinoDAO treinoDAO = new treinoDAO();
        TreinoTO treino = new TreinoTO();
        
		treino.setIdAluno(1);
        treino.setDescricao("Treino de resistência - costas");
        treino.setData(LocalDate.of(2023, 10, 21));
        treino.setTreinoTipo(TipoTreino.COSTA);

        if (treinoDAO.update(treino)) {
            System.out.println("Treino atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar treino.");
        }
    }
	
	public static void testeRemoverTreino() {
		
		treinoDAO treinoDAO = new treinoDAO();

        if (treinoDAO.delete(1)) {
            System.out.println("Treino removido com sucesso!");
        } else {
            System.out.println("Falha ao remover treino.");
        }
	 }
	
	public static void testeListarTreinos() {
		treinoDAO treinoDAO = new treinoDAO();
		List<TreinoTO> treinos = treinoDAO.list(4, 0);
		
		if (treinos == null || treinos.isEmpty()) {
	        System.out.println("Nenhum aluno encontrado ou lista é nula.");
	        return;
	    }
		
		for(TreinoTO treino : treinos) {
			System.out.println("ID:"+ treino.getId());
			System.out.println("DESCRIÇÃO:"+ treino.getDescricao());
			System.out.println("DATA:"+ treino.getData());
			System.out.println("ALUNO_ID:"+ treino.getIdAluno());
			System.out.println("TIPO DO TREINO:"+ treino.getTipoTreino());
		}
	}
}
