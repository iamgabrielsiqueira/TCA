package controller.tipo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.JDBCTipoQuartoDAO;
import model.TipoQuarto;

public class ControllerRedirecionamentoTipo {

    @FXML
    private ComboBox<TipoQuarto> cbTipo;

    private ObservableList<TipoQuarto> listaTipo;

    public TipoQuarto processResult() {
        TipoQuarto t = cbTipo.getValue();
        return t;
    }

    public void initialize() throws Exception {
        listaTipo = FXCollections.observableArrayList(JDBCTipoQuartoDAO.getInstance().list());
        cbTipo.setItems(listaTipo);
    }

}
