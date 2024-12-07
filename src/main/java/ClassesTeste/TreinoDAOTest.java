package ClassesTeste;

import java.time.LocalDate;
import java.util.List;

import DAOs.treinoDAO;
import DTOs.TreinoTO;
import Enum.Intensidade;
import Enum.NivelDificuldade;
import Enum.TipoTreino;

public class TreinoDAOTest {
    public static void main(String[] args) {
    	
        testeInserirTreino();
        testeBuscarTreinoPorId();
        testeAtualizarTreino();
        testeListarTreinos();
        testeRemoverTreino();
    }

    public static void testeInserirTreino() {
        treinoDAO treinoDAO = new treinoDAO();
        TreinoTO treino = new TreinoTO();

        // Dados do treino a serem inseridos
        treino.setTipoTreino(TipoTreino.FORCA);
        treino.setData(LocalDate.of(2024, 1, 1));
        treino.setAlunoId(56);
        treino.setIntensidade(Intensidade.ALTA);
        treino.setNivelDificuldade(NivelDificuldade.AVANCADO);

        if (treinoDAO.insert(treino)) {
            System.out.println("Inserido com SUCESSO! ID gerado: " + treino.getId());
        } else {
            System.out.println("Falha ao inserir.");
        }
    }

    public static void testeBuscarTreinoPorId() {
        treinoDAO dao = new treinoDAO();
        TreinoTO treino = dao.buscarPorId(1);

        if (treino != null) {
            System.out.println("Treino encontrado:");
            System.out.println("ID: " + treino.getId());
            System.out.println("ALUNO_ID: " + treino.getAlunoId());
            System.out.println("TIPO_TREINO: " + treino.getTipoTreino());
            System.out.println("DATA: " + treino.getData());
            System.out.println("INTENSIDADE: " + treino.getIntensidade());
            System.out.println("NIVEL_DIFICULDADE: " + treino.getNivelDificuldade());
        } else {
            System.out.println("Treino não encontrado.");
        }
    }

    public static void testeAtualizarTreino() {
        treinoDAO treinoDAO = new treinoDAO();
        TreinoTO treino = new TreinoTO();

       
        treino.setId(1); 
        treino.setTipoTreino(TipoTreino.HIPERTROFIA);
        treino.setData(LocalDate.of(2023, 10, 21));
        treino.setAlunoId(56);
        treino.setIntensidade(Intensidade.ALTA);
        treino.setNivelDificuldade(NivelDificuldade.AVANCADO);

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
            System.out.println("Nenhum treino encontrado ou lista é nula.");
            return;
        }

        for (TreinoTO treino : treinos) {
            System.out.println("ID: " + treino.getId());
            System.out.println("ALUNO_ID: " + treino.getAlunoId());
            System.out.println("TIPO_TREINO: " + treino.getTipoTreino());
            System.out.println("DATA: " + treino.getData());
            System.out.println("INTENSIDADE: " + treino.getIntensidade());
            System.out.println("NIVEL_DIFICULDADE: " + treino.getNivelDificuldade());
            System.out.println("-------------------------------------");
        }
    }
}
