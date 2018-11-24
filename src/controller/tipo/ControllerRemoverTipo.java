package controller.tipo;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import model.jdbc.JDBCTipoQuartoDAO;
import model.classes.TipoQuarto;

public class ControllerRemoverTipo {

    @FXML
    public Parent mainWindow;

    @FXML
    private Label lbNome;

    @FXML
    private Label lbValor;

    @FXML
    private Label lbDescricao;

    public void initialize() {
        TipoQuarto tipoQuarto = JDBCTipoQuartoDAO.t1;

        lbNome.setText("Nome: " + tipoQuarto.getNome());
        lbDescricao.setText("Descrição: " + tipoQuarto.getDescricao());
        lbValor.setText("Valor: " + tipoQuarto.getValor());
    }

}
