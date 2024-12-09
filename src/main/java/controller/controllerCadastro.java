package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//import Enum.TipoTreino;
import Models.Aluno;
import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
//import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import repository.Repository;

public class controllerCadastro{

    @FXML
    private TextField alturaField;

    @FXML
    private TextField cpfField;

    @FXML
    private TextField dataNascField;

    @FXML
    private TextArea descricaoTextField;

    @FXML
    private TextField nameField;

    @FXML
    private Pane paneCadastro;

    @FXML
    private TextField pesoField;

//    @FXML
//    private ComboBox<TipoTreino> tipoTreinoSelected;

    @FXML
    private Button btCancelar;

    @FXML
    private Button btFinalizar;

    @FXML
    private Button btVoltar;
    
    @FXML
    private void initialize() {
    	
        
        addAlturaMask(alturaField);
        addPesoMask(pesoField);    
        addCpfValidation(cpfField);
        addDateValidation(dataNascField);
//        tipoTreinoSelected.getItems().addAll(TipoTreino.values());
    }
    
    public void voltarMenu() {
    	Main.loadView("main");
    }
   
    @FXML
    private void cancelarCadastro() {
        // Exibe um alerta de confirmação
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
            "Tem certeza que deseja cancelar o cadastro? Todos os dados serão perdidos.", 
            ButtonType.YES, ButtonType.NO);
        alert.setTitle("Cancelar Cadastro");
        alert.setHeaderText(null);

        // Verifica a escolha do usuário
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            limparCampos(); // Limpa os campos do formulário
            voltarMenu();   // Retorna ao menu principal ou tela inicial
        }
    }

    // Função auxiliar para limpar os campos do formulário
    private void limparCampos() {
        nameField.clear();
        cpfField.clear();
        dataNascField.clear();
        pesoField.clear();
        alturaField.clear();
    }
    
    @FXML
    public void handleCadastro() {
	    try {
	        // Validação de campos obrigatórios
	        if (nameField.getText().isEmpty() || 
	            cpfField.getText().isEmpty() || 
	            dataNascField.getText().isEmpty() || 
	            pesoField.getText().isEmpty() || 
	            alturaField.getText().isEmpty()) {
	            
	            Alert alert = new Alert(Alert.AlertType.ERROR, "Preencha todos os campos obrigatórios.");
	            alert.setTitle("Erro de Validação");
	            alert.setHeaderText(null);
	            alert.showAndWait();
	            return;
	        }
	
	        // Conversão de dados
	        String nome = nameField.getText();
	        String cpf = cpfField.getText();
	        LocalDate dataNasc = LocalDate.parse(dataNascField.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	        Double peso = Double.parseDouble(pesoField.getText());
	        Double altura = Double.parseDouble(alturaField.getText());
	
	        // Criação do objeto Aluno
	        Aluno aluno = new Aluno(nome, cpf, dataNasc, peso, altura, null);
	
	        // Inserção no repositório
	        Repository repository = new Repository();
	        repository.insert(aluno);
	
	        // Confirmação e pergunta sobre o treino
	        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Cadastro finalizado! Deseja cadastrar um treino para o cliente?", ButtonType.YES, ButtonType.NO);
	        alert.setTitle("Cadastro Concluído");
	        alert.setHeaderText(null);
	
	        // Resposta do usuário
	        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
	            carregarTelaTreino(); // Carrega a tela de cadastro de treino
	        } else {
	            limparCampos(); // Limpa os campos do formulário
	            voltarMenu();   // Retorna ao menu principal
	        }
	
	    } catch (Exception e) {
	        // Tratamento de exceções e exibição de alerta em caso de erro
	        Alert alert = new Alert(Alert.AlertType.ERROR, "Ocorreu um erro ao processar o cadastro. Verifique os dados e tente novamente.");
	        alert.setTitle("Erro no Cadastro");
	        alert.setHeaderText(null);
	        alert.showAndWait();
	        e.printStackTrace(); // Para depuração
	    }
	}
	    
    private void carregarTelaTreino() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/telaCriarTreino.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btFinalizar.getScene().getWindow(); // Pega a janela atual
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
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
                    if (i == 2 || i == 4) {
                        formatted.append("/");
                    }
                    formatted.append(value.charAt(i));
                }

                
                if (!formatted.toString().equals(newValue)) {
                    int caretPosition = textField.getCaretPosition(); 
                    textField.setText(formatted.toString());

                    textField.positionCaret(Math.min(caretPosition + 1, formatted.length()));
                }
            });
        });
    }
    
    private void addPesoMask(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
           
            String cleanValue = newValue.replaceAll("[^\\d]", "");

          
            if (cleanValue.length() > 4) {
                cleanValue = cleanValue.substring(0, 4);
            }

          
            String formattedValue;
            if (cleanValue.length() >= 4) {
                formattedValue = cleanValue.substring(0, 3) + "." + cleanValue.substring(3);
            } else {
                formattedValue = cleanValue;
            }

          
            textField.setText(formattedValue);

          
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



