package controller.servico;

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
import model.jdbc.JDBCServicoDAO;
import model.classes.Servico;
import view.TextFieldMoney;
import java.io.IOException;
import java.util.Optional;

public class ControllerCadastroServico {

    @FXML
    public Parent mainWindow;

    @FXML
    private TextField tfNome;

    @FXML
    private TextFieldMoney tfValor;

    @FXML
    private TextField tfDescricao;

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
    public void salvarServico() throws Exception {

        try {
            if(tfNome.getText().isEmpty() || tfValor.getCleanValue().isEmpty()) {
                mostrarMensagem("Faltam dados!");
            } else {
                String nome = tfNome.getText();
                Double valor = Double.valueOf(tfValor.getCleanValue());
                String descricao;

                Servico servico = new Servico();
                servico.setNome(nome);
                servico.setValor(valor);

                if(tfDescricao.getText().isEmpty()) {
                    descricao = "Sem descrição";
                } else {
                    descricao = tfDescricao.getText();
                }

                servico.setDescricao(descricao);

                int flag = 0;

                for (Servico servico1 : JDBCServicoDAO.getInstance().list()) {
                    if(servico1.getNome().equals(servico.getNome())) {
                        flag = 1;
                    }
                }

                if(flag!=1) {
                    try {
                        JDBCServicoDAO.getInstance().create(servico);
                        mostrarMensagem("Serviço cadastrado!");
                        voltar();
                    } catch (Exception e) {
                        mostrarMensagem("Erro!");
                    }
                } else {
                    mostrarMensagem("Este serviço já existe!");
                }
            }
        } catch (NullPointerException e) {
            mostrarMensagem("Faltam dados!");
        }

    }

    @FXML
    public void voltar() {
        trocarJanela("../../view/servico/janelaServico.fxml");
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
