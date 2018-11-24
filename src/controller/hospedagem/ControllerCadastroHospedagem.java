package controller.hospedagem;

import controller.MaskFieldUtil;
import controller.Mensagem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.classes.*;
import model.jdbc.JDBCHospedagemDAO;
import model.jdbc.JDBCHospedeDAO;
import model.jdbc.JDBCQuartoDAO;
import java.io.IOException;
import java.util.Optional;

public class ControllerCadastroHospedagem {

    @FXML
    public Parent mainWindow;

    @FXML
    public TextField tfCheckIn;

    @FXML
    public TextField tfCheckOut;

    @FXML
    public ComboBox<Hospede> tfHospede;

    @FXML
    public ComboBox<Hospede> tfHospede2;

    @FXML
    public ComboBox<Hospede> tfHospede3;

    @FXML
    public ComboBox<Quarto> tfQuarto;

    private ObservableList<Hospede> listaHospede1;

    private ObservableList<Hospede> listaHospede2;

    private ObservableList<Hospede> listaHospede3;

    private ObservableList<Quarto> listaQuarto;

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

    @FXML
    public void salvarHospedagem() throws Exception {
        String checkIn = tfCheckIn.getText();
        String checkOut = tfCheckOut.getText();
        Hospede hospede = tfHospede.getSelectionModel().getSelectedItem();
        Hospede hospede2 = tfHospede2.getSelectionModel().getSelectedItem();
        Hospede hospede3 = tfHospede3.getSelectionModel().getSelectedItem();
        Quarto quarto = tfQuarto.getSelectionModel().getSelectedItem();

        Hospedagem hospedagem = new Hospedagem();
        hospedagem.setDataCheckIn(checkIn);
        hospedagem.setDataCheckOut(checkOut);
        hospedagem.setStatusCheckOut(false);
        hospedagem.setStatusCheckIn(false);
        hospedagem.setValor(quarto.getTipoQuarto().getValor());
        hospedagem.setStatus(true);
        hospedagem.setHospede01(hospede);
        hospedagem.setHospede02(hospede2);
        hospedagem.setHospede03(hospede3);
        hospedagem.setQuarto(quarto);

        JDBCHospedagemDAO.getInstance().create(hospedagem);
        mostrarMensagem("Hospedagem cadastrada!");
        voltar();
    }

    public void initialize() throws Exception {
        MaskFieldUtil.dateField(tfCheckIn);
        MaskFieldUtil.dateField(tfCheckOut);

        Hospede hospede = new Hospede();
        hospede.setNome("Nenhum");
        hospede.setId(0);

        listaHospede1 = FXCollections.observableArrayList(JDBCHospedeDAO.getInstance().list());
        tfHospede.setItems(listaHospede1);

        listaHospede2 = FXCollections.observableArrayList(JDBCHospedeDAO.getInstance().list());
        listaHospede2.add(0, hospede);
        tfHospede2.setItems(listaHospede2);

        listaHospede3 = FXCollections.observableArrayList(JDBCHospedeDAO.getInstance().list());
        listaHospede3.add(0, hospede);
        tfHospede3.setItems(listaHospede3);

        listaQuarto = FXCollections.observableArrayList(JDBCQuartoDAO.getInstance().list());
        tfQuarto.setItems(listaQuarto);
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
                //System.out.println("1: " + mensagem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
