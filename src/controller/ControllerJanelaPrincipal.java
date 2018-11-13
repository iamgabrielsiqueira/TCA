package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;

public class ControllerJanelaPrincipal {

    @FXML
    public Parent mainWindow;

    @FXML
    public void carregarHospedes() {
        trocarJanela("../view/hospede/janelaHospede.fxml");
    }

    @FXML
    public void carregarTiposQuartos() {
        trocarJanela("../view/tipo/janelaTipoQuarto.fxml");
    }

    @FXML
    public void carregarQuartos() {
        trocarJanela("../view/quarto/janelaQuarto.fxml");
    }

    @FXML
    public void carregarServicos() {
        trocarJanela("../view/servico/janelaServico.fxml");
    }

    public void trocarJanela(String address){

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
                    mensagem(Alert.AlertType.ERROR, "Erro!");
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
