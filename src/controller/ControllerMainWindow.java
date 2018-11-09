package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ControllerMainWindow {

    @FXML
    public Parent mainWindow;

    @FXML
    public void carregarHospedes() {
        switchWindow("../view/janelaHospede.fxml");
    }

    @FXML
    public void carregarTiposQuartos() {
        switchWindow("../view/janelaTipoQuarto.fxml");
    }

    @FXML
    public void carregarQuartos() {
        switchWindow("../view/janelaQuarto.fxml");
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

}
