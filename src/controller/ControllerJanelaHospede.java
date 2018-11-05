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
import model.Hospede;
import model.JDBCHospedeDAO;

import java.io.IOException;

public class ControllerJanelaHospede {

    @FXML
    public Parent mainWindow;

    @FXML
    private TableView tbHospedes;

    @FXML
    private TableColumn tcNome;

    @FXML
    private TableColumn tcCPF;

    @FXML
    private TableColumn tcData;

    private ObservableList<Hospede> listaHospedes;

    public void initialize() throws Exception {

//        try {
//            for (Hospede hospede:JDBCHospedeDAO.getInstance().list()) {
//                System.out.println(hospede.getNome());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
        tcCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
        tbHospedes.setItems(JDBCHospedeDAO.getInstance().list());
    }


}
