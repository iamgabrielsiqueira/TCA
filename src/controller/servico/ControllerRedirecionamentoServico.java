package controller.servico;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import model.jdbc.JDBCServicoDAO;
import model.classes.Servico;

public class ControllerRedirecionamentoServico {

    @FXML
    private ComboBox<Servico> cbServico;

    private ObservableList<Servico> listaServico;

    public Servico processResult() {
        Servico s = cbServico.getValue();
        return s;
    }

    public void initialize() throws Exception {
        listaServico = FXCollections.observableArrayList(JDBCServicoDAO.getInstance().list());
        cbServico.setItems(listaServico);
    }

}
