package controller.hospede;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import model.classes.Hospede;
import model.jdbc.JDBCHospedeDAO;

public class ControllerRemoverHospede {

    @FXML
    public Parent mainWindow;

    @FXML
    public Label lbNome;

    @FXML
    public Label lbCPF;

    @FXML
    public Label lbDataNasc;

    public void initialize() {
        Hospede hospede = JDBCHospedeDAO.h1;

        lbNome.setText("Nome: " + hospede.getNome());
        lbCPF.setText("CPF: " + hospede.getCpf());
        lbDataNasc.setText("Data de Nascimento: " + hospede.getDataNasc());
    }

    protected void mensagem(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensagem!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
