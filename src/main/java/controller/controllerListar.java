package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Models.Aluno;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import repository.Repository;

public class controllerListar implements Initializable {

	   @FXML
	    private Button btAlterar;

	    @FXML
	    private Button btVisualizar;

	    @FXML
	    private Button btexcluir;
	    
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		System.out.println("Inicializando a tabela");
		this.listarAlunos();
		
		
	}
}