package controller.hospedagem;

import controller.Mensagem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.classes.Hospedagem;
import model.jdbc.JDBCHospedagemDAO;
import java.io.IOException;
import java.util.Optional;

public class ControllerVisualizarHospedagem {

    @FXML
    public Parent mainWindow;

    @FXML
    public Label lbCheckIn;

    @FXML
    public Label lbCheckOut;

    @FXML
    public Label lbHospede1;

    @FXML
    public Label lbHospede2;

    @FXML
    public Label lbHospede3;

    @FXML
    public Label lbQuarto;

    @FXML
    public Label lbValor;

    @FXML
    public void voltar() {
        trocarJanela("../../view/hospedagem/janelaHospedagem.fxml");
    }

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
    public void carregarHospedagens() {
        trocarJanela("../../view/hospedagem/janelaHospedagem.fxml");
    }

    public void initialize() {
        Hospedagem hospedagem = JDBCHospedagemDAO.h1;

        lbCheckIn.setText("Check In: " + hospedagem.getDataCheckIn());
        lbCheckOut.setText("Check Out: " + hospedagem.getDataCheckOut());
        lbHospede1.setText("Hóspede Principal: " + hospedagem.getHospede01().getNome());

        if(hospedagem.getHospede02() == null) {
            lbHospede2.setText("Hóspede Secundário: Nenhum");
        } else {
            lbHospede2.setText("Hóspede Secundário: " + hospedagem.getHospede01().getNome());
        }

        if(hospedagem.getHospede03() == null) {
            lbHospede3.setText("Hóspede Terciário: Nenhum");
        } else {
            lbHospede3.setText("Hóspede Terciário: " + hospedagem.getHospede01().getNome());
        }

        lbQuarto.setText("Quarto: " + hospedagem.getQuarto().getNumero()+" - "+hospedagem.getQuarto().getTipoQuarto().getNome());

        lbValor.setText("Valor: R$" + hospedagem.getValor());
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

            if(result.isPresent() && result.get()==ButtonType.OK) { }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
