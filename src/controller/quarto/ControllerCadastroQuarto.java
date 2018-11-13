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
import model.classes.Quarto;
import model.classes.TipoQuarto;
import model.jdbc.JDBCQuartoDAO;
import model.jdbc.JDBCTipoQuartoDAO;
import java.io.IOException;

public class ControllerCadastroQuarto {

    @FXML
    public Parent mainWindow;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfDescricao;

    @FXML
    private ComboBox<TipoQuarto> tfTipo;

    private ObservableList<TipoQuarto> listaTipos;

    @FXML
    public void salvarQuarto() {

        if(tfNumero.getText().isEmpty() || tfTipo.getSelectionModel().isEmpty()) {
            mensagem(Alert.AlertType.ERROR, "Dados faltando!");
        } else {
            try {
                int num = Integer.parseInt(tfNumero.getText());

                TipoQuarto tipoQuarto = tfTipo.getSelectionModel().getSelectedItem();

                Quarto quarto = new Quarto();
                quarto.setNumero(num);

                if(tfDescricao.getText().isEmpty()) {
                    String descricao = "Sem descrição";
                    quarto.setDescricao(descricao);
                } else {
                    String descricao = tfDescricao.getText();
                    quarto.setDescricao(descricao);
                }

                quarto.setTipoQuarto(tipoQuarto);

                try {
                    JDBCQuartoDAO.getInstance().create(quarto);
                    mensagem(Alert.AlertType.INFORMATION, "Quarto cadastrado!");
                } catch (Exception e) {
                    mensagem(Alert.AlertType.ERROR, "Erro!");
                }
            } catch (NullPointerException e) {
                mensagem(Alert.AlertType.ERROR, "Erro!");
            }
        }
    }

    @FXML
    public void voltar() {
        trocarJanela("../../view/quarto/janelaQuarto.fxml");
    }

    public void initialize() throws Exception {
        listaTipos = FXCollections.observableArrayList(JDBCTipoQuartoDAO.getInstance().list());
        tfTipo.setItems(listaTipos);
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
