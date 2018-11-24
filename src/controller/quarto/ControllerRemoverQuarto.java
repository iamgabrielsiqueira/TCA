package controller.quarto;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import model.classes.Quarto;
import model.jdbc.JDBCQuartoDAO;

public class ControllerRemoverQuarto {

    @FXML
    public Parent mainWindow;

    @FXML
    private Label lbNumero;

    @FXML
    private Label lbTipo;

    @FXML
    private Label lbDescricao;

    public void initialize() {
        Quarto quarto = JDBCQuartoDAO.q1;

        lbNumero.setText("Numero: " + quarto.getNumero());
        lbDescricao.setText("Descrição: " + quarto.getDescricao());
        lbTipo.setText("Tipo: " + quarto.getTipoQuarto().getNome());
    }

}
