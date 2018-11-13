package controller.quarto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import model.jdbc.JDBCQuartoDAO;
import model.classes.Quarto;

public class ControllerRedirecionamentoQuarto {

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
