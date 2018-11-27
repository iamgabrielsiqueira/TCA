package controller.hospedagem;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import model.classes.Hospedagem;
import model.jdbc.JDBCHospedagemDAO;

public class ControllerRemoverHospedagem {

    @FXML
    public Parent mainWindow;

    @FXML
    public Label lbHospede;

    @FXML
    public Label lbCheckIn;

    @FXML
    public Label lbCheckOut;

    public void initialize() {
        Hospedagem hospedagem = JDBCHospedagemDAO.h1;

        lbCheckIn.setText("Check In: " + hospedagem.getDataCheckIn());
        lbCheckOut.setText("Check Out: " + hospedagem.getDataCheckOut());
        lbHospede.setText("Hóspede: " + hospedagem.getHospede01().getNome());
    }
}
