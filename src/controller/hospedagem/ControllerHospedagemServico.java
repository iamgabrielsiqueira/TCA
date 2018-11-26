package controller.hospedagem;

import controller.Mensagem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.classes.Hospedagem;
import model.classes.HospedagemServico;
import model.classes.Hospede;
import model.classes.Servico;
import model.jdbc.JDBCHospedagemDAO;
import model.jdbc.JDBCHospedagemServicoDAO;
import model.jdbc.JDBCServicoDAO;
import java.io.IOException;
import java.util.Optional;

public class ControllerHospedagemServico {

    @FXML
    public Parent mainWindow;

    @FXML
    private Label lbHospede;

    @FXML
    private Label lbTotal;

    @FXML
    private ListView<Servico> ltvwServico;

    @FXML
    private ListView<Servico> ltvwConta;

    private Double valorTotal;

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
    public void salvar() throws Exception {

        Hospedagem hospedagem = JDBCHospedagemDAO.h1;

        for (Servico servico : ltvwServico.getItems()) {
            JDBCHospedagemServicoDAO.getInstance().delete(hospedagem, servico);
        }

        for (Servico servico: ltvwConta.getItems()) {
            JDBCHospedagemServicoDAO.getInstance().create(hospedagem, servico);
        }

        mostrarMensagem("Conta atualizada!");
        voltar();
    }

    public void initialize() throws Exception {

        Hospedagem hospedagem = JDBCHospedagemDAO.h1;

        Hospede hospede = hospedagem.getHospede01();

        lbHospede.setText("Hóspede: " + hospede.getNome());

        populaListView();

        ltvwServico.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ltvwConta.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        ltvwServico.setCellFactory(new Callback<ListView<Servico>, ListCell<Servico>>() {
            @Override
            public ListCell<Servico> call(ListView<Servico> param) {
                ListCell<Servico> cell = new ListCell<Servico>(){
                    @Override
                    protected void updateItem(Servico item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        } else {
                            setText(item.getNome() + " — R$" + item.getValor());
                        }
                    }
                };

                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Servico item = ltvwServico.getSelectionModel().getSelectedItem();
                        ltvwConta.getItems().add(item);
                        valorTotal = valorTotal + item.getValor();
                        lbTotal.setText("Total: R$" + valorTotal);
                    }
                });

                cell.emptyProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasEmpty, Boolean isNowEmpty) {

                        if(isNowEmpty){
                            cell.setContextMenu(null);
                        }else{
                            //cell.setContextMenu();
                        }
                    }});


                return cell;

            }
        });


        ltvwConta.setCellFactory(new Callback<ListView<Servico>, ListCell<Servico>>() {
            @Override
            public ListCell<Servico> call(ListView<Servico> param) {
                ListCell<Servico> cell = new ListCell<Servico>(){
                    @Override
                    protected void updateItem(Servico item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        } else {
                            setText(item.getNome());
                        }
                    }
                };

                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Servico item = ltvwConta.getSelectionModel().getSelectedItem();
                        ltvwConta.getItems().remove(item);
                        valorTotal = valorTotal - item.getValor();
                        lbTotal.setText("Total: R$" + valorTotal);
                    }
                });

                cell.emptyProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> obs, Boolean wasEmpty, Boolean isNowEmpty) {

                        if(isNowEmpty){
                            cell.setContextMenu(null);
                        }else{
                            //cell.setContextMenu();
                        }
                    }});


                return cell;

            }
        });

    }

    public void populaListView() throws Exception {

        valorTotal = 0.0;

        ltvwServico.getItems().clear();

        for(Servico servico: JDBCServicoDAO.getInstance().list()) {
            ltvwServico.getItems().add(servico);
        }

        ltvwConta.getItems().clear();

        Hospedagem hospedagem = JDBCHospedagemDAO.h1;

        for (HospedagemServico hospedagemServico : JDBCHospedagemServicoDAO.getInstance().list(hospedagem)) {
            ltvwConta.getItems().add(hospedagemServico.getServico());
            valorTotal = valorTotal + hospedagemServico.getServico().getValor();
        }

        lbTotal.setText("Total: R$" + valorTotal);

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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
