package controller;

import Enum.Intensidade;
import Enum.NivelDificuldade;
import Enum.TipoTreino;
import Models.Treino;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    @FXML
    public void initialize() {
   
        tipoTreinoComboBox.getItems().addAll(TipoTreino.values());
        intensidadeComboBox.getItems().addAll(Intensidade.values());
        nivelDificuldadeComboBox.getItems().addAll(NivelDificuldade.values());
        criarTreinoButton.setOnAction(event -> criarTreino());
        cancelarButton.setOnAction(event -> cancelar());
    }

    private void criarTreino() {
        try {
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

            // Criar o objeto Treino
            Treino treino = new Treino(0, 0, tipoTreino, intensidade, data, nivelDificuldade); // Ajuste alunoId conforme necessário
            System.out.println("Treino criado com sucesso: " + treino);

            // Exibir mensagem de sucesso
            exibirAlerta(AlertType.INFORMATION, "Treino criado com sucesso!", treino.toString());

        } catch (IllegalArgumentException e) {
            // Exibir mensagem de erro
            exibirAlerta(AlertType.ERROR, "Erro ao criar treino", e.getMessage());
        }
    }

    private void cancelar() {
    	Main.loadView("listar");
    }

    private void exibirAlerta(AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
