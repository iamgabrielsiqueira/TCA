package controller.hospede;

import controller.MaskFieldData;
import controller.Mensagem;
import controller.ValidaCPF;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jeanderson.br.util.MaskFormatter;
import model.classes.Cidade;
import model.jdbc.JDBCCidadeDAO;
import model.classes.Estado;
import model.jdbc.JDBCEstadoDAO;
import model.classes.Hospede;
import model.jdbc.JDBCHospedeDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

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
    private TextField tfDataNasc;

    @FXML
    private ComboBox<Estado> tfEstado;

    @FXML
    private ComboBox<Cidade> tfCidade;

    private ObservableList<Estado> listaEstado;

    private ObservableList<Cidade> listaCidade;

    private ObservableList<Cidade> listaCidadeFiltro;

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
    public void carregarCidade() {
        Estado estadoSelecionado = tfEstado.getSelectionModel().getSelectedItem();

        tfCidade.getItems().clear();

        try {
            listaCidadeFiltro = JDBCCidadeDAO.getInstance().listaPorEstado(estadoSelecionado);
            tfCidade.setItems(listaCidadeFiltro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void salvarHospede() throws Exception {

        try {
            if (tfNome.getText().isEmpty() || tfCpf.getText().isEmpty() ||
                    tfRg.getText().isEmpty() || tfTelefone.getText().isEmpty() ||
                    tfDataNasc.getText().isEmpty()) {
                mostrarMensagem("Faltam dados!");
            } else {
                String nome = tfNome.getText();
                String cpf = tfCpf.getText();
                String rg = tfRg.getText();
                String telefone = tfTelefone.getText();
                String dataNasc = tfDataNasc.getText();

                String cpfValidar = cpf;

                cpfValidar = cpfValidar.replace(".", "");
                cpfValidar = cpfValidar.replace("-", "");

                if (ValidaCPF.isCPF(cpfValidar)) {
                    Hospede hospede = new Hospede();
                    hospede.setNome(nome);
                    hospede.setCpf(cpf);
                    hospede.setRg(rg);
                    hospede.setTelefone(telefone);
                    hospede.setDataNasc(dataNasc);

                    Cidade cidade = tfCidade.getSelectionModel().getSelectedItem();
                    hospede.setCidade(cidade);

                    Estado estado = tfEstado.getSelectionModel().getSelectedItem();
                    hospede.setEstado(estado);

                    int flag = 0;

                    for (Hospede hospedes: JDBCHospedeDAO.getInstance().list()) {
                        if(hospedes.getCpf().equals(hospede.getCpf())) {
                            flag = 1;
                        }
                    }

                    if(flag != 1) {
                        try {
                            JDBCHospedeDAO.getInstance().create(hospede);
                            mostrarMensagem("Hóspede cadastrado!");
                            voltar();
                        } catch (SQLException e) {
                            mostrarMensagem("Data inválida!");
                        } catch (Exception e) {
                            mostrarMensagem("Erro!");
                        }
                    } else {
                        mostrarMensagem("Este hóspede já existe!");
                    }
                }
            }
        } catch(NullPointerException e){
            mostrarMensagem("Faltam dados!");
        }
    }

    @FXML
    public void voltar() {
        trocarJanela("../../view/hospede/janelaHospede.fxml");
    }

    public void initialize() throws Exception {
        listaEstado = FXCollections.observableArrayList(JDBCEstadoDAO.getInstance().list());
        tfEstado.setItems(listaEstado);

        listaCidade = FXCollections.observableArrayList(JDBCCidadeDAO.getInstance().list());
        tfCidade.setItems(listaCidade);

        MaskFormatter formatter = new MaskFormatter(tfCpf);
        formatter.setMask(MaskFormatter.CPF);

        MaskFormatter formatter2 = new MaskFormatter(tfRg);
        formatter2.setMask(MaskFormatter.RG);

        MaskFormatter formatter3 = new MaskFormatter(tfTelefone);
        formatter3.setMask(MaskFormatter.TEL_9DIG);

        MaskFieldData maskFieldData = new MaskFieldData(tfDataNasc);
        maskFieldData.setMask(MaskFieldData.DATA);

        tfDataNasc.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke){
                if (ke.getCode().equals(KeyCode.BACK_SPACE)){
                    tfDataNasc.setText("");
                }
            }
        });
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
