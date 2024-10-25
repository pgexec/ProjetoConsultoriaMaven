package ClassesTeste;

import java.time.LocalDate;

import DAOs.alunoDAO;
import DTOs.AlunoTO;


public class AlunoDAOTest {
	public static void main(String[] args) {
		 AlunoDAOTest teste = new AlunoDAOTest();
	        teste.testeInserirAluno();
	        teste.testeBuscarAlunoPorId();
	        teste.testeAtualizarAluno();
	        teste.testeRemoverAluno();
	}
	
	public void testeInserirAluno() {
		alunoDAO alunoDAO = new alunoDAO();
		AlunoTO aluno = new AlunoTO();
		aluno.setNome("paulo");
		aluno.setCpf("12345678911");
		aluno.setDataNascimento(LocalDate.of(1999, 1, 1));
		aluno.setPeso(85.5);
		aluno.setAltura(1.73);

		boolean inserido = alunoDAO.insert(aluno);
		if(inserido) {
			System.out.println("inserido com SUCESSO!");
		}else
			System.out.println("DEU PROBLEMA AO INSERIR");
	}
	
	public void testeBuscarAlunoPorId() {
		alunoDAO alunoDAO = new alunoDAO();
		AlunoTO aluno = alunoDAO.buscarPorId(6);
		if(aluno != null) {
			System.out.println("buscado com SUCESSO!");
		}else	
			System.out.println("DEU PROBLEMA AO BUSCAR");
	}
	
	public void testeAtualizarAluno() {
		alunoDAO alunoDAO = new alunoDAO();
		AlunoTO aluno = new AlunoTO();
		aluno.setNome("paulin");
		aluno.setCpf("12345678911");
		aluno.setDataNascimento(LocalDate.of(1999, 1, 1));
		aluno.setPeso(90.0);
		aluno.setAltura(1.74);
		boolean atualizado = alunoDAO.update(aluno);
		if(atualizado) {
			System.out.println("Atualizado com SUCESSO!");
		}else
			System.out.println("DEU PROBLEMA AO ATUALIZAR");
	}
	
	
	public void testeRemoverAluno() {
		alunoDAO alunoDAO = new alunoDAO();
		boolean removido = alunoDAO.delete(1);
		if(removido) {
			System.out.println("deletar com SUCESSO");
		}else
			System.out.println("DEU PROBLEMA AO REMOVER");
	}
	
}
