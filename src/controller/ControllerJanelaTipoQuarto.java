package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.JDBCHospedeDAO;
import model.JDBCTipoQuartoDAO;
import model.TipoQuarto;

import java.io.IOException;

public class ControllerJanelaTipoQuarto {

    @FXML
    public Parent mainWindow;

    @FXML
    private TableView tbTipos;

    @FXML
    private TableColumn tcNome;

    @FXML
    private TableColumn tcValor;

    @FXML
    private TableColumn tcDescricao;

    private ObservableList<TipoQuarto> listaTipos;

    public void initialize() throws Exception {
        carregarLista();
    }

    @FXML
    public void voltar() {
        switchWindow("../view/mainWindow.fxml");
    }

    @FXML
    public void cadastrar() {
        switchWindow("../view/janelaCadastrarHospede.fxml");
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

    private void carregarLista() throws Exception {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbTipos.setItems(JDBCTipoQuartoDAO.getInstance().list());
    }


}
