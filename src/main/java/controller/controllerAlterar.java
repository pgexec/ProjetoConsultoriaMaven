package controller;


import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

import DAOs.alunoDAO;
import DTOs.AlunoTO;
import Enum.TipoTreino;
import application.Main;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class controllerAlterar implements Initializable{
	
	
	@FXML
	private TextField dataNascField;
	
	@FXML
    private TextField dataInicioField;
	

    @FXML
    private TextArea descricaoField;
	
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
    
    private AlunoTO alunoAtual;
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.addAlturaMask(alturaField);
		this.addCpfValidation(cpfField);
		this.addDateValidation(dataNascField);
		this.addPesoMask(pesoField);
		this.addDateValidation(dataInicioField);
//		tipoTreinoSelected.getItems().addAll(TipoTreino.values());
	}
	
	
	
	private ObservableList<AlunoTO> listaAlunos;

	public void setListaAlunos(ObservableList<AlunoTO> listaAlunos) {
	    this.listaAlunos = listaAlunos;
	}
	
	//função para o botão de cancelar que contém na tela
	public void cancelar(){
    	Main.loadView("listar");
	}
	
	
	public void setAluno(AlunoTO aluno){	
		System.out.println("Aluno selecionado para alterar: " + aluno);
	    this.alunoAtual = aluno;
	    nameField.setText(aluno.getNome());
	    cpfField.setText(aluno.getCpf());
	    pesoField.setText(aluno.getPeso().toString());
	    alturaField.setText(aluno.getAltura().toString());   
	}
	
	//função que salva as alterações que são feito no formulário
	public void salvarAlteracoes() {
		
		
	    if (alunoAtual.getId() == 0) {
	    	
	        Alert alertaErro = new Alert(AlertType.ERROR);
	        alertaErro.setTitle("Erro");
	        alertaErro.setHeaderText("Ocorreu um erro");
	        alertaErro.setContentText("Erro, ID de aluno inválido, impossível efetuar alteração");
	        alertaErro.showAndWait();
	        return;
	    }

	    // Atualiza os dados do alunoAtual
	    alunoAtual.setNome(nameField.getText());
	    alunoAtual.setCpf(cpfField.getText());
	    alunoAtual.setPeso(Double.parseDouble(pesoField.getText()));
	    alunoAtual.setAltura(Double.parseDouble(alturaField.getText()));
	    System.out.println(alunoAtual.getId());

	    try{
	        // Configura o DateTimeFormatter para o formato esperado
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	        alunoAtual.setDataNascimento(LocalDate.parse(dataNascField.getText(), formatter));

	      
	        alunoDAO dao = new alunoDAO();
	        
	        if (!dao.update(alunoAtual)) {
	        	
	            Alert alertaErro = new Alert(AlertType.ERROR);
	            alertaErro.setTitle("Erro");
	            alertaErro.setHeaderText("Ocorreu um erro");
	            alertaErro.setContentText("Erro ao atualizar o aluno no banco de dados");
	            alertaErro.showAndWait();
	            
	        } else {
	        	
	            Alert alertaSucesso = new Alert(AlertType.INFORMATION);
	            alertaSucesso.setTitle("Sucesso");
	            alertaSucesso.setHeaderText("Atualização bem-sucedida");
	            alertaSucesso.setContentText("O aluno foi alterado com sucesso.");
	            alertaSucesso.showAndWait();

	            // Atualiza o ObservableList
	            int index = listaAlunos.indexOf(alunoAtual);
	            if (index >= 0) {
	                listaAlunos.set(index, alunoAtual);
	            }
	            Stage stageAtual = (Stage) btAlterar.getScene().getWindow();
	            stageAtual.close();
	        }
	    }catch (DateTimeParseException e){
	        Alert alertaErro = new Alert(AlertType.ERROR);
	        alertaErro.setTitle("Erro");
	        alertaErro.setHeaderText("Erro no formato da data");
	        alertaErro.setContentText("Por favor, insira a data no formato yyyy/MM/dd.");
	        alertaErro.showAndWait();
	        e.printStackTrace();     
	    }catch(Exception e){
	        Alert alertaErro = new Alert(AlertType.ERROR);
	        alertaErro.setTitle("Erro");
	        alertaErro.setHeaderText("Erro ao salvar alterações");
	        alertaErro.setContentText("Ocorreu um problema ao salvar as alterações.");
	        alertaErro.showAndWait();
	        e.printStackTrace();
	    }
	}


	
	
	
	
	//Mascara da altura para o formulario de alterar
	private void addAlturaMask(TextField textField) {
	    textField.textProperty().addListener((observable, oldValue, newValue) -> {
	        
	    	
	        String cleanValue = newValue.replaceAll("[^\\d]", "");
	        if (cleanValue.length() > 3) {
	            cleanValue = cleanValue.substring(0, 3);
	        }
	
	       
	        String formattedValue;
	        if (cleanValue.length() >= 2) {
	            formattedValue = cleanValue.substring(0, 1) + "." + cleanValue.substring(1);
	        } else {
	            formattedValue = cleanValue;
	        }
	
	        textField.setText(formattedValue);
	
	        Platform.runLater(() -> textField.positionCaret(formattedValue.length()));
	    });
	    }


	    //função que faz a formatação da data para ddMMyyyy enquanto o usuário digita e faz a validação
	private void addDateValidation(TextField textField) {
	    textField.textProperty().addListener((observable, oldValue, newValue) -> {
	        Platform.runLater(() -> {
	            
	            String value = newValue.replaceAll("[^\\d]", "");
	            StringBuilder formatted = new StringBuilder();

	            
	            if (value.length() > 8) {
	                textField.setText(oldValue);
	                return;
	            }

	            for (int i = 0; i < value.length(); i++) {
	                if (i == 4 || i == 6) {
	                    formatted.append("/");
	                }
	                formatted.append(value.charAt(i));
	            }

	            // Atualiza o campo de texto somente se o valor formatado for diferente do novo valor
	            if (!formatted.toString().equals(newValue)) {
	                int caretPosition = textField.getCaretPosition(); 
	                textField.setText(formatted.toString());

	                // Reajusta o cursor para a posição correta
	                textField.positionCaret(Math.min(caretPosition + 1, formatted.length()));
	            }
	        });
	    });
	}

	//Mascara do PESO para o formulrio de ALTERAR
	private void addPesoMask(TextField textField) {
	    textField.textProperty().addListener((observable, oldValue, newValue) -> {
	        Platform.runLater(() -> {
	            
	            String cleanValue = newValue.replaceAll("[^\\d]", "");

	            
	            if (cleanValue.length() > 4) {
	                cleanValue = cleanValue.substring(0, 4);
	            }     
	            String formattedValue;
	            if (cleanValue.length() >= 3) {
	            
	                formattedValue = cleanValue.substring(0, cleanValue.length() - 1) + "." + cleanValue.substring(cleanValue.length() - 1);
	            } else if (cleanValue.length() >= 2) {
	            
	                formattedValue = cleanValue.substring(0, cleanValue.length() - 1) + "." + cleanValue.substring(cleanValue.length() - 1);
	            } else {
	                // Apenas 1 dígito (ou vazio)
	                formattedValue = cleanValue;
	            }	            
	            if (!formattedValue.equals(newValue)){
	                textField.setText(formattedValue);
	                textField.positionCaret(formattedValue.length());
	            }
	        });
	    });
	}


	    // Função para formatar e validar CPF no formato 000.000.000-00 enquanto o usuário digita
	    private void addCpfValidation(TextField textField) {
	        textField.textProperty().addListener((observable, oldValue, newValue) -> {
	            Platform.runLater(() -> {
	                String value = newValue.replaceAll("[^\\d]", "");
	                StringBuilder formatted = new StringBuilder();

	                if (value.length() > 11) {
	                    textField.setText(oldValue); 
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

	                if (!formatted.toString().equals(newValue)) { 
	                    textField.setText(formatted.toString());
	                }
	            });
	        });
	    }


}
