package ClassesTeste;

import java.time.LocalDate;
import java.util.List;

import DAOs.alunoDAO;
import DTOs.AlunoTO;


public class AlunoDAOTest {
	public static void main(String[] args) {
	        testeInserirAluno();
	        testeBuscarAlunoPorId();
	        testeAtualizarAluno();
	        testeRemoverAluno();
	        testeListAlunos();
	}
	
	public static void testeInserirAluno() {
		alunoDAO alunoDAO = new alunoDAO();
		AlunoTO aluno = new AlunoTO();
		aluno.setNome("joana da silva");
		aluno.setCpf("99955544422");
		aluno.setDataNascimento(LocalDate.of(1999, 1, 1));
		aluno.setPeso(85.5);
		aluno.setAltura(1.73);

		boolean inserido = alunoDAO.insert(aluno);
		if(inserido) {
			System.out.println("inserido com SUCESSO!");
		}else
			System.out.println("DEU PROBLEMA AO INSERIR");
	}
	
	public static void testeBuscarAlunoPorId() {
		alunoDAO alunoDAO = new alunoDAO();
		AlunoTO aluno = alunoDAO.buscarPorId(6);
		if(aluno != null) {
			System.out.println("buscado com SUCESSO!");
		}else	
			System.out.println("DEU PROBLEMA AO BUSCAR");
	}
	
	public static void testeAtualizarAluno() {
		alunoDAO alunoDAO = new alunoDAO();
		AlunoTO aluno = new AlunoTO();
		aluno.setNome("joana da silva");
		aluno.setCpf("99955544422");
		aluno.setDataNascimento(LocalDate.of(2000, 6, 25));
		aluno.setPeso(67.0);
		aluno.setAltura(1.74);
		boolean atualizado = alunoDAO.update(aluno);
		if(atualizado) {
			System.out.println("Atualizado com SUCESSO!");
		}else
			System.out.println("DEU PROBLEMA AO ATUALIZAR");
	}
	
	public static void testeListAlunos() {
		alunoDAO alunoDAO = new alunoDAO();
		List<AlunoTO> alunos = alunoDAO.list(4, 0);
		
		 if (alunos == null || alunos.isEmpty()) {
		        System.out.println("Nenhum aluno encontrado ou lista é nula.");
		        return;
		    }
		 
			for(AlunoTO aluno : alunos) {
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
	
	
	public static void testeRemoverAluno() {
		alunoDAO alunoDAO = new alunoDAO();
		boolean removido = alunoDAO.delete(43);
		if(removido) {
			System.out.println("deletar com SUCESSO");
		}else
			System.out.println("DEU PROBLEMA AO REMOVER");
	}
	
	
}
