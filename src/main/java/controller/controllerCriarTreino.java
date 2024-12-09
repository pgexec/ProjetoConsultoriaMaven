package controller;

import Enum.Intensidade;
import Enum.NivelDificuldade;
import Enum.TipoTreino;
import Models.Treino;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import repository.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import DTOs.AlunoTO;
import DTOs.TreinoTO;

public class controllerCriarTreino {

    @FXML
    private ComboBox<TipoTreino> tipoTreinoComboBox;

    @FXML
    private ComboBox<Intensidade> intensidadeComboBox;

    @FXML
    private ComboBox<NivelDificuldade> nivelDificuldadeComboBox;

    @FXML
    private TextField dataInicioField;

    @FXML
    private Button criarTreinoButton;

    @FXML
    private Button cancelarButton;
    
    private int alunoId;  // Variável para armazenar o ID do aluno

    // Método para definir o ID do aluno
    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
        // Aqui você pode usar o alunoId para buscar informações adicionais do aluno ou para configurar a tela.
        System.out.println("ID do aluno: " + alunoId);
    }

	public int getAlunoId() {
		return alunoId;
	}



	@FXML
    public void initialize() {
   
        tipoTreinoComboBox.getItems().addAll(TipoTreino.values());
        intensidadeComboBox.getItems().addAll(Intensidade.values());
        nivelDificuldadeComboBox.getItems().addAll(NivelDificuldade.values());
        cancelarButton.setOnAction(event -> cancelar());
    }

    private void criarTreino() {
	    try {
	        // Obter o aluno selecionado da tabela
	    	int alunoSelecionado = getAlunoId();
	
	        // Validar e obter os valores preenchidos
	        TipoTreino tipoTreino = tipoTreinoComboBox.getValue();
	        if (tipoTreino == null) throw new IllegalArgumentException("Selecione o Tipo de Treino.");
	
	        Intensidade intensidade = intensidadeComboBox.getValue();
	        if (intensidade == null) throw new IllegalArgumentException("Selecione a Intensidade.");
	
	        NivelDificuldade nivelDificuldade = nivelDificuldadeComboBox.getValue();
	        if (nivelDificuldade == null) throw new IllegalArgumentException("Selecione o Nível de Dificuldade.");
	
	        String dataTexto = dataInicioField.getText();
	        if (dataTexto == null || dataTexto.isEmpty()) throw new IllegalArgumentException("Preencha a Data de Início.");
	        
	        LocalDate data;
	        try {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	            data = LocalDate.parse(dataTexto, formatter);
	        } catch (DateTimeParseException e) {
	            throw new IllegalArgumentException("A Data de Início deve estar no formato dd/MM/yyyy.");
	        }
	
	        // Criar o objeto TreinoTO com o alunoId do aluno selecionado
	        TreinoTO treinoTO = new TreinoTO(0, alunoSelecionado, tipoTreino, intensidade, data, nivelDificuldade); // ID será gerado no banco
	
	        // Inserção no repositório
	        Repository repository = new Repository();
	        repository.insert(treinoTO, alunoSelecionado);
	        
	        
	        exibirAlerta(AlertType.INFORMATION, "Treino criado com sucesso!", treinoTO.toString());
	        
	
	    } catch (IllegalArgumentException e) {
	        // Exibir mensagem de erro
	        exibirAlerta(AlertType.ERROR, "Erro ao criar treino", e.getMessage());
	    }
	}
    
    private void cancelar() {
    	Main.loadView("listar");
    }

 // Valida se os campos obrigatórios estão preenchidos
    private boolean validarDadosTreino() {
        return tipoTreinoComboBox.getValue() != null &&
               !intensidadeComboBox.getPromptText().isEmpty();
    }
    
 // Limpa os campos do formulário
    private void limparCampos() {
        tipoTreinoComboBox.setValue(null);
        intensidadeComboBox.setValue(null);
        dataInicioField.clear();
        nivelDificuldadeComboBox.setValue(null);
    }
    
    public void voltarMenu() {
    	Main.loadView("main");
    }
    
 // Botão "Salvar"
    @FXML
    private void salvarTreino() {
        if (validarDadosTreino()) {
            System.out.println("Treino salvo com sucesso!");
            limparCampos();
            voltarMenu(); // Volta ao menu principal ou à tela anterior
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Preencha todos os campos corretamente.");
            alert.setTitle("Erro de Validação");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    
    private void exibirAlerta(AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
