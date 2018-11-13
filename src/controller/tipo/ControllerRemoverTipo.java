package controller.tipo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.jdbc.JDBCTipoQuartoDAO;
import model.classes.TipoQuarto;
import java.io.IOException;

public class ControllerRemoverTipo {

    @FXML
    public Parent mainWindow;

    @FXML
    private Label lbNome;

    @FXML
    private Label lbValor;

    @FXML
    private Label lbDescricao;

    private TipoQuarto tipoQuarto;

    @FXML
    public void voltar() {
        switchWindow("../../view/tipo/janelaTipoQuarto.fxml");
    }

    @FXML
    public void removerTipo() {
        try {
            JDBCTipoQuartoDAO.getInstance().delete(tipoQuarto);
            mensagem(Alert.AlertType.INFORMATION, "Removido!");
            voltar();
        } catch (Exception e) {
            mensagem(Alert.AlertType.ERROR, "Erro!");
        }
    }

    public void initialize() {
        this.tipoQuarto = JDBCTipoQuartoDAO.t1;
        lbNome.setText("Nome: " + tipoQuarto.getNome());
        lbDescricao.setText("Descrição: " + tipoQuarto.getDescricao());
        lbValor.setText("Valor: " + tipoQuarto.getValor());
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
