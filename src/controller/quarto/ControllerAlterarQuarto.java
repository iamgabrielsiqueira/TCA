package controller.quarto;

import controller.Mensagem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.classes.Quarto;
import model.classes.TipoQuarto;
import model.jdbc.JDBCQuartoDAO;
import model.jdbc.JDBCTipoQuartoDAO;
import java.io.IOException;
import java.util.Optional;

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
    public void salvarQuarto() {
        if(tfNumero.getText().isEmpty()) {
            mostrarMensagem("Dados faltando!");
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
                    JDBCQuartoDAO.getInstance().update(q1, quarto);
                    mostrarMensagem("Quarto alterado!");
                    voltar();
                } catch (Exception e) {
                    mostrarMensagem("Erro!");
                }
            } catch (NullPointerException e) {
                mostrarMensagem("Erro!");
            }
        }
    }

    @FXML
    public void voltar() {
        trocarJanela("../../view/quarto/janelaQuarto.fxml");
    }

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

            if(result.isPresent() && result.get()==ButtonType.OK) {
                //System.out.println("1: " + mensagem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
