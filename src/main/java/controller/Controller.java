package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Enum.TipoTreino;
import Models.Aluno;
import Models.Treino;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import repository.Repository;

public class Controller {

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
    private void initialize() {
        // Limita altura e peso para apenas números e ponto decimal
        addNumericValidation(alturaField);
        addNumericValidation(pesoField);

        // Limita a data de nascimento para o formato dd/mm/yyyy
        addDateValidation(dataNascField);
        
        tipoTreinoSelected.getItems().addAll(TipoTreino.values());

    }
    
    private void handleCadastro() {
    	String dataNascimentoTxt = dataNascField.getText();
    	DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");	
    	
    	
    	String nome = nameField.getText();
    	String cpf = cpfField.getText();
    	LocalDate dataNasc = LocalDate.parse(dataNascimentoTxt,formatoData);
    	Double peso = Double.parseDouble(pesoField.getText());
    	Double altura = Double.parseDouble(alturaField.getText());
    	
    	String descricao = descricaoTextField.getText();
    	TipoTreino tipoTreino = tipoTreinoSelected.getValue();
    	LocalDate dataTreino = LocalDate.now();
    	Treino treino  = new Treino(descricao,dataTreino,tipoTreino);
    	Aluno aluno = new Aluno(nome,cpf,dataNasc,peso,altura,treino);
    	Repository repository = new Repository();
    	repository.insert(aluno);
    	
    }
    
    
    //função para apenas permite números e no formato real, neste caso permitindo ter apenas um ponto 
    private void addNumericValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) { 
                textField.setText(oldValue); 
            }
        });
    }
    
    //função para apenas numericos serem inseridos neste textField de data e com o formato correto.
    private void addDateValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,2}/?\\d{0,2}/?\\d{0,4}")) { // Formato dd/mm/yyyy
                textField.setText(oldValue);
            }
        });
    }	
    

}
