package controller.hospede;

import controller.MaskFieldUtil;
import controller.ValidaCPF;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jeanderson.br.util.MaskFormatter;
import model.classes.Cidade;
import model.jdbc.JDBCCidadeDAO;
import model.classes.Estado;
import model.jdbc.JDBCEstadoDAO;
import model.classes.Hospede;
import model.jdbc.JDBCHospedeDAO;
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
    private Label lbData;

    @FXML
    private Label lbCidade;

    @FXML
    private Label lbEstado;

    @FXML
    public void carregarHospedes() {
        trocarJanela("../../view/hospede/janelaHospede.fxml");
    }

    @FXML
    public void carregarTiposQuartos() {
        trocarJanela("../../view/tipo/janelaTipoQuarto.fxml");
    }

    @FXML
    public void carregarQuartos() {
        trocarJanela("../../view/quarto/janelaQuarto.fxml");
    }

    @FXML
    public void carregarServicos() {
        trocarJanela("../../view/servico/janelaServico.fxml");
    }

    @FXML
    public void voltar() {
        trocarJanela("../../view/hospede/janelaHospede.fxml");
    }

    public void initialize() throws Exception {

        Hospede hospede = JDBCHospedeDAO.h1;

        lbNome.setText("Nome: " + hospede.getNome());
        lbCPF.setText("CPF: " + hospede.getCpf());
        lbRG.setText("RG: " + hospede.getRg());
        lbTelefone.setText("Telefone: " + hospede.getTelefone());
        lbData.setText("Data de Nascimento: " + hospede.getDataNasc());
        lbCidade.setText("Cidade: " + hospede.getCidade());
        lbEstado.setText("Estado: " + hospede.getEstado());
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
