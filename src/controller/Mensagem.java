package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;

public class Mensagem {

    @FXML
    public Parent mainWindow;

    @FXML
    public Label lbMensagem;

    public static String mensagem;

    public void initialize() {
        lbMensagem.setText(mensagem);
    }

}
