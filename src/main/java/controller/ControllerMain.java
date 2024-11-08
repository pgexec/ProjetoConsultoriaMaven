package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import telas.cadastrar;
import telas.listar;

public class ControllerMain implements Initializable {

    @FXML
    private Button btListar;

    @FXML
    private Button btSair;

    @FXML
    private Button btcadastro;

    @FXML
    private Pane paneMain;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		btcadastro.setOnMouseClicked((MouseEvent e)-> {
			
			//pegando Stage atual no caso a tela Main
			Stage stageAtual = (Stage) paneMain.getScene().getWindow();
			stageAtual.close(); //fechando ela
			
			//criação da tela cadastro
			cadastrar tlCadastro = new cadastrar();
			try {
				
				tlCadastro.start(new Stage());
				
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
		});
		
		
		btListar.setOnMouseClicked((MouseEvent e)-> {
			
			//pegando Stage atual no caso a tela Main
			Stage stageAtual = (Stage) paneMain.getScene().getWindow();
			stageAtual.close(); //fechando ela
			
			//criação da tela listar
			listar tlListar = new listar();
			try {
				
				tlListar.start(new Stage());
				
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
		});
		
	}
    
    
    
   
}
