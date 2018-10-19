package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Estado;
import model.Hospede;
import model.JDBCEstadoDAO;

import java.io.IOException;
import java.time.LocalDate;

public class ControllerCadastroHospede {

    @FXML
    public Parent mainWindow;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfRg;

    @FXML
    private TextField tfTelefone;

    @FXML
    private DatePicker tfDataNasc;

    @FXML
    private ComboBox<Hospede> tfEstado;

    @FXML
    private ComboBox<Hospede> tfCidade;

    private ObservableList<Estado> listaEstado;

    public void initialize() throws Exception {
        listaEstado = FXCollections.observableArrayList(JDBCEstadoDAO.getInstance().list());
        tfEstado.setItems(listaEstado);

    }

    @FXML
    public void salvarHospede() {
        String nome = tfNome.getText();
        String cpf = tfCpf.getText();
        String rg = tfRg.getText();
        String telefone = tfTelefone.getText();
        LocalDate dataNasc = tfDataNasc.getValue();
    }

    @FXML
    public void voltar() {
        switchWindow("../view/janelaHospede.fxml");
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

    protected void message(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Message!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
