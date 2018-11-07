package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import model.Hospede;
import model.JDBCHospedeDAO;

public class ControllerJanelaRedirecionamento {

    @FXML
    private ComboBox<Hospede> cbHospedes;

    private ObservableList<Hospede> listaHospedes;

    public Hospede processResult() {
        Hospede h = cbHospedes.getValue();
        return h;
    }

    public void initialize() throws Exception {
        listaHospedes = FXCollections.observableArrayList(JDBCHospedeDAO.getInstance().list());
        cbHospedes.setItems(listaHospedes);
    }

}
