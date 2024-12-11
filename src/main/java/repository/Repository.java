package repository;

import java.util.ArrayList;
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
	
	
	public Repository() {
        this.alunoDAO =  new alunoDAO();
        this.treinoDAO =  new treinoDAO();
    }

	@Override
	public boolean insert(Aluno aluno) {
	    try {
	        // Conversão do objeto Aluno para AlunoTO
	        AlunoTO alunoTO = new AlunoTO();
	        alunoTO.setCpf(aluno.getCpf());
	        alunoTO.setAltura(aluno.getAltura());
	        alunoTO.setNome(aluno.getNome());
	        alunoTO.setPeso(aluno.getPeso());
	        alunoTO.setDataNascimento(aluno.getDataNascimento());

	        // Inserir aluno no banco de dados
	        boolean alunoInserido = alunoDAO.insert(alunoTO);
	        System.out.println(alunoTO.getId() + " este é o id do aluno");
	        if (!alunoInserido) {
	            System.out.println("Falha ao inserir aluno.");
	            return false;
	        }

	        // Atualizar ID do aluno no objeto principal
	        aluno.setId(alunoTO.getId());

	        // Verificar e inserir treino, se existir
	        if (aluno.getTreino() != null) {
	            Treino treino = aluno.getTreino();

	            TreinoTO treinoTO = new TreinoTO();
	            treinoTO.setTipoTreino(treino.getTipoTreino());
	            treinoTO.setData(treino.getData());
	            treinoTO.setAlunoId(alunoTO.getId()); // Relacionar treino ao aluno inserido

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
	        try {
	            // Convertendo Aluno para AlunoTO
	            AlunoTO alunoTO = new AlunoTO();
	            alunoTO.setId(aluno.getId());
	            alunoTO.setNome(aluno.getNome());
	            alunoTO.setCpf(aluno.getCpf());
	            alunoTO.setDataNascimento(aluno.getDataNascimento());
	            alunoTO.setPeso(aluno.getPeso());
	            alunoTO.setAltura(aluno.getAltura());

	            boolean alunoAtualizado = alunoDAO.update(alunoTO);
	            if (!alunoAtualizado) {
	                throw new RuntimeException("Falha ao atualizar aluno.");
	            }

	            // Atualizando treino associado
	            Treino treino = aluno.getTreino();
	            if (treino != null) {
	                TreinoTO treinoTO = new TreinoTO();

	                treinoTO.setId(treino.getId());
	                treinoTO.setAlunoId(aluno.getId());
	                treinoTO.setData(treino.getData());
	                treinoTO.setTipoTreino(treino.getTipoTreino());
	                treinoTO.setIntensidade(treino.getIntensidade());
	                treinoTO.setNivelDificuldade(treino.getNivelDificuldade());

	                boolean treinoAtualizado = treinoDAO.update(treinoTO);
	                if (!treinoAtualizado) {
	                    throw new RuntimeException("Falha ao atualizar treino.");
	                }
	            }

	            return true;

	        } catch (Exception e) {
	            System.err.println("Erro ao atualizar aluno ou treino: " + e.getMessage());
	            return false;
	        }
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
    public List<Aluno> list(int limit, int offset) {
        List<Aluno> listaAluno = new ArrayList<>();

        List<AlunoTO> listaAlunosTO = alunoDAO.list(limit, offset);
        List<TreinoTO> listaTreinoTO = treinoDAO.list(limit, offset);

        for (AlunoTO alunoTO : listaAlunosTO) {
            Aluno aluno = converterAluno(alunoTO);

            for (TreinoTO treinoTO : listaTreinoTO) {
                if (alunoTO.getId() == treinoTO.getAlunoId()) {
                    Treino treino = converterTreino(treinoTO);
                    aluno.setTreino(treino);
                    break;
                }
            }

            listaAluno.add(aluno);
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
        treino.setData(treinoTO.getData());
        treino.setTipoTreino(treinoTO.getTipoTreino());
        treino.setIntensidade(treinoTO.getIntensidade());
        treino.setNivelDificuldade(treinoTO.getNivelDificuldade());
        return treino;
	}

	public void insert(TreinoTO treinoTO2, int alunoId) {
		TreinoTO treinoTO = new TreinoTO();

        treinoTO.setId(treinoTO2.getId());
        treinoTO.setAlunoId(alunoId);
        treinoTO.setData(treinoTO2.getData());
        treinoTO.setTipoTreino(treinoTO2.getTipoTreino());
        treinoTO.setIntensidade(treinoTO2.getIntensidade());
        treinoTO.setNivelDificuldade(treinoTO2.getNivelDificuldade());

        boolean treinoAtualizado = treinoDAO.update(treinoTO);
        if (!treinoAtualizado) {
            throw new RuntimeException("Falha ao atualizar treino.");
        }
		
	}
}
