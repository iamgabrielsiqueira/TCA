package controller.servico;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.jdbc.JDBCServicoDAO;
import model.classes.Servico;
import java.io.IOException;
import java.util.Optional;

public class ControllerJanelaServico {

    @FXML
    public Parent mainWindow;

    @FXML
    private TableView tbServico;

    @FXML
    private TableColumn tcNome;

    @FXML
    private TableColumn tcValor;

    @FXML
    private TableColumn tcDescricao;

    public void initialize() throws Exception {
        carregarLista();
    }

    @FXML
    public void voltar() {
        switchWindow("../../view/janelaPrincipal.fxml");
    }

    @FXML
    public void cadastrar() {
        switchWindow("../../view/servico/janelaCadastrarServico.fxml");
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
            fxmlLoader.setLocation(getClass().getResource("../../view/servico/janelaRedirecionamentoServico.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            ControllerRedirecionamentoServico controle = fxmlLoader.getController();

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){

                Servico s = controle.processResult();

                JDBCServicoDAO.s1 = s;

                if(s!=null) {
                    try {
                        switch (id) {
                            case 1:
                                switchWindow("../../view/servico/janelaAlterarServico.fxml");
                                break;
                            case 2:
                                switchWindow("../../view/servico/janelaVisualizarServico.fxml");
                                break;
                            case 3:
                                switchWindow("../../view/servico/janelaRemoverServico.fxml");
                                break;
                        }
                    } catch (Exception e) {
                        mensagem(Alert.AlertType.ERROR, e.getMessage());
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
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tbServico.setItems(JDBCServicoDAO.getInstance().list());
    }

    protected void mensagem(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensagem!");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
