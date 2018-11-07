package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.JDBCTipoQuartoDAO;
import model.TipoQuarto;
import view.TextFieldMoney;
import java.io.IOException;

public class ControllerCadastroTipo {

    @FXML
    public Parent mainWindow;

    @FXML
    private TextField tfNome;

    @FXML
    private TextFieldMoney tfValor;

    @FXML
    private TextField tfDescricao;

    @FXML
    public void salvarTipo() throws Exception {
        String nome = tfNome.getText();
        Double valor = Double.valueOf(tfValor.getCleanValue());
        String descricao = tfDescricao.getText();
        System.out.println(valor);

        TipoQuarto tipoQuarto = new TipoQuarto();
        tipoQuarto.setNome(nome);
        tipoQuarto.setValor(valor);
        tipoQuarto.setDescricao(descricao);

        JDBCTipoQuartoDAO.getInstance().create(tipoQuarto);
    }

    @FXML
    public void voltar() {
        switchWindow("../view/janelaTipoQuarto.fxml");
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
}
