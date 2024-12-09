package telas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class cadastrar extends Application{
	
	
	@Override
	public void start (Stage primaryStage) throws Exception {
		
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/cadastrar.fxml"));
		Scene scene = new Scene(root);//coloca o FXML em uma cena
		primaryStage.setTitle("cadastrar alunos");
		primaryStage.centerOnScreen();
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
