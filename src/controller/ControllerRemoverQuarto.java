package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import view.TextFieldMoney;
import java.io.IOException;

public class ControllerRemoverQuarto {

    @FXML
    public Parent mainWindow;

    @FXML
    private Label lbNumero;

    @FXML
    private Label lbTipo;

    @FXML
    private Label lbDescricao;

    private Quarto quarto;

    public void initialize() {
        this.quarto = JDBCQuartoDAO.q1;
        lbNumero.setText("Numero: " + quarto.getNumero());
        lbDescricao.setText("Descrição: " + quarto.getDescricao());
        lbTipo.setText("Tipo: " + quarto.getTipoQuarto().getNome());
    }

    @FXML
    public void voltar() {
        switchWindow("../view/janelaQuarto.fxml");
    }

    @FXML
    public void removerQuarto() throws Exception {
        JDBCQuartoDAO.getInstance().delete(quarto);
        mensagem(Alert.AlertType.INFORMATION, "Removido!");
        voltar();
    }

    public void switchWindow(String address){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(address));

                try {
                    Parent layoutWindow = loader.load();

                    Stage stage=(Stage)mainWindow.getScene().getWindow();

                    stage.setScene(new Scene(layoutWindow,800, 600));
                    stage.setResizable(false);

                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });

    }

    protected void mensagem(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensagem!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
