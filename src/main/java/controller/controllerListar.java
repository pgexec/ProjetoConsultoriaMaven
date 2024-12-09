package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DAOs.alunoDAO;
import DTOs.AlunoTO;
import Models.Aluno;
import application.Main;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
	    
	    	Repository repository = new Repository();
	    	AlunoTO alunoSelecionado = tableViewAlunos.getSelectionModel().getSelectedItem();
	    	
	    	if(alunoSelecionado != null) {
	    		
	    		repository.delete(alunoSelecionado.getId());
	    		tableViewAlunos.getItems().remove(alunoSelecionado);
	    		
	    		Alert confirmacao = new Alert(AlertType.INFORMATION);
	            confirmacao.setTitle("Confirmação de Exclusão");
	            confirmacao.setHeaderText("Exclusão Realizada com Sucesso");
	            confirmacao.setContentText("O aluno " + alunoSelecionado.getNome() + " foi excluído com sucesso.");
	            confirmacao.showAndWait();
	            
	    	}else {
	    		
	    		 Alert alerta = new Alert(AlertType.ERROR);
	    	     alerta.setTitle("Erro de Exclusão");
	    	     alerta.setHeaderText("Nenhum Aluno Selecionado");
	    	     alerta.setContentText("Por favor, selecione um aluno na tabela antes de clicar em Excluir.");
	    	     alerta.showAndWait();
	    	}
	    }
	    
	    public void alterarAluno() {
		    // Obtém o aluno selecionado na tabela
		    AlunoTO alunoSelecionado = tableViewAlunos.getSelectionModel().getSelectedItem();
		
		    // Verifica se um aluno foi selecionado
		    if (alunoSelecionado != null) {
		        try {
		            // Carrega a tela de alteração (alterar.fxml)
		            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/alterar.fxml"));
		            Parent root = loader.load();
		            
		            // Obtém o controlador da tela de alteração
		            controllerAlterar controller = loader.getController();
		            
		            // Passa o aluno selecionado e a lista de alunos para o controlador
		            controller.setAluno(alunoSelecionado);
		            controller.setListaAlunos(tableViewAlunos.getItems());
		
		            // Cria e exibe a nova janela
		            Stage stage = new Stage();
		            stage.setScene(new Scene(root));
		            stage.setTitle("Alterar Aluno");
		            stage.show();
		
		            // Se desejar fechar a janela de listagem após abrir a tela de alteração, pode ser feito assim:
		            // Stage currentStage = (Stage) btAlterar.getScene().getWindow();
		            // currentStage.close();
		
		        } catch (IOException e) {
		            // Caso ocorra algum erro ao carregar a tela de alteração, exibe um alerta
		            e.printStackTrace();
		            Alert alerta = new Alert(AlertType.ERROR);
		            alerta.setTitle("Erro ao Carregar a Tela");
		            alerta.setHeaderText("Não foi possível carregar a tela de alteração.");
		            alerta.setContentText("Por favor, verifique o arquivo alterar.fxml e tente novamente.");
		            alerta.showAndWait();
		        }
		    } else {
		        // Caso nenhum aluno seja selecionado, exibe um alerta de aviso
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
	
		public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("Inicializando a tabela");
		this.listarAlunos();
		
		btVisualizar.setOnAction(event -> visualizarDadosAluno());
	}
}
