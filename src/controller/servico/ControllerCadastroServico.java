package controller.servico;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.jdbc.JDBCServicoDAO;
import model.classes.Servico;
import view.TextFieldMoney;
import java.io.IOException;

public class ControllerCadastroServico {

    @FXML
    public Parent mainWindow;

    @FXML
    private TextField tfNome;

    @FXML
    private TextFieldMoney tfValor;

    @FXML
    private TextField tfDescricao;

    @FXML
    public void salvarServico() {
        if(tfNome.getText().isEmpty() || tfValor.getCleanValue().isEmpty()) {
            mensagem(Alert.AlertType.ERROR, "Erro!");
        } else {
            String nome = tfNome.getText();
            Double valor = Double.valueOf(tfValor.getCleanValue());
            String descricao = tfDescricao.getText();

            Servico servico = new Servico();
            servico.setNome(nome);
            servico.setValor(valor);
            servico.setDescricao(descricao);

            try {
                JDBCServicoDAO.getInstance().create(servico);
                mensagem(Alert.AlertType.INFORMATION, "Serviço cadastrado!");
            } catch (Exception e) {
                mensagem(Alert.AlertType.ERROR, "Erro!");
            }
        }
    }

    @FXML
    public void voltar() {
        trocarJanela("../../view/servico/janelaServico.fxml");
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
