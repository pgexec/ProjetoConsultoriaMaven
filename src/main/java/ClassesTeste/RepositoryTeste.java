package ClassesTeste;

import java.time.LocalDate;
import java.util.List;

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
		/*
		 * System.out.println("--------------------------------------------");
		 * System.out.println("Teste 1: Inserção de Aluno com Treino"); if
		 * (inserirAlunoComTreino(repository)) {
		 * System.out.println("Aluno e treino inseridos com sucesso."); } else {
		 * System.out.println("Falha ao inserir aluno e treino."); }
		 */

		System.out.println("--------------------------------------------");
		// Teste 2: Buscar Aluno por ID
		System.out.println("\nTeste 2: Buscar Aluno por ID");
		buscarAlunoPorId(repository, 31); // Supondo que o ID do aluno seja 1
		System.out.println("--------------------------------------------");

		System.out.println("Teste 3: listar os alunos existentes:");
		
		List<Aluno> listados = repository.list(5, 0);
		if (listados.isEmpty()) {
			System.out.println("Falha ao listar");
		} else {
			for (Aluno aluno : listados) {
				System.out.println("##############################################");
				System.out.println("NOME do Aluno encontrado: " + aluno.getNome());
				System.out.println("ID do Aluno encontrado: " + aluno.getId());
				System.out.println("CPF do Aluno encontrado: " + aluno.getCpf());
				System.out.println("DATA DE NASCIMENTO do Aluno encontrado: " + aluno.getDataNascimento());
				System.out.println("PESO do Aluno encontrado: " + aluno.getPeso());
				System.out.println("ALTURA do Aluno encontrado: " + aluno.getAltura());
				System.out.println("###############################################");

			}
		}

		System.out.println("--------------------------------------------");
		//Teste 3: Teste excluindo Aluno do BD
		  System.out.println("Teste 4: teste de excluir Aluno"); 
		  boolean deletado = repository.delete(33);
		  if(deletado) {
			  System.out.println("deletado com Sucesso!");
		  }else {
			  System.out.println("Erro ao deletar!");
		  }
		
		System.out.println("--------------------------------------------");
		// Teste 4: Inserção de Aluno com Dados Inválidos
		System.out.println("\nTeste 4: Inserção de Aluno com Nome Inválido");
		try {
			inserirAlunoComNomeInvalido(repository);
		} catch (IllegalArgumentException e) {
			System.out.println("Exceção capturada: " + e.getMessage());
		}

	}

	public static boolean inserirAlunoComTreino(Repository repository) {

		Aluno aluno = new Aluno();
		aluno.setNome("marquinho");
		aluno.setCpf("32458678911");
		aluno.setDataNascimento(LocalDate.of(2001, 5, 25));
		aluno.setPeso(68.5);
		aluno.setAltura(1.72);

		// Cria um treino associado
		Treino treino = new Treino();
		treino.setDescricao("Treino de Força");
		treino.setData(LocalDate.now().minusDays(10));
		treino.setTreinoTipo(TipoTreino.COSTA);
		aluno.setTreino(treino);

		boolean alunoInserido = repository.insert(aluno);
		if (alunoInserido) {

			aluno.setTreino(treino);
			System.out.println("Aluno e treino inseridos com sucesso!");
			return true;
		} else {
			System.out.println("Falha ao inserir aluno.");
			return false;
		}
	}

	// Método para Buscar Aluno por ID
	public static void buscarAlunoPorId(Repository repository, int id) {
		
		Aluno aluno = repository.buscarPorId(id);
		
		if (aluno != null) {
			System.out.println("--------------------------------------------");
			System.out.println("NOME do Aluno encontrado: " + aluno.getNome());
			System.out.println("ID do Aluno encontrado: " + aluno.getId());
			System.out.println("CPF do Aluno encontrado: " + aluno.getCpf());
			System.out.println("PESO do Aluno encontrado: " + aluno.getPeso());
			System.out.println("ALTURA do Aluno encontrado: " + aluno.getAltura());
			System.out.println("--------------------------------------------");
			Treino treino = aluno.getTreino();
			if (treino != null) {
				System.out.println("--------------------------------------------");
				System.out.println("ID do Treino associado: " + treino.getId());
				System.out.println("DATA do Treino associado: " + treino.getData());
				System.out.println("DESCRIÇÃO do Treino associado: " + treino.getDescricao());
				System.out.println("--------------------------------------------");

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
		aluno.setNome(""); // Nome inválido
		aluno.setCpf("987.654.321-00");
		aluno.setDataNascimento(LocalDate.of(1985, 7, 15));
		aluno.setPeso(70.0);
		aluno.setAltura(1.75);

		repository.insert(aluno); // Deve lançar exceção
	}
}
