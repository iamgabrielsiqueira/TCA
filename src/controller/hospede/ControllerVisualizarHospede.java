package controller.hospede;

import controller.Mensagem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.classes.Hospede;
import model.jdbc.JDBCHospedeDAO;
import java.io.IOException;
import java.util.Optional;

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

    public void initialize() {

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
                    mostrarMensagem("Erro!");
                }
            }
        });

    }

    protected void mostrarMensagem(String mensagem) {

        Mensagem.mensagem = mensagem;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Mensagem");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/janelaMensagem.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("../../estilo.css").toExternalForm());
            dialog.getDialogPane().getStyleClass().add("myDialog");

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK) {
                System.out.println("1: " + mensagem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
