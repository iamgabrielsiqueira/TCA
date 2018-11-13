package controller.servico;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.jdbc.JDBCServicoDAO;
import model.classes.Servico;

import java.io.IOException;

public class ControllerVisualizarServico {

    @FXML
    public Parent mainWindow;

    @FXML
    private Label lbNome;

    @FXML
    private Label lbValor;

    @FXML
    private Label lbDescricao;

    private Servico servico;

    public void initialize() {
        this.servico = JDBCServicoDAO.s1;
        lbNome.setText("Nome: " + servico.getNome());
        lbDescricao.setText("Descrição: " + servico.getDescricao());
        lbValor.setText("Valor: " + servico.getValor());
    }

    @FXML
    public void voltar() {
        switchWindow("../../view/servico/janelaServico.fxml");
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
