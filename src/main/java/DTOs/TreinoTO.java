package DTOs;

import java.time.LocalDate;

import Enum.TipoTreino;

public class TreinoTO {
	private int id;
	private int alunoId;
	private String descricao;
	private LocalDate data;
	private TipoTreino tipo;

	public int getId(){
		return this.id;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public LocalDate getData() {
		return this.data;
	}
	
	public int getIdAluno() {
		return this.alunoId;
	}

	public TipoTreino getTipoTreino() {
		return this.tipo;
	}
	
	public void setId(int id) {
		if(id > 0) {
			this.id = id;
		}
		
	}
	
	public void setIdAluno(int idAluno) {
		if(idAluno > 0) { //colocar validação de comparação com ID existente já
			this.alunoId = idAluno;
		}else {
			throw new IllegalArgumentException("ID ALUNO INVÁLIDO");
		}
	}
	
	public void setDescricao(String descricao) {
		if(descricao == null || descricao.trim().isEmpty() ) {
			throw new IllegalArgumentException("descrição inválida, está vazia");
		}
		this.descricao = descricao;
	}
	
	public void setData(LocalDate data) {

		if(data == null) {
			 throw new IllegalArgumentException("Data inválida, vazia");
		}
		if(data.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Sua data está no futuro, inserá uma data válida");
		}
		if(data.isBefore(LocalDate.of(1900, 1, 1))) {
			throw new IllegalArgumentException("A data deve ser depois de 01/01/1900");
		}
		this.data = data;
	}
	
	public void setTreinoTipo(TipoTreino tipoTreino) {
		if(tipoTreino == null) {
			throw new IllegalArgumentException("Tipo de treino inválido");
		}
		this.tipo = tipoTreino;
	}

	

}