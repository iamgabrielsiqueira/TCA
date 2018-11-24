package controller.servico;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import model.classes.Servico;
import model.jdbc.JDBCServicoDAO;

public class ControllerRemoverServico {

    @FXML
    public Parent mainWindow;

    @FXML
    private Label lbNome;

    @FXML
    private Label lbValor;

    @FXML
    private Label lbDescricao;

    public void initialize() {
        Servico servico = JDBCServicoDAO.s1;

        lbNome.setText("Nome: " + servico.getNome());
        lbDescricao.setText("Descrição: " + servico.getDescricao());
        lbValor.setText("Valor: " + servico.getValor());
    }

}
