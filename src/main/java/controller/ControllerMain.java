package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;

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
        btcadastro.setOnMouseClicked((MouseEvent e) -> {
            // Chamar a tela de cadastro
            Main.loadView("cadastrar");
        });

        btListar.setOnMouseClicked((MouseEvent e) -> {
            // Chamar a tela de listagem
            Main.loadView("listar");
        });

        btSair.setOnMouseClicked((MouseEvent e) -> {
            // Sair da aplicação
            System.exit(0);
        });
    }
}
