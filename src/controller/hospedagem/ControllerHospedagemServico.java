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
import model.classes.Servico;
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

    public void initialize() throws Exception {

        populaListView();

        ltvwServico.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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
                            setText(item.getNome());
                        }
                    }
                };

                cell.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        Servico item = ltvwServico.getSelectionModel().getSelectedItem();
                        System.out.println(item.getNome());
                        ltvwConta.getItems().add(item);
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

        ltvwServico.getItems().clear();

        for(Servico servico: JDBCServicoDAO.getInstance().list()) {
            ltvwServico.getItems().add(servico);
        }

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
