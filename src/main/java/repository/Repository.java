package repository;

import java.util.List;

import DAOs.alunoDAO;
import DAOs.treinoDAO;
import DTOs.AlunoTO;
import DTOs.TreinoTO;
import Interface.CrudRepository;
import Models.Aluno;
import Models.Treino;

public class Repository implements CrudRepository<Aluno>{

	private alunoDAO alunoDAO;
	private treinoDAO treinoDAO;
	
	
	public Repository(alunoDAO alunoDAO, treinoDAO treinoDAO) {
        this.alunoDAO = alunoDAO;
        this.treinoDAO = treinoDAO;
    }
	@Override
	public boolean insert(Aluno aluno) {
		
		 try {
		        // Preencher o AlunoTO
		        AlunoTO alunoTO = new AlunoTO();
		        alunoTO.setCpf(aluno.getCpf());
		        alunoTO.setAltura(aluno.getAltura());
		        alunoTO.setNome(aluno.getNome());
		        alunoTO.setPeso(aluno.getPeso());
		        alunoTO.setDataNascimento(aluno.getDataNascimento());

		        // Inserir o aluno e recuperar o ID gerado
		        boolean alunoInserido = alunoDAO.insert(alunoTO);
		        System.out.println(alunoTO.getId() + "este é o id do aluno");
		        if (!alunoInserido) {
		            System.out.println("Falha ao inserir aluno.");
		            return false;
		        }

		        // Atribuir o ID gerado ao objeto Aluno e ao Treino
		        aluno.setId(alunoTO.getId());  // Atribui o ID ao objeto Aluno
		        if (aluno.getTreino() != null || aluno.getId() != 0) {
		            Treino treino = aluno.getTreino();
		            // Preencher o TreinoTO
		            TreinoTO treinoTO = new TreinoTO();
		            treinoTO.setDescricao(treino.getDescricao());
		            treinoTO.setTreinoTipo(treino.getTipoTreino());
		            treinoTO.setData(treino.getData());
		            treinoTO.setIdAluno(alunoTO.getId());  // Agora com o ID correto

		            // Inserir o treino
		            boolean treinoInserido = treinoDAO.insert(treinoTO);
		            if (!treinoInserido) {
		                System.out.println("Falha ao inserir treino.");
		                return false;
		            }
		        } else {
		            System.out.println("Aluno não tem treino associado.");
		        }

		        System.out.println("Aluno e treino inseridos com sucesso!");
		        return true;

		    } catch (Exception e) {
		        throw new RuntimeException("Erro ao inserir aluno e treino: " + e.getMessage(), e);
		    }
	}

	@Override
	public boolean update(Aluno aluno) {
		
		AlunoTO alunoTO = new AlunoTO();
		TreinoTO treinoTO =new TreinoTO();
		
		alunoTO.setNome(aluno.getNome());
		alunoTO.setCpf(aluno.getCpf());
		alunoTO.setDataNascimento(aluno.getDataNascimento());
		alunoTO.setPeso(aluno.getPeso());
		alunoTO.setAltura(aluno.getAltura());

		
		
		
		return true;
	}

	@Override
	public boolean delete(int id) {
		
		 boolean AlunoDeletado = alunoDAO.delete(id);
		 if(!AlunoDeletado) {
			 return false;
		 }
		 boolean TreinoDeletado = treinoDAO.delete(id);
		 if(!TreinoDeletado) {
			 return false;
		 }
		return true;
	}

	@Override
	public Aluno buscarPorId(int id) {
		
		AlunoTO alunoTO = alunoDAO.buscarPorId(id);
		if(alunoTO == null) {
			return null;
		}
		Aluno aluno = converterAluno(alunoTO);
		if(aluno == null) {
			return null;
		}
		
		TreinoTO treinoTO = treinoDAO.buscarTreinoPorAlunoId(id);
		if(treinoTO == null) {
			return null;
		}
		Treino treino = converterTreino(treinoTO);
		if(treino == null) {
			return null;
		}
		
		aluno.setTreino(treino);
		
		return aluno;
	}

	@Override
	public List<Aluno> list() {
		
		
		List<Aluno> listaAluno = null; 
		List<AlunoTO> listaAlunosTO = alunoDAO.list();
		List<TreinoTO> listaTreinoTO = treinoDAO.list();
		for(int i = 0; i < listaAlunosTO.size(); i++) {
			if(listaAlunosTO.get(i).getId() == listaTreinoTO.get(i).getIdAluno()) {
				
				Aluno aluno = new Aluno();
				Treino treino = new Treino();
				aluno.setNome(listaAlunosTO.get(i).getNome());
				aluno.setCpf(listaAlunosTO.get(i).getCpf());
				aluno.setDataNascimento(listaAlunosTO.get(i).getDataNascimento());
				aluno.setPeso(listaAlunosTO.get(i).getPeso());
				aluno.setAltura(listaAlunosTO.get(i).getAltura());
				
				treino.setId(listaTreinoTO.get(i).getId());
				treino.setDescricao(listaTreinoTO.get(i).getDescricao());
				treino.setData(listaTreinoTO.get(i).getData());
				treino.setTreinoTipo(listaTreinoTO.get(i).getTipoTreino());
				
				aluno.setTreino(treino);
				listaAluno.add(aluno);
				
			}
		}
		
		return listaAluno;
	}
	
	private Aluno converterAluno(AlunoTO alunoTO) {
		Aluno aluno = new Aluno();
		
		aluno.setId(alunoTO.getId());
		aluno.setNome(alunoTO.getNome());
		aluno.setCpf(alunoTO.getCpf());
		aluno.setDataNascimento(alunoTO.getDataNascimento());
		aluno.setPeso(alunoTO.getPeso());
		aluno.setAltura(alunoTO.getAltura());
		return aluno;
		
	}
	
	private Treino converterTreino(TreinoTO treinoTO) {
	    Treino treino = new Treino();
	    treino.setId(treinoTO.getId());
	    treino.setDescricao(treinoTO.getDescricao());
	    treino.setData(treinoTO.getData());
	    treino.setTreinoTipo(treinoTO.getTipoTreino());
	    return treino;
	}
}
