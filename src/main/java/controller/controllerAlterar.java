package controller;


import java.net.URL;
import java.util.ResourceBundle;

import Enum.TipoTreino;
import Models.Aluno;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.Repository;

public class controllerAlterar implements Initializable{
	
	
	@FXML
	private TextField dataNascField;
	
	@FXML
    private Button btAlterar;
	
	 @FXML
	 private Button btCancelar;

    @FXML
    private TextField alturaField;

    @FXML
    private TextField cpfField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField pesoField;
    
    @FXML
    private ComboBox<TipoTreino> tipoTreinoSelected;
    
    private Aluno alunoAtual;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.addAlturaMask(alturaField);
		this.addCpfValidation(cpfField);
		this.addDateValidation(dataNascField);
		this.addPesoMask(pesoField);
		
	}
	
	public void cancelar() {
		Stage stageAtual = (Stage) btCancelar.getScene().getWindow();
    	stageAtual.close();
	}
	
	public void alterar() {
		Repository repository = new Repository();
		repository.update(alunoAtual);
	}

	public void setAluno(Aluno aluno) {
		
		this.alunoAtual = aluno;
		alunoAtual.setId(aluno.getId());
		nameField.setText(aluno.getNome());
		cpfField.setText(aluno.getCpf());
		pesoField.setText(aluno.getPeso().toString());
		alturaField.setText(aluno.getAltura().toString());
		
	}
	
	public void salvarAlteracoes() {
		
        alunoAtual.setNome(nameField.getText());
        alunoAtual.setCpf(cpfField.getText());
        alunoAtual.setPeso(Double.parseDouble(pesoField.getText()));
        alunoAtual.setAltura(Double.parseDouble(alturaField.getText()));
        
        // Atualize os dados do aluno no banco de dados
        Repository repository = new Repository();
        repository.update(alunoAtual);
        
       
    }
	
	private void addAlturaMask(TextField textField) {
	    textField.textProperty().addListener((observable, oldValue, newValue) -> {
	        // Remove todos os caracteres que não são números
	        String cleanValue = newValue.replaceAll("[^\\d]", "");
	
	        // Limita a entrada a no máximo 3 dígitos
	        if (cleanValue.length() > 3) {
	            cleanValue = cleanValue.substring(0, 3);
	        }
	
	        // Aplica a máscara "N.NN"
	        String formattedValue;
	        if (cleanValue.length() >= 2) {
	            formattedValue = cleanValue.substring(0, 1) + "." + cleanValue.substring(1);
	        } else {
	            formattedValue = cleanValue;
	        }
	
	        // Atualiza o campo de texto com o valor formatado
	        textField.setText(formattedValue);
	
	        // Coloca o cursor no final do texto
	        Platform.runLater(() -> textField.positionCaret(formattedValue.length()));
	    });
	    }


	    //função que faz a formatação da data para ddMMyyyy enquanto o usuário digita e faz a validação
	    private void addDateValidation(TextField textField) {
	        textField.textProperty().addListener((observable, oldValue, newValue) -> {
	            Platform.runLater(() -> {
	                String value = newValue.replaceAll("[^\\d]", ""); // Remove tudo que não for número
	                StringBuilder formatted = new StringBuilder();

	                // Limita a 8 caracteres numéricos (ddMMyyyy)
	                if (value.length() > 8) {
	                    textField.setText(oldValue);
	                    return;
	                }

	                // Adiciona as barras conforme os caracteres são digitados
	                for (int i = 0; i < value.length(); i++) {
	                    if (i == 2 || i == 4) {
	                        formatted.append("/");
	                    }
	                    formatted.append(value.charAt(i));
	                }

	                // Atualiza o texto do campo apenas se houver uma diferença
	                if (!formatted.toString().equals(newValue)) {
	                    int caretPosition = textField.getCaretPosition(); // Posição atual do cursor
	                    textField.setText(formatted.toString());

	                    // Reajusta o cursor para a posição correta
	                    textField.positionCaret(Math.min(caretPosition + 1, formatted.length()));
	                }
	            });
	        });
	    }
	    
	    private void addPesoMask(TextField textField) {
	        textField.textProperty().addListener((observable, oldValue, newValue) -> {
	            // Remove todos os caracteres que não são números
	            String cleanValue = newValue.replaceAll("[^\\d]", "");

	            // Limita a entrada a no máximo 4 dígitos
	            if (cleanValue.length() > 4) {
	                cleanValue = cleanValue.substring(0, 4);
	            }

	            // Aplica a máscara "NNN.N"
	            String formattedValue;
	            if (cleanValue.length() >= 4) {
	                formattedValue = cleanValue.substring(0, 3) + "." + cleanValue.substring(3);
	            } else {
	                formattedValue = cleanValue;
	            }

	            // Atualiza o campo de texto com o valor formatado
	            textField.setText(formattedValue);

	            // Coloca o cursor no final do texto
	            Platform.runLater(() -> textField.positionCaret(formattedValue.length()));
	        });
	    }


	    // Função para formatar e validar CPF no formato 000.000.000-00 enquanto o usuário digita
	    private void addCpfValidation(TextField textField) {
	        textField.textProperty().addListener((observable, oldValue, newValue) -> {
	            Platform.runLater(() -> {
	                String value = newValue.replaceAll("[^\\d]", "");
	                StringBuilder formatted = new StringBuilder();

	                if (value.length() > 11) {
	                    textField.setText(oldValue); // Limita a 11 caracteres
	                    return;
	                }

	                for (int i = 0; i < value.length(); i++) {
	                    if (i == 3 || i == 6) {
	                        formatted.append(".");
	                    } else if (i == 9) {
	                        formatted.append("-");
	                    }
	                    formatted.append(value.charAt(i));
	                }

	                if (!formatted.toString().equals(newValue)) { // Só atualiza se houver diferença
	                    textField.setText(formatted.toString());
	                }
	            });
	        });
	    }


}
