package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Hospede;
import model.JDBCHospedeDAO;
import java.io.IOException;

public class ControllerVisualizarHospede {

    @FXML
    public Parent mainWindow;

    @FXML
    private Label lbNome;

    @FXML
    private Label lbCPF;

    @FXML
    private Label lbRG;

    @FXML
    private Label lbTelefone;

    @FXML
    private Label lbDataNasc;

    @FXML
    private Label lbEstado;

    @FXML
    private Label lbCidade;

    public void initialize() {
        Hospede hospede = JDBCHospedeDAO.h1;

        lbNome.setText("Nome: " + hospede.getNome());
        lbCPF.setText("CPF: " + hospede.getCpf());
        lbRG.setText("RG: " + hospede.getRg());
        lbTelefone.setText("Telefone: " + hospede.getTelefone());
        lbDataNasc.setText("Data de Nascimento: " + hospede.getDataNasc());
        lbEstado.setText("Estado: " + hospede.getEstado().getNome());
        lbCidade.setText("Cidade: " + hospede.getCidade().getNome());
    }

    @FXML
    public void voltar() {
        switchWindow("../view/janelaHospede.fxml");
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
