package controller.hospede;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.classes.Hospede;
import model.jdbc.JDBCHospedeDAO;
import java.io.IOException;

public class ControllerRemoverHospede {

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
    public void removerHospede() throws Exception {
        Hospede hospede = JDBCHospedeDAO.h1;
        JDBCHospedeDAO.getInstance().delete(hospede);
        mensagem(Alert.AlertType.INFORMATION, "Removido!");
        voltar();
    }

    @FXML
    public void voltar() {
        trocarJanela("../../view/hospede/janelaHospede.fxml");
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
