package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.Aluno;
import application.Main;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
	    private Button btexcluir;

	    @FXML
	    private Button btVoltar;
	    
	    
	    @FXML
	    private TableColumn <Aluno, Integer> columnID;
	    
	    @FXML
	    private TableColumn<Aluno, String> columnNome;

	    @FXML
	    private TableColumn<Aluno, String> columnCpf;

	    @FXML
	    private TableColumn<Aluno, LocalDate> columnData;

	    @FXML
	    private TableColumn<Aluno, Double> columnPeso;

	    @FXML
	    private TableColumn<Aluno, Double> columnAltura;
	    
	    @FXML
	    private TableView<Aluno> tableViewAlunos;
	    
	    @FXML
	    private TableColumn<Aluno, Integer> columnIdTreino;
	    
	    @FXML
	    private TableColumn<Aluno,String> columnTipoTreino;
	    
	    @FXML
	    private TableColumn<Aluno,String> columnDescricao;
	    
	    @FXML
	    private TableColumn<Aluno, LocalDate> columnDataInicio;



	  
    
    public void listarAlunos() {
    	
    	Repository repository = new Repository();
    	
    	ObservableList<Aluno> alunos = FXCollections.observableArrayList(repository.list(5, 0));
    	
    	columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
    	columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    	columnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
    	columnPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        columnAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));
        columnData.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getDataNascimento()));

        columnIdTreino.setCellValueFactory(data -> { Integer idTreino = data.getValue().getTreino() != null ? data.getValue().getTreino().getId() : null;
            return new SimpleObjectProperty<>(idTreino);
        });
        columnDescricao.setCellValueFactory(data ->new SimpleStringProperty(data.getValue().getTreino() != null ? data.getValue().getTreino().getDescricao() : ""));
        
        columnDataInicio.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getTreino() != null ? data.getValue().getTreino().getData() : null));
    
        columnTipoTreino.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTreino() != null && data.getValue().getTreino().getTipoTreino() != null 
        ? data.getValue().getTreino().getTipoTreino().toString() 
        : ""));
    
    	tableViewAlunos.setItems(alunos);
     }
    
    public void excluirAluno() {
    
    	Repository repository = new Repository();
    	
    	Aluno alunoSelecionado = tableViewAlunos.getSelectionModel().getSelectedItem();
    	
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
    	
    Aluno alunoSelecionado = tableViewAlunos.getSelectionModel().getSelectedItem();
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
    	
    	Stage stageAtual = (Stage) btVoltar.getScene().getWindow();
    	stageAtual.close(); 
    	
    	Main main = new Main();

    	try {
    		main.start(new Stage());
    	}catch(Exception e) {
    		e.printStackTrace();
    	} 	
    }
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("Inicializando a tabela");
		this.listarAlunos();
		
		
	}
}
