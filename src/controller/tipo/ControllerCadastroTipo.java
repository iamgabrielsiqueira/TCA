package controller.tipo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.jdbc.JDBCTipoQuartoDAO;
import model.classes.TipoQuarto;
import view.TextFieldMoney;
import java.io.IOException;

public class ControllerCadastroTipo {

    @FXML
    public Parent mainWindow;

    @FXML
    private TextField tfNome;

    @FXML
    private TextFieldMoney tfValor;

    @FXML
    private TextField tfDescricao;

    @FXML
    public void salvarTipo() {
        if(tfNome.getText().isEmpty() || tfValor.getCleanValue().isEmpty()) {
            mensagem(Alert.AlertType.ERROR, "Erro!");
        } else {
            String nome = tfNome.getText();
            Double valor = Double.valueOf(tfValor.getCleanValue());
            String descricao = tfDescricao.getText();

            TipoQuarto tipoQuarto = new TipoQuarto();
            tipoQuarto.setNome(nome);
            tipoQuarto.setValor(valor);
            tipoQuarto.setDescricao(descricao);

            try {
                JDBCTipoQuartoDAO.getInstance().create(tipoQuarto);
                mensagem(Alert.AlertType.INFORMATION, "Tipo de Quarto cadastrado!");
            } catch (Exception e) {
                mensagem(Alert.AlertType.ERROR, "Erro!");
            }
        }
    }

    @FXML
    public void voltar() {
        trocarJanela("../../view/tipo/janelaTipoQuarto.fxml");
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
