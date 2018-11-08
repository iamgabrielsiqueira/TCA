package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.JDBCTipoQuartoDAO;
import model.TipoQuarto;
import view.TextFieldMoney;
import java.io.IOException;

public class ControllerVisualizarTipo {

    @FXML
    public Parent mainWindow;

    @FXML
    private Label lbNome;

    @FXML
    private Label lbValor;

    @FXML
    private Label lbDescricao;

    private TipoQuarto tipoQuarto;

    public void initialize() {
        this.tipoQuarto = JDBCTipoQuartoDAO.t1;
        lbNome.setText("Nome: " + tipoQuarto.getNome());
        lbDescricao.setText("Descrição: " + tipoQuarto.getDescricao());
        lbValor.setText("Valor: " + tipoQuarto.getValor());
    }

    @FXML
    public void voltar() {
        switchWindow("../view/janelaTipoQuarto.fxml");
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
