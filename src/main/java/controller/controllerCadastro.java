package controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import DTOs.AlunoTO;
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
    private TableView<AlunoTO> tableViewAlunos;
    
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
            String nome = nameField.getText().trim();
            String cpf = cpfField.getText().trim();
            LocalDate dataNasc;
            try {
                dataNasc = LocalDate.parse(dataNascField.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, insira uma data válida no formato dd/MM/yyyy.");
                alert.setTitle("Erro de Formato");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            Double peso, altura;
            try {
                peso = Double.parseDouble(pesoField.getText().trim());
                altura = Double.parseDouble(alturaField.getText().trim());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Por favor, insira valores numéricos válidos para peso e altura.");
                alert.setTitle("Erro de Formato");
                alert.setHeaderText(null);
                alert.showAndWait();
                return;
            }

            // Criação do objeto Aluno
            Aluno aluno = new Aluno(nome, cpf, dataNasc, peso, altura, null);

            // Operação de persistência
            Repository repository = new Repository();
            repository.insert(aluno);

            
            System.out.println(repository.toString());
         // Criação do dialog de confirmação
            System.out.println("Criando o diálogo de confirmação.");
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Cadastro Concluído");
            dialog.setHeaderText(null);

            // Botões personalizados
            ButtonType buttonYes = new ButtonType("Sim", ButtonBar.ButtonData.YES);
            ButtonType buttonNo = new ButtonType("Não", ButtonBar.ButtonData.NO);
            System.out.println("Adicionando botões personalizados.");
            dialog.getDialogPane().getButtonTypes().addAll(buttonYes, buttonNo);

            // Mensagem no corpo do dialog
            System.out.println("Adicionando a mensagem no corpo do diálogo.");
            dialog.getDialogPane().setContentText("Cadastro finalizado! Deseja cadastrar um treino para o cliente?");

           
           // Exibe o dialog e aguarda a resposta
            System.out.println("Exibindo o diálogo.");
            ButtonType result = dialog.showAndWait().orElse(buttonNo);  // Corrigido aqui
            System.out.println("Resultado do diálogo: " + result);
                
          // Resposta do usuário
            if (result == buttonYes) {
               System.out.println("Usuário escolheu 'Sim'. Redirecionando para a tela de treino.");
//               AlunoTO alunoSelecionado = repository.insert(aluno);
//               System.out.println(alunoSelecionado);
               
//               controllerCriarTreino.alunoAtual = alunoSelecionado;
               Main.loadView("criarTreino");
           } else {
               System.out.println("Usuário escolheu 'Não'. Limpando campos e voltando ao menu.");
               limparCampos(); // Limpa os campos
               voltarMenu();   // Retorna ao menu principal
           }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ocorreu um erro inesperado: " + e.getMessage());
            alert.setTitle("Erro Inesperado");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }



    private void carregarTelaTreino() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/telaCriarTreino.fxml"));
            Parent root = loader.load();
            
            // Configura a nova cena e define na janela atual
            Stage stage = (Stage) btFinalizar.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            // Exibe um alerta ao usuário sobre o erro
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao carregar a tela de treino. Por favor, tente novamente.");
            alert.setTitle("Erro ao Carregar Tela");
            alert.setHeaderText(null);
            alert.showAndWait();

            // Log do erro para facilitar a depuração
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



