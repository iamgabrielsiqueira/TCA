package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import model.JDBCQuartoDAO;
import model.Quarto;

public class ControllerJanelaRedirecionamentoQuarto {

    @FXML
    private ComboBox<Quarto> cbQuarto;

    private ObservableList<Quarto> listaQuarto;

    public Quarto processResult() {
        Quarto q = cbQuarto.getValue();
        return q;
    }

    public void initialize() throws Exception {
        listaQuarto = FXCollections.observableArrayList(JDBCQuartoDAO.getInstance().list());
        cbQuarto.setItems(listaQuarto);
    }

}
