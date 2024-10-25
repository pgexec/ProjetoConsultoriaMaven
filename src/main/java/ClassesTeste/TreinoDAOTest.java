package ClassesTeste;

import java.time.LocalDate;

import DAOs.treinoDAO;
import DTOs.TreinoTO;
import Enum.TipoTreino;


public class TreinoDAOTest {
	public static void main(String[] args) {
		TreinoDAOTest teste = new TreinoDAOTest();
		
		 teste.testeInserirTreino();
	     teste.testeBuscarTreinoPorId();
	     teste.testeAtualizarTreino();
	     teste.testeRemoverTreino();
	}
	
	public void testeInserirTreino() {
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
	
	public void testeBuscarTreinoPorId() {
	 treinoDAO dao = new treinoDAO();
        TreinoTO treino = dao.buscarPorId(1);

        if (treino != null) {
            System.out.println("Treino encontrado: " + treino.getDescricao());
        } else {
            System.out.println("Treino não encontrado.");
        }
	}
	
	public void testeAtualizarTreino() {
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
	
	public void testeRemoverTreino() {
		
		treinoDAO treinoDAO = new treinoDAO();

        if (treinoDAO.delete(1)) {
            System.out.println("Treino removido com sucesso!");
        } else {
            System.out.println("Falha ao remover treino.");
        }
	 }
}
