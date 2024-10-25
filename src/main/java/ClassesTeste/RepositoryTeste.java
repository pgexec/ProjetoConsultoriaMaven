	package ClassesTeste;

import java.time.LocalDate;

import DAOs.alunoDAO;
import DAOs.treinoDAO;
import Enum.TipoTreino;
import Models.Aluno;
import Models.Treino;
import repository.Repository;

public class RepositoryTeste {
	public static void main(String[] args) {
		
		alunoDAO alunoDAO = new alunoDAO();
	    treinoDAO treinoDAO = new treinoDAO();
		
		Repository repository = new Repository(alunoDAO, treinoDAO);
		
		// Teste 1: Inserção de Aluno com Treino
        System.out.println("Teste 1: Inserção de Aluno com Treino");
        if (inserirAlunoComTreino(repository)) {
            System.out.println("Aluno e treino inseridos com sucesso.");
        } else {
            System.out.println("Falha ao inserir aluno e treino.");
        }

        // Teste 2: Buscar Aluno por ID
        System.out.println("\nTeste 2: Buscar Aluno por ID");
        buscarAlunoPorId(repository, 30);  // Supondo que o ID do aluno seja 1

        // Teste 3: Inserção de Aluno com Dados Inválidos
        System.out.println("\nTeste 3: Inserção de Aluno com Nome Inválido");
        try {
            inserirAlunoComNomeInvalido(repository);
        } catch (IllegalArgumentException e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }
		
	}
	
	public static boolean inserirAlunoComTreino(Repository repository) {
		
		Aluno aluno = new Aluno();
	    aluno.setNome("Carlos Silva");
	    aluno.setCpf("12346890045");
	    aluno.setDataNascimento(LocalDate.of(1990, 5, 20));
	    aluno.setPeso(75.0);
	    aluno.setAltura(1.80);

	    // Cria um treino associado
	    Treino treino = new Treino();
	    treino.setDescricao("Treino de Força");
	    treino.setData(LocalDate.now().minusDays(10));
	    treino.setTreinoTipo(TipoTreino.PEITO);
	    aluno.setTreino(treino);
	    
	    boolean alunoInserido = repository.insert(aluno);
	    if (alunoInserido) {
	    
	        aluno.setTreino(treino);    
	        System.out.println("Aluno e treino inseridos com sucesso!");  
	        return true;
	    } 
	    else {
	        System.out.println("Falha ao inserir aluno.");
	        return false;
	    }
    }

    // Método para Buscar Aluno por ID
    public static void buscarAlunoPorId(Repository repository, int id) {
        Aluno aluno = repository.buscarPorId(id);
        if (aluno != null) {
            System.out.println("NOME do Aluno encontrado: " + aluno.getNome());
            System.out.println("ID do Aluno encontrado: " + aluno.getId());
            System.out.println("CPF do Aluno encontrado: " + aluno.getCpf());
            System.out.println("PESO do Aluno encontrado: " + aluno.getPeso());
            System.out.println("ALTURA do Aluno encontrado: " + aluno.getAltura());
            Treino treino = aluno.getTreino();
            if (treino != null) {
                System.out.println("ID do Treino associado: " + treino.getId());
                System.out.println("DATA do Treino associado: " + treino.getData());
                System.out.println("DESCRIÇÃO do Treino associado: " + treino.getDescricao());

                
            } else {
                System.out.println("Aluno não tem treino associado.");
            }
        } else {
            System.out.println("Aluno não encontrado com ID: " + id);
        }
    }

    // Método para Testar Inserção de Aluno com Nome Inválido
    public static void inserirAlunoComNomeInvalido(Repository repository) {
        Aluno aluno = new Aluno();
        aluno.setNome("");  // Nome inválido
        aluno.setCpf("987.654.321-00");
        aluno.setDataNascimento(LocalDate.of(1985, 7, 15));
        aluno.setPeso(70.0);
        aluno.setAltura(1.75);

        repository.insert(aluno);  // Deve lançar exceção
    }
}
