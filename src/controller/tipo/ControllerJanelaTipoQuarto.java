package controller.tipo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.jdbc.JDBCTipoQuartoDAO;
import model.classes.TipoQuarto;
import java.io.IOException;
import java.util.Optional;

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

    @FXML
    public void voltar() {
        trocarJanela("../../view/janelaPrincipal.fxml");
    }

    @FXML
    public void cadastrar() {
        trocarJanela("../../view/tipo/janelaCadastrarTipo.fxml");
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

    public void initialize() {
        try {
            carregarLista();
        } catch (Exception e) {
            mensagem(Alert.AlertType.ERROR, "Erro!");
        }
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
            fxmlLoader.setLocation(getClass().getResource("../../view/tipo/janelaRedirecionamentoTipo.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            ControllerRedirecionamentoTipo controle = fxmlLoader.getController();

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){

                TipoQuarto t = controle.processResult();

                JDBCTipoQuartoDAO.t1 = t;

                if(t!=null) {
                    try {
                        switch (id) {
                            case 1:
                                trocarJanela("../../view/tipo/janelaAlterarTipo.fxml");
                                break;
                            case 2:
                                trocarJanela("../../view/tipo/janelaVisualizarTipo.fxml");
                                break;
                            case 3:
                                trocarJanela("../../view/tipo/janelaRemoverTipo.fxml");
                                break;
                        }
                    } catch (Exception e) {
                        mensagem(Alert.AlertType.ERROR, "Erro!");
                    }
                }
            }
        }catch (IOException e){
            mensagem(Alert.AlertType.ERROR, "Erro!");
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
                    mensagem(Alert.AlertType.ERROR, "Erro!");
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

    protected void mensagem(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensagem!");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
