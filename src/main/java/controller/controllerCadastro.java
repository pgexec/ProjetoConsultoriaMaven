package controller;

import javafx.scene.control.Button;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Enum.TipoTreino;
import Models.Aluno;
import Models.Treino;
import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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

    @FXML
    private ComboBox<TipoTreino> tipoTreinoSelected;
    
    @FXML
    private Button btvoltar;
    
    @FXML
    private void initialize() {
    	
        // Limita altura e peso para apenas números e ponto decimal
        addNumericValidation(alturaField);
        addNumericValidation(pesoField);
        addCpfValidation(cpfField);
        // Limita a data de nascimento para o formato dd/mm/yyyy
        addDateValidation(dataNascField);
        
    }
    
    public void voltarMenu() {
    	
    	//pegando tela atual (tela cadastro)
    	Stage stageAtual = (Stage) btvoltar.getScene().getWindow();
    	stageAtual.close(); //fechando ela
    	
    	Main main = new Main();

    	try {
    		main.start(new Stage());
    	}catch(Exception e) {
    		e.printStackTrace();
    	} 	
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
    	Repository repository = new Repository();
    	repository.insert(aluno);
    	
    }
    
    
    private void addNumericValidation(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (!newValue.matches("\\d*\\.?\\d*")) {
                    textField.setText(oldValue);
                }
            });
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



