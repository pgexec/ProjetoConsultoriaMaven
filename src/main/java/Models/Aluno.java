package Models;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Aluno {
	int id;
	String nome;
	String cpf;
	LocalDate dataNascimento;
	Double peso;
	Double altura;
	Treino treino;
	
	public Aluno(String nome, String cpf, LocalDate datanasc, Double peso,Double altura, Treino treino) {
		setNome(nome);
		setCpf(cpf);
		setDataNascimento(datanasc);
		setPeso(peso);
		setAltura(altura);
		setTreino(treino);
	}
	public Aluno() {
		
	}
	public int getId() {
		return this.id;
	}
	public String getNome() {
		return this.nome;
	}
	public String getCpf() {
		return this.cpf;
	}
	public LocalDate getDataNascimento() {
		return this.dataNascimento;
	}
	public Double getPeso() {
		return this.peso;
	}
	public Double getAltura() {
		return this.altura;
	}
	
	public Treino getTreino() {
		return this.treino;
	}
	
	public void setTreino(Treino treino) {
		this.treino = treino;
	}
	public void setId(int id) {
		if(id > 0) {  //colocar validação de comparação com ID existente já
			this.id = id;
		}else {
			throw new IllegalArgumentException("ID INVÁLIDO!");
		}
		
	}
	
	public void setNome(String nome) {
		if(nome == null || nome.trim().isEmpty()) {
			throw new IllegalArgumentException("Nome inválido, está vazio, insera um nome VÁLIDO!");
		}
		this.nome = nome;
	}
	
	public void setCpf(String cpf) {
		if(validarCpf(cpf)) {
			this.cpf = cpf;
		}else {
			throw new IllegalArgumentException("CPF INVÁLIDO");
		}
		
	}
	
	public void setDataNascimento(LocalDate data) {
		if(data == null) {
			throw new IllegalArgumentException("data vazia, inserá uma data válida!");
			
		}
		if(data.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Sua data está no Futuro, inserá uma data válida!");
		}
		if(data.isBefore(LocalDate.of(1900, 1, 1))) {
			throw new IllegalArgumentException("A data de nascimento deve ser depois de 01/01/1900!");
		}
		this.dataNascimento = data;
	}
	
	
	public void setPeso(Double peso) {
		if(peso > 20 && peso < 300) {
			this.peso = peso;
		}else {
			throw new IllegalArgumentException("Peso Inválido, O Peso tem que ser maior que ZERO!");
		}
		
	}
	
	public void setAltura(Double altura) {
		if(altura > 0 && altura < 2.5) {
			this.altura = altura;
		}else {
			throw new IllegalArgumentException("Altura Inválida!");
		}
		
	}
	
	
	public boolean validarCpf(String cpf) {
		String cpfNumeros = cpf.replaceAll("\\D","");
		if(cpfNumeros.length() != 11) {
			throw new IllegalArgumentException("Seu cpf está inválido, tamanho errado!");
		}
		String cpfFormatado = formatarCpf(cpf);
		
		String regex =  "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
		return Pattern.matches(regex, cpfFormatado);
	}
	
	public String formatarCpf(String cpf) {
		return String.format("%s.%s.%s-%s",cpf.substring(0,3),
											cpf.substring(3, 6),
											cpf.substring(6, 9),
											cpf.substring(9, 11));
	}
	
	
	  // Método para calcular o IMC
	public double calcularIMC() {
        return peso / (altura * altura);
    }
	
	// Método para calcular o peso ideal mínimo e máximo
	 public double getPesoIdealMinimo() {
	        return 18.5 * (altura * altura);
	 }
	 
	 public double getPesoIdealMaximo() {
	        return 24.9 * (altura * altura);
	 }
	 
	// Método para calcular quanto peso a pessoa precisa perder ou ganhar
    public String calcularPesoParaPesoIdeal() {
    	double pesoIdealMax = getPesoIdealMaximo();
    	double pesoIdealMin = getPesoIdealMinimo();
    	if(peso > pesoIdealMax) {
    		
    		double pesoAPerder = peso - pesoIdealMax;
    		return String.format("Você precisa perder %.2f para atingir o peso ideal", pesoAPerder);
    		
    	}else if (peso < pesoIdealMin) {
    		double pesoAGanhar = peso - pesoIdealMin;
    		return String.format("Você precisa ganhar %.2f para atingir o peso ideal", pesoAGanhar);
    	}else {
    		return "Você está no peso ideal";
    	}
    }


}
