package controller.hospede;

import controller.ValidaCPF;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jeanderson.br.util.MaskFormatter;
import model.classes.Cidade;
import model.jdbc.JDBCCidadeDAO;
import model.classes.Estado;
import model.jdbc.JDBCEstadoDAO;
import model.classes.Hospede;
import model.jdbc.JDBCHospedeDAO;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class ControllerAlterarHospede {

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
    private DatePicker tfDataNasc;

    @FXML
    private ComboBox<Estado> tfEstado;

    @FXML
    private ComboBox<Cidade> tfCidade;

    private ObservableList<Estado> listaEstado;

    private ObservableList<Cidade> listaCidade;

    private ObservableList<Cidade> listaCidadeFiltro;

    private Hospede hospede1;

    @FXML
    public void carregarCidade() {
        Estado estadoSelecionado = tfEstado.getSelectionModel().getSelectedItem();

        tfCidade.getItems().clear();

        try {
            listaCidadeFiltro = JDBCCidadeDAO.getInstance().listaPorEstado(estadoSelecionado);
            tfCidade.setItems(listaCidadeFiltro);
        } catch (Exception e) {
            mensagem(Alert.AlertType.ERROR, "Erro!");
        }

    }

    @FXML
    public void salvarHospede() {
        try {
            if(tfNome.getText().isEmpty() || tfCpf.getText().isEmpty()
                    || tfRg.getText().isEmpty() || tfTelefone.getText().isEmpty()
                    || tfDataNasc.getValue().toString().isEmpty()) {
                mensagem(Alert.AlertType.ERROR, "Dados faltando!");
            } else {
                String nome = tfNome.getText();
                String cpf = tfCpf.getText();
                String rg = tfRg.getText();
                String telefone = tfTelefone.getText();
                Date dataNasc = Date.valueOf(tfDataNasc.getValue());

                String cpfValidar = cpf;

                cpfValidar = cpfValidar.replace( "." , "");
                cpfValidar = cpfValidar.replace( "-" , "");

                if(ValidaCPF.isCPF(cpfValidar)) {
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

                    try {
                        JDBCHospedeDAO.getInstance().update(hospede1, hospede);
                        mensagem(Alert.AlertType.INFORMATION, "Hóspede alterado!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    mensagem(Alert.AlertType.ERROR, "CPF inválido!");
                }
            }
        } catch (NullPointerException e) {
            mensagem(Alert.AlertType.ERROR, "Erro!");
        }
    }

    @FXML
    public void voltar() {
        trocarJanela("../../view/hospede/janelaHospede.fxml");
    }

    public void initialize() throws Exception {

        this.hospede1 = JDBCHospedeDAO.h1;

        tfNome.setText(hospede1.getNome());
        tfCpf.setText(hospede1.getCpf());
        tfTelefone.setText(hospede1.getTelefone());
        tfRg.setText(hospede1.getRg());

        Date dataNasc = hospede1.getDataNasc();
        LocalDate d1 = Instant.ofEpochMilli(dataNasc.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        tfDataNasc.setValue(d1);

        tfCidade.getSelectionModel().select(hospede1.getCidade());
        tfEstado.getSelectionModel().select(hospede1.getEstado());

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
