package controller.servico;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    @FXML
    public void voltar() {
        trocarJanela("../../view/servico/janelaServico.fxml");
    }

    public void initialize() {
        this.servico = JDBCServicoDAO.s1;
        lbNome.setText("Nome: " + servico.getNome());
        lbDescricao.setText("Descrição: " + servico.getDescricao());
        lbValor.setText("Valor: " + servico.getValor());
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
