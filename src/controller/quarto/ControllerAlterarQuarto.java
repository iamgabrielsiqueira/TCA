package controller.quarto;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;

public class ControllerAlterarQuarto {

    @FXML
    public Parent mainWindow;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfDescricao;

    @FXML
    private ComboBox<TipoQuarto> tfTipo;

    private ObservableList<TipoQuarto> listaTipos;

    private Quarto q1;

    public void initialize() throws Exception {
        this.q1 = JDBCQuartoDAO.q1;

        listaTipos = FXCollections.observableArrayList(JDBCTipoQuartoDAO.getInstance().list());
        tfTipo.setItems(listaTipos);

        tfNumero.setText(String.valueOf(q1.getNumero()));
        tfDescricao.setText(q1.getDescricao());

        tfTipo.getSelectionModel().select(q1.getTipoQuarto());
        listaTipos = FXCollections.observableArrayList(JDBCTipoQuartoDAO.getInstance().list());
        tfTipo.setItems(listaTipos);
    }

    @FXML
    public void salvarQuarto() {

        try {
            int numero = Integer.parseInt(tfNumero.getText());

            TipoQuarto tipoQuarto = tfTipo.getSelectionModel().getSelectedItem();

            if(tfNumero.getText() != null && tipoQuarto!=null) {
                Quarto quarto = new Quarto();
                quarto.setNumero(numero);

                if(tfDescricao.getText().isEmpty()) {
                    String descricao = "Sem descrição";
                    quarto.setDescricao(descricao);
                } else {
                    String descricao = tfDescricao.getText();
                    quarto.setDescricao(descricao);
                }

                quarto.setTipoQuarto(tipoQuarto);

                try {
                    JDBCQuartoDAO.getInstance().update(q1, quarto);
                    mensagem(Alert.AlertType.INFORMATION, "Atualizado!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mensagem(Alert.AlertType.ERROR, "Dados faltando!");
            }
        } catch (NullPointerException e) {
            mensagem(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    @FXML
    public void voltar() {
        switchWindow("../../view/janelaQuarto.fxml");
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

    protected void mensagem(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensagem!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
