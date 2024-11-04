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
        addCpfValidation(cpfField);
        // Limita a data de nascimento para o formato dd/mm/yyyy
        addDateValidation(dataNascField);
        
        tipoTreinoSelected.getItems().addAll(TipoTreino.values());

    }
    
    @FXML
    public void handleCadastro() {
    	
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
    	System.out.println("nome:" + aluno.getNome());
    	System.out.println("cpf: " + aluno.getCpf());
    	System.out.println("Peso:" + aluno.getPeso());
    	System.out.println("Altura: " + aluno.getAltura());
    	System.out.println("Descricao: " + aluno.getTreino().getDescricao());
    	System.out.println("Tipo de treino: " + aluno.getTreino().getTipoTreino());
    	System.out.println("Data: : " + aluno.getTreino().getData());
    	//Repository repository = new Repository();
    	//repository.insert(aluno);
    	
    }
    
    
    //função para apenas permite números e no formato real, neste caso permitindo ter apenas um ponto 
    private void addNumericValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) { 
                textField.setText(oldValue); 
            }
        });
    }
    
    // Função para formatar e validar a data no formato dd/MM/yyyy enquanto o usuário digita
    private void addDateValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove caracteres não numéricos
            String value = newValue.replaceAll("[^\\d]", "");
            StringBuilder formatted = new StringBuilder();

            if (value.length() > 8) {
                textField.setText(oldValue); // Limita a 8 caracteres (ddMMyyyy)
                return;
            }

            // Aplica a formatação dd/MM/yyyy
            for (int i = 0; i < value.length(); i++) {
                if (i == 2 || i == 4) {
                    formatted.append("/");
                }
                formatted.append(value.charAt(i));
            }
            textField.setText(formatted.toString());
        });
    }

    // Função para formatar e validar CPF no formato 000.000.000-00 enquanto o usuário digita
    private void addCpfValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove caracteres não numéricos
            String value = newValue.replaceAll("[^\\d]", "");
            StringBuilder formatted = new StringBuilder();

            if (value.length() > 11) {
                textField.setText(oldValue); // Limita a 11 caracteres (apenas números)
                return;
            }

            // Aplica a formatação 000.000.000-00
            for (int i = 0; i < value.length(); i++) {
                if (i == 3 || i == 6) {
                    formatted.append(".");
                } else if (i == 9) {
                    formatted.append("-");
                }
                formatted.append(value.charAt(i));
            }
            textField.setText(formatted.toString());
        });
    }
    

}
