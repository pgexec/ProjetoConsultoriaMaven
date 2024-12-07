package controller;

import javafx.scene.control.Button;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Enum.TipoTreino;
import Models.Aluno;
import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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

    @FXML
    private ComboBox<TipoTreino> tipoTreinoSelected;
    
    @FXML
    private Button btvoltar;
    
    @FXML
    private void initialize() {
    	
        
        addAlturaMask(alturaField);
        addPesoMask(pesoField);    
        addCpfValidation(cpfField);
        addDateValidation(dataNascField);
        tipoTreinoSelected.getItems().addAll(TipoTreino.values());
    }
    
    public void voltarMenu() {
    	Main.loadView("main");
    }
   
    @FXML
    public void handleCadastro(){
    	
    	String dataNascimentoTxt = dataNascField.getText();
    	DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");	
    	
    	
    	String nome = nameField.getText();
    	String cpf = cpfField.getText();
    	LocalDate dataNasc = LocalDate.parse(dataNascimentoTxt,formatoData);
    	Double peso = Double.parseDouble(pesoField.getText());
    	Double altura = Double.parseDouble(alturaField.getText());
    	Aluno aluno = new Aluno(nome,cpf,dataNasc,peso,altura,null);
    	
    	Repository repository = new Repository();
    	repository.insert(aluno);  	
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



