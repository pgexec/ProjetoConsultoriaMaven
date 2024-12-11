package telas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class criarTreino extends Application{
	
	
	@Override
	public void start (Stage primaryStage) throws Exception {
		
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/telaCriarTreino.fxml"));
		Scene scene = new Scene(root);//coloca o FXML em uma cena
		primaryStage.setTitle("criar treino");
		primaryStage.centerOnScreen();
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
