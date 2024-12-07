package application;


	
import java.util.HashMap;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static Stage stage;
		
	@Override
	public void start(Stage primaryStage) {
		
		stage = primaryStage;
		HashMap<String, Object> dados = new HashMap<>();
        stage.setUserData(dados); 
		loadView("main");
	}

	
	public static void main(String[] args) {
		launch(args);
	}

	//abre view passando dados a salvar
	public static <V> void loadView(String view) {
		String tela = "/views/"+ view +".fxml";
		try {
	        System.out.println("Carregando: " + tela);
			Pane root = FXMLLoader.load(Main.class.getResource(tela));
			Scene scene = new Scene(root,1000,1000);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
	        System.err.println("Erro ao carregar a tela: " + tela);
			e.printStackTrace();
		}
	}
	
	

}