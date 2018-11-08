package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Hospede;
import model.JDBCHospedeDAO;
import java.io.IOException;
import java.util.Optional;

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

    @FXML
    public void alterar() {
        redirecionar(1);
    }

    @FXML
    public void visualizar() {
        redirecionar(2);
    }

    @FXML
    public void remover() {
        redirecionar(3);
    }

    public void redirecionar(int id) {

        Dialog<ButtonType> dialog = new Dialog();

        switch(id) {
            case 1:
                dialog.setTitle("Alterar");
                break;
            case 2:
                dialog.setTitle("Visualizar");
                break;
            case 3:
                dialog.setTitle("Remover");
                break;
        }

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../view/janelaRedirecionamento.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            ControllerJanelaRedirecionamentoHospede controle = fxmlLoader.getController();

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){

                Hospede h = controle.processResult();

                JDBCHospedeDAO.h1 = h;

                if(h!=null) {
                    try {
                        switch (id) {
                            case 1:
                                switchWindow("../view/janelaAlterarHospede.fxml");
                                break;
                            case 2:
                                switchWindow("../view/janelaVisualizarHospede.fxml");
                                break;
                            case 3:
                                switchWindow("../view/janelaRemoverHospede.fxml");
                                break;
                        }
                    } catch (Exception e) {
                        message(Alert.AlertType.ERROR, e.getMessage());
                        System.out.printf(e.getMessage());
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

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

    protected void message(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensagem!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
