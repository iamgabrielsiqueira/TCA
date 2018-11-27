package controller.quarto;

import controller.Mensagem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.classes.Hospedagem;
import model.classes.Quarto;
import model.jdbc.JDBCHospedagemDAO;
import model.jdbc.JDBCQuartoDAO;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

public class ControllerQuartosDisponiveis {

    @FXML
    public Parent mainWindow;

    @FXML
    private TableView tbQuartos;

    @FXML
    private TableColumn tcNumero;

    @FXML
    private TableColumn tcDescricao;

    @FXML
    private TableColumn tcTipo;

    @FXML
    private DatePicker dataInicio;

    @FXML
    private DatePicker dataFinal;

    private ObservableList<Quarto> listaFiltrada;

    private ObservableList<Quarto> listaFiltrada2;

    private ObservableList<Quarto> listaRemover;

    @FXML
    public void voltar() {
        trocarJanela("../../view/quarto/janelaQuarto.fxml");
    }

    @FXML
    public void cadastrar() {
        trocarJanela("../../view/quarto/janelaCadastrarQuarto.fxml");
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
    public void filtrar() throws Exception {

        if(dataInicio.getValue() != null && dataFinal.getValue() != null) {
            Date data1 = Date.valueOf(dataInicio.getValue());
            Date data2 = Date.valueOf(dataFinal.getValue());
            filtraLista(data1, data2);
        } else {
            carregarLista();
        }

    }

    public void filtraLista(Date data1, Date data2) throws Exception {

        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        listaFiltrada = FXCollections.observableArrayList();
        listaFiltrada = JDBCQuartoDAO.getInstance().list();

        listaFiltrada2 = FXCollections.observableArrayList();
        listaRemover = FXCollections.observableArrayList();

        for (Hospedagem hospedagem : JDBCHospedagemDAO.getInstance().list()) {
            try {
                String data3 = myFormat.format(fromUser.parse(hospedagem.getDataCheckIn()));
                Date checkIn = Date.valueOf(data3);
                String data4 = myFormat.format(fromUser.parse(hospedagem.getDataCheckOut()));
                Date checkOut = Date.valueOf(data4);

                for (Quarto q : JDBCQuartoDAO.getInstance().list()) {
                    if(hospedagem.getQuarto().getId() == q.getId()) {

                        // Se a data digitada for depois ao do check in
                        if(data1.after(checkIn) && data2.before(checkOut) && data1.before(checkOut) && data2.after(checkIn)) {

                            listaRemover.add(hospedagem.getQuarto());
                        } else if (data1.equals(checkIn) && data2.equals(checkOut) ) {

                            listaRemover.add(hospedagem.getQuarto());
                        } else if (data1.before(checkIn) && data2.before(checkOut) && data1.before(checkOut) && data2.after(checkIn)) {

                            listaRemover.add(hospedagem.getQuarto());
                        } else if (data1.after(checkIn) && data2.after(checkOut) && data1.before(checkOut) && data2.after(checkIn)) {

                            listaRemover.add(hospedagem.getQuarto());
                        } else {

                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int diferente = 0;

        for (Quarto quarto : JDBCQuartoDAO.getInstance().list()) {
            for (Quarto quarto1 : listaRemover) {
                if(quarto.getId() == quarto1.getId()) {

                    diferente = 1;
                }
            }

            if(diferente == 0) {

                listaFiltrada2.add(quarto);
            }

            diferente = 0;
        }

        tbQuartos.getItems().clear();
        tbQuartos.setItems(listaFiltrada2);

    }

    public void initialize() throws Exception {
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipoQuarto"));

        tbQuartos.setItems(JDBCQuartoDAO.getInstance().list());
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

    private void carregarLista() throws Exception {
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipoQuarto"));
        tbQuartos.setItems(JDBCQuartoDAO.getInstance().list());
    }

    public void remover(Quarto quarto) throws Exception {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Remover");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/quarto/janelaRemoverQuarto.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("../../estilo.css").toExternalForm());
            dialog.getDialogPane().getStyleClass().add("myDialog");

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK) {
                if(quarto!=null) {
                    try {
                        JDBCQuartoDAO.getInstance().delete(quarto);
                    } catch (Exception e) {
                        mostrarMensagem("Erro!");
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            carregarLista();
        }
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
