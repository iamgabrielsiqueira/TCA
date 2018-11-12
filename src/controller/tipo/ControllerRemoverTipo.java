package controller.tipo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Hospede;
import model.JDBCHospedeDAO;
import model.JDBCTipoQuartoDAO;
import model.TipoQuarto;
import view.TextFieldMoney;
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

    public void initialize() {
        this.tipoQuarto = JDBCTipoQuartoDAO.t1;
        lbNome.setText("Nome: " + tipoQuarto.getNome());
        lbDescricao.setText("Descrição: " + tipoQuarto.getDescricao());
        lbValor.setText("Valor: " + tipoQuarto.getValor());
    }

    @FXML
    public void voltar() {
        switchWindow("../../view/tipo/janelaTipoQuarto.fxml");
    }

    @FXML
    public void removerTipo() throws Exception {
        JDBCTipoQuartoDAO.getInstance().delete(tipoQuarto);
        message(Alert.AlertType.INFORMATION, "Removido!");
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

    protected void message(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensagem!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
