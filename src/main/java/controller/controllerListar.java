package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import DAOs.alunoDAO;
import DTOs.AlunoTO;
import Models.Aluno;
import application.Main;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import repository.Repository;


public class controllerListar {

	   @FXML
	    private Button btAlterar;

	    @FXML
	    private Button btVisualizar;

	    @FXML
	    private Button btVoltar;
	    
	    @FXML
	    private Button btCriarTreino;
	    
	    @FXML
	    private Button btExcluir;
	    
	    @FXML
	    private TableColumn <AlunoTO, Integer> columnID;
	    
	    @FXML
	    private TableColumn<AlunoTO, String> columnNome;

	    @FXML
	    private TableColumn<AlunoTO, String> columnCpf;

	    @FXML
	    private TableColumn<AlunoTO, LocalDate> columnData;

	    @FXML
	    private TableColumn<AlunoTO, Double> columnPeso;

	    @FXML
	    private TableColumn<AlunoTO, Double> columnAltura;
	    
	    @FXML
	    public void initialize() {
	        listarAlunos();
	    }
	    
	    @FXML
	    private TableView<AlunoTO> tableViewAlunos;

	    public void visualizarDadosAluno() {
	        // Obtém o aluno selecionado na tabela
	        AlunoTO alunoSelecionado = tableViewAlunos.getSelectionModel().getSelectedItem();

	        // Verifica se um aluno foi selecionado
	        if (alunoSelecionado != null) {
	            // Cria um alert para mostrar os dados do aluno
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Detalhes do Aluno");
	            alert.setHeaderText("Informações do Aluno");

	            // Define o conteúdo do alert com os dados do aluno
	            String alunoInfo = "Nome: " + alunoSelecionado.getNome() + "\n"
	                             + "CPF: " + alunoSelecionado.getCpf() + "\n"
	                             + "Data de Nascimento: " + alunoSelecionado.getDataNascimento() + "\n"
	                             + "Peso: " + alunoSelecionado.getPeso() + " kg\n"
	                             + "Altura: " + alunoSelecionado.getAltura() + " m";
	            
	            alert.setContentText(alunoInfo);

	            // Exibe o alert
	            alert.showAndWait();
	        } else {
	            // Caso nenhum aluno seja selecionado, exibe um alerta de erro
	            Alert alerta = new Alert(AlertType.WARNING);
	            alerta.setTitle("Seleção Necessária");
	            alerta.setHeaderText("Nenhum Aluno Selecionado");
	            alerta.setContentText("Por favor, selecione um aluno na tabela para visualizar.");
	            alerta.showAndWait();
	        }
	    }
	    
	    public void listarAlunos() {
	        alunoDAO dao = new alunoDAO();

	        // Obtendo a lista de AlunoTO e carregando-a no ObservableList
	        ObservableList<AlunoTO> alunos = FXCollections.observableArrayList(dao.list(10, 0));

	        // Configurando as colunas da tabela para exibir apenas os dados desejados
	        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
	        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
	        columnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
	        columnPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
	        columnAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));
	        columnData.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDataNascimento()));

	        // Definindo os dados para a tabela
	        tableViewAlunos.setItems(alunos);
	    }

	    public void excluirAluno() {
	        AlunoTO alunoSelecionado = tableViewAlunos.getSelectionModel().getSelectedItem();
	        
	        if (alunoSelecionado != null) {
	            try {
	                // Tenta excluir o aluno do banco de dados
	                Repository repository = new Repository();
	                repository.delete(alunoSelecionado.getId());

	                // Remove o aluno da lista visível na tabela
	                tableViewAlunos.getItems().remove(alunoSelecionado);

	                // Exibe uma mensagem de sucesso
	                exibirMensagemDeConfirmacao(alunoSelecionado);

	            } catch (Exception e) {
	                // Caso ocorra um erro durante a exclusão
	                exibirMensagemDeErro(e);
	            }
	        } else {
	            // Caso não tenha selecionado um aluno
	            exibirMensagemDeSelecao();
	        }
	    }

	    // Método para exibir o alerta de confirmação de exclusão
	    private void exibirMensagemDeConfirmacao(AlunoTO alunoSelecionado) {
	        Alert confirmacao = new Alert(AlertType.INFORMATION);
	        confirmacao.setTitle("Confirmação de Exclusão");
	        confirmacao.setHeaderText("Exclusão Realizada com Sucesso");
	        confirmacao.setContentText("O aluno " + alunoSelecionado.getNome() + " foi excluído com sucesso.");
	        confirmacao.showAndWait();
	    }

	    // Método para exibir o alerta de erro em caso de falha na exclusão
	    private void exibirMensagemDeErro(Exception e) {
	        Alert erro = new Alert(AlertType.ERROR);
	        erro.setTitle("Erro de Exclusão");
	        erro.setHeaderText("Falha ao Excluir Aluno");
	        erro.setContentText("Ocorreu um erro ao tentar excluir o aluno. Erro: " + e.getMessage());
	        erro.showAndWait();
	    }

	    // Método para exibir o alerta caso nenhum aluno tenha sido selecionado
	    private void exibirMensagemDeSelecao() {
	        Alert alerta = new Alert(AlertType.ERROR);
	        alerta.setTitle("Erro de Exclusão");
	        alerta.setHeaderText("Nenhum Aluno Selecionado");
	        alerta.setContentText("Por favor, selecione um aluno na tabela antes de clicar em Excluir.");
	        alerta.showAndWait();
	    }

//	    @FXML
//	    private void alterarAluno(ActionEvent event) {
//	        // Obtém o aluno selecionado na tabela
//	        AlunoTO alunoSelecionado = tableViewAlunos.getSelectionModel().getSelectedItem();
//
//	        if (alunoSelecionado == null) {
//	            // Exibe alerta caso nenhum aluno esteja selecionado
//	            Alert alert = new Alert(Alert.AlertType.WARNING);
//	            alert.setTitle("Seleção Inválida");
//	            alert.setHeaderText("Nenhum Aluno Selecionado");
//	            alert.setContentText("Por favor, selecione um aluno na tabela para alterar.");
//	            alert.showAndWait();
//	            return;
//	        }
//
//	        // Alterar Nome
//	        TextInputDialog nomeDialog = new TextInputDialog(alunoSelecionado.getNome());
//	        nomeDialog.setTitle("Alterar Dados do Aluno");
//	        nomeDialog.setHeaderText("Alterar Nome do Aluno");
//	        nomeDialog.setContentText("Insira o novo nome:");
//	        Optional<String> nomeResult = nomeDialog.showAndWait();
//	        nomeResult.ifPresent(novoNome -> {
//	            if (!novoNome.trim().isEmpty()) {
//	                alunoSelecionado.setNome(novoNome);
//	            }
//	        });
//
//	        // Alterar CPF
//	        TextInputDialog cpfDialog = new TextInputDialog(alunoSelecionado.getCpf());
//	        cpfDialog.setTitle("Alterar Dados do Aluno");
//	        cpfDialog.setHeaderText("Alterar CPF do Aluno");
//	        cpfDialog.setContentText("Insira o novo CPF:");
//	        Optional<String> cpfResult = cpfDialog.showAndWait();
//	        cpfResult.ifPresent(novoCpf -> {
//	            if (!novoCpf.trim().isEmpty()) {
//	                alunoSelecionado.setCpf(novoCpf);
//	            }
//	        });
//
//	        // Alterar Data de Nascimento
//	        DatePicker dataNascimentoDialog = new DatePicker();
//	        dataNascimentoDialog.setValue(alunoSelecionado.getDataNascimento());
//	        Alert dataAlert = new Alert(Alert.AlertType.CONFIRMATION);
//	        dataAlert.setTitle("Alterar Dados do Aluno");
//	        dataAlert.setHeaderText("Alterar Data de Nascimento");
//	        dataAlert.getDialogPane().setContent(dataNascimentoDialog);
//	        Optional<ButtonType> dataResult = dataAlert.showAndWait();
//	        if (dataResult.isPresent() && dataResult.get() == ButtonType.OK) {
//	            alunoSelecionado.setDataNascimento(dataNascimentoDialog.getValue());
//	        }
//
//	        // Alterar Peso
//	        TextInputDialog pesoDialog = new TextInputDialog(String.valueOf(alunoSelecionado.getPeso()));
//	        pesoDialog.setTitle("Alterar Dados do Aluno");
//	        pesoDialog.setHeaderText("Alterar Peso do Aluno");
//	        pesoDialog.setContentText("Insira o novo peso:");
//	        Optional<String> pesoResult = pesoDialog.showAndWait();
//	        pesoResult.ifPresent(novoPeso -> {
//	            if (!novoPeso.trim().isEmpty()) {
//	                try {
//	                    double peso = Double.parseDouble(novoPeso);
//	                    alunoSelecionado.setPeso(peso);
//	                } catch (NumberFormatException e) {
//	                    Alert error = new Alert(Alert.AlertType.ERROR, "O peso deve ser um número válido.", ButtonType.OK);
//	                    error.showAndWait();
//	                }
//	            }
//	        });
//
//	        // Alterar Altura
//	        TextInputDialog alturaDialog = new TextInputDialog(String.valueOf(alunoSelecionado.getAltura()));
//	        alturaDialog.setTitle("Alterar Dados do Aluno");
//	        alturaDialog.setHeaderText("Alterar Altura do Aluno");
//	        alturaDialog.setContentText("Insira a nova altura:");
//	        Optional<String> alturaResult = alturaDialog.showAndWait();
//	        alturaResult.ifPresent(novaAltura -> {
//	            if (!novaAltura.trim().isEmpty()) {
//	                try {
//	                    double altura = Double.parseDouble(novaAltura);
//	                    alunoSelecionado.setAltura(altura);
//	                } catch (NumberFormatException e) {
//	                    Alert error = new Alert(Alert.AlertType.ERROR, "A altura deve ser um número válido.", ButtonType.OK);
//	                    error.showAndWait();
//	                }
//	            }
//	        });
//
//	        // Atualiza visualmente a tabela (caso necessário)
//	        tableViewAlunos.refresh();
//
//	        // Exibe confirmação
//	        Alert confirm = new Alert(Alert.AlertType.INFORMATION, "Dados alterados com sucesso!", ButtonType.OK);
//	        confirm.showAndWait();
//	    }

	    public void alterarAluno() {

            AlunoTO alunoSelecionado = tableViewAlunos.getSelectionModel().getSelectedItem();
            System.out.println(alunoSelecionado);
            
            controllerAlterar.alunoAtual = alunoSelecionado;
            controllerAlterar.listaAlunos = tableViewAlunos.getItems()
;
            if (alunoSelecionado != null) {
            	Main.loadView("alterar");                
            } else {
                Alert alerta = new Alert(AlertType.WARNING);
                alerta.setTitle("Seleção Necessária");
                alerta.setHeaderText("Nenhum Aluno Selecionado");
                alerta.setContentText("Por favor, selecione um aluno na tabela para alterar.");
                alerta.showAndWait();
            }
	    }
	    
	    public void voltarMenu() {
	    	Main.loadView("main");
	    }
	    
	    public void criarTreinoAluno() {
	    	// Obtém o aluno selecionado na tabela
		    AlunoTO alunoSelecionado = tableViewAlunos.getSelectionModel().getSelectedItem();
		    
		 // Carrega a tela de criação de treino
		    try {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/telaCriarTreino.fxml"));
		        Parent root = loader.load();

		        // Obtém o controlador da tela de criação de treino
		        controllerCriarTreino controllerCriarTreino = loader.getController();
		        
		        // Passa o ID do aluno para o controlador da tela de criação de treino
		        controllerCriarTreino.setAlunoId(alunoSelecionado.getId());
		        
		        // Cria uma nova janela para mostrar a tela de criação de treino
		        Stage stage = new Stage();
		        stage.setScene(new Scene(root));
		        stage.setTitle("Criar Treino");
		        stage.show();
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
	    }
}
