package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DAOs.alunoDAO;
import DTOs.AlunoTO;
import application.Main;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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


public class controllerListar implements Initializable {

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
    	
    AlunoTO alunoSelecionado = tableViewAlunos.getSelectionModel().getSelectedItem();
    System.out.println(alunoSelecionado);

    if (alunoSelecionado != null) {
    	
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/alterar.fxml"));
            Parent root = loader.load();
                    
            controllerAlterar controller = loader.getController();
            
            
            controller.setAluno(alunoSelecionado);
            controller.setListaAlunos(tableViewAlunos.getItems());

            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Alterar Aluno");
            stage.show();
            
        } catch (IOException e) {
        	
            e.printStackTrace();
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro ao Carregar a Tela");
            alerta.setHeaderText("Não foi possível carregar a tela de alteração.");
            alerta.setContentText("Por favor, verifique o arquivo alterar.fxml e tente novamente.");
            alerta.showAndWait();
        }
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
    	Main.loadView("telaCriarTreino");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("Inicializando a tabela");
		this.listarAlunos();
		
		
	}
}
