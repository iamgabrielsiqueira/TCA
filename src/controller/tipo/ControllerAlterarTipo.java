package controller.tipo;

import controller.Mensagem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.jdbc.JDBCTipoQuartoDAO;
import model.classes.TipoQuarto;
import view.TextFieldMoney;
import java.io.IOException;
import java.util.Optional;

public class ControllerAlterarTipo {

    @FXML
    public Parent mainWindow;

    @FXML
    private TextField tfNome;

    @FXML
    private TextFieldMoney tfValor;

    @FXML
    private TextField tfDescricao;

    private TipoQuarto tipoQuarto1;

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

    @FXML
    public void salvarTipo() {
        if (tfNome.getText().isEmpty() || tfValor.getCleanValue().isEmpty()) {
            mostrarMensagem("Erro!");
        } else {
            String nome = tfNome.getText();
            Double valor = Double.valueOf(tfValor.getCleanValue());
            String descricao = tfDescricao.getText();
            System.out.println(valor);

            TipoQuarto tipoQuarto = new TipoQuarto();
            tipoQuarto.setNome(nome);
            tipoQuarto.setValor(valor);
            tipoQuarto.setDescricao(descricao);

            try {
                JDBCTipoQuartoDAO.getInstance().update(tipoQuarto1, tipoQuarto);
                mostrarMensagem("Tipo de Quarto alterado!");
                voltar();
            } catch (Exception e) {
                mostrarMensagem("Erro!");
            }
        }
    }

    @FXML
    public void voltar() {
        trocarJanela("../../view/tipo/janelaTipoQuarto.fxml");
    }

    public void initialize() {
        this.tipoQuarto1 = JDBCTipoQuartoDAO.t1;

        tfNome.setText(tipoQuarto1.getNome());
        tfDescricao.setText(tipoQuarto1.getDescricao());
        tfValor.setText(String.valueOf(tipoQuarto1.getValor()));
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

            if(result.isPresent() && result.get()==ButtonType.OK) { }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
