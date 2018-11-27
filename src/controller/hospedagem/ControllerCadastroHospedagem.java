package controller.hospedagem;

import controller.MaskFieldData;
import controller.Mensagem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.classes.*;
import model.jdbc.JDBCHospedagemDAO;
import model.jdbc.JDBCHospedeDAO;
import model.jdbc.JDBCQuartoDAO;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Optional;

public class ControllerCadastroHospedagem {

    @FXML
    public Parent mainWindow;

    @FXML
    public TextField tfCheckIn;

    @FXML
    public TextField tfCheckOut;

    @FXML
    public ComboBox<Hospede> tfHospede;

    @FXML
    public ComboBox<Hospede> tfHospede2;

    @FXML
    public ComboBox<Hospede> tfHospede3;

    @FXML
    public ComboBox<Quarto> tfQuarto;

    @FXML
    private CheckBox tbCheckIn;

    private ObservableList<Hospede> listaHospede1;

    private ObservableList<Hospede> listaHospede2;

    private ObservableList<Hospede> listaHospede3;

    private ObservableList<Quarto> listaQuarto;

    private ObservableList<Quarto> listaFiltrada;

    private ObservableList<Quarto> listaFiltrada2;

    private ObservableList<Quarto> listaRemover;

    @FXML
    public void voltar() {
        trocarJanela("../../view/hospedagem/janelaHospedagem.fxml");
    }

    @FXML
    public void carregarHospedes() {
        trocarJanela("../../view/hospede/janelaHospede.fxml");
    }

    @FXML
    public void carregarTiposQuartos() {
        trocarJanela("../../view/tipo/janelaTipoQuarto.fxml");
    }

    @FXML
    public void carregarQuartos() {
        trocarJanela("../../view/quarto/janelaQuarto.fxml");
    }

    @FXML
    public void carregarServicos() {
        trocarJanela("../../view/servico/janelaServico.fxml");
    }

    @FXML
    public void carregarHospedagens() {
        trocarJanela("../../view/hospedagem/janelaHospedagem.fxml");
    }

    @FXML
    public void setData1() throws Exception {

        if(tfCheckIn.getText().isEmpty() || tfCheckOut.getText().isEmpty()) {
            //carregarLista();
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            java.sql.Date data1 = new java.sql.Date(format.parse(tfCheckIn.getText()).getTime());
            java.sql.Date data2 = new java.sql.Date(format.parse(tfCheckOut.getText()).getTime());
            filtraLista(data1, data2);
        }
    }

    public void filtraLista(Date data1, Date data2) throws Exception {

        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

        listaFiltrada = FXCollections.observableArrayList();
        listaFiltrada2 = FXCollections.observableArrayList();
        listaRemover = FXCollections.observableArrayList();

        listaFiltrada = JDBCQuartoDAO.getInstance().list();

        for (Hospedagem hospedagem : JDBCHospedagemDAO.getInstance().list()) {
            try {
                String data3 = myFormat.format(fromUser.parse(hospedagem.getDataCheckIn()));
                Date checkIn = Date.valueOf(data3);
                String data4 = myFormat.format(fromUser.parse(hospedagem.getDataCheckOut()));
                Date checkOut = Date.valueOf(data4);

                for (Quarto q : JDBCQuartoDAO.getInstance().list()) {
                    if(hospedagem.getQuarto().getId() == q.getId()) {

                        // Se a data digitada for depois ao do check in
                        if(data1.after(checkIn) && data2.before(checkOut) && data1.before(checkOut) && data2.after(checkIn)) {

                            listaRemover.add(hospedagem.getQuarto());
                        } else if (data1.equals(checkIn) && data2.equals(checkOut) ) {

                            listaRemover.add(hospedagem.getQuarto());
                        } else if (data1.before(checkIn) && data2.before(checkOut) && data1.before(checkOut) && data2.after(checkIn)) {

                            listaRemover.add(hospedagem.getQuarto());
                        } else if (data1.after(checkIn) && data2.after(checkOut) && data1.before(checkOut) && data2.after(checkIn)) {

                            listaRemover.add(hospedagem.getQuarto());
                        } else {

                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        int diferente = 0;

        for (Quarto quarto : JDBCQuartoDAO.getInstance().list()) {
            for (Quarto quarto1 : listaRemover) {
                if(quarto.getId() == quarto1.getId()) {
                    diferente = 1;
                }
            }

            if(diferente == 0) {
                listaFiltrada2.add(quarto);
            }

            diferente = 0;
        }

        tfQuarto.getItems().clear();
        tfQuarto.setItems(listaFiltrada2);

    }

    @FXML
    public void salvarHospedagem() throws Exception {

        try {
            if(tfCheckIn.getText().isEmpty() || tfCheckOut.getText().isEmpty() ||
                    tfHospede.getSelectionModel().isEmpty() || tfQuarto.getSelectionModel().isEmpty()) {
                mostrarMensagem("Faltam dados!");
            } else {
                String checkIn = tfCheckIn.getText();
                String checkOut = tfCheckOut.getText();
                Hospede hospede = tfHospede.getSelectionModel().getSelectedItem();
                Hospede hospede2 = tfHospede2.getSelectionModel().getSelectedItem();
                Hospede hospede3 = tfHospede3.getSelectionModel().getSelectedItem();
                Quarto quarto = tfQuarto.getSelectionModel().getSelectedItem();

                Hospedagem hospedagem = new Hospedagem();
                hospedagem.setDataCheckIn(checkIn);
                hospedagem.setDataCheckOut(checkOut);

                if(tbCheckIn.isSelected()) {
                    hospedagem.setStatusCheckIn(true);
                } else {
                    hospedagem.setStatusCheckIn(false);
                }

                hospedagem.setStatusCheckOut(false);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                java.sql.Date date1 = new java.sql.Date(format.parse(checkIn).getTime());
                java.sql.Date date2 = new java.sql.Date(format.parse(checkOut).getTime());

                int dias = dataDiff(date1, date2);

                hospedagem.setValor(dias * (quarto.getTipoQuarto().getValor()));
                hospedagem.setStatus(true);
                hospedagem.setHospede01(hospede);
                hospedagem.setHospede02(hospede2);
                hospedagem.setHospede03(hospede3);
                hospedagem.setQuarto(quarto);

                try {
                    JDBCHospedagemDAO.getInstance().create(hospedagem);
                    mostrarMensagem("Hospedagem cadastrada!");
                    voltar();
                } catch (SQLException e) {
                    mostrarMensagem("Erro!");
                }
            }
        } catch (NullPointerException e) {
            mostrarMensagem("Faltam dados!");
        }

    }

    public void initialize() throws Exception {

        MaskFieldData maskFieldData = new MaskFieldData(tfCheckIn);
        maskFieldData.setMask(MaskFieldData.DATA);

        MaskFieldData maskFieldData2 = new MaskFieldData(tfCheckOut);
        maskFieldData2.setMask(MaskFieldData.DATA);

        Hospede hospede = new Hospede();
        hospede.setNome("Nenhum");
        hospede.setId(0);

        listaHospede1 = FXCollections.observableArrayList(JDBCHospedeDAO.getInstance().list());
        tfHospede.setItems(listaHospede1);

        listaHospede2 = FXCollections.observableArrayList(JDBCHospedeDAO.getInstance().list());
        listaHospede2.add(0, hospede);
        tfHospede2.setItems(listaHospede2);

        listaHospede3 = FXCollections.observableArrayList(JDBCHospedeDAO.getInstance().list());
        listaHospede3.add(0, hospede);
        tfHospede3.setItems(listaHospede3);

        listaQuarto = FXCollections.observableArrayList(JDBCQuartoDAO.getInstance().list());
        tfQuarto.setItems(listaQuarto);

        tfHospede2.getSelectionModel().select(0);
        tfHospede3.getSelectionModel().select(0);

        tfCheckIn.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke){
                if (ke.getCode().equals(KeyCode.BACK_SPACE)){
                    tfCheckIn.setText("");
                }
            }
        });

        tfCheckOut.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent ke){
                if (ke.getCode().equals(KeyCode.BACK_SPACE)){
                    tfCheckOut.setText("");
                }
            }
        });
    }

    public void trocarJanela(String address){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(address));

                try {
                    Parent layoutWindow = loader.load();

                    Stage stage=(Stage)mainWindow.getScene().getWindow();

                    stage.setScene(new Scene(layoutWindow,800, 600));
                    stage.setResizable(false);

                }catch (IOException e){
                    mostrarMensagem("Erro!");
                }
            }
        });

    }

    protected void mostrarMensagem(String mensagem) {

        Mensagem.mensagem = mensagem;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Mensagem");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/janelaMensagem.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("../../estilo.css").toExternalForm());
            dialog.getDialogPane().getStyleClass().add("myDialog");

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK) {
                //System.out.println("1: " + mensagem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método para comparar as das e retornar o numero de dias de diferença entre elas
     *
     * Compare two date and return the difference between them in days.
     *
     * @param dataLow The lowest date
     * @param dataHigh The highest date
     *
     * @return int
     */
    public static int dataDiff(java.util.Date dataLow, java.util.Date dataHigh){
        GregorianCalendar startTime = new GregorianCalendar();
        GregorianCalendar endTime = new GregorianCalendar();
        GregorianCalendar curTime = new GregorianCalendar();
        GregorianCalendar baseTime = new GregorianCalendar();
        startTime.setTime(dataLow);
        endTime.setTime(dataHigh);
        int dif_multiplier = 1;
        // Verifica a ordem de inicio das datas
        if( dataLow.compareTo( dataHigh ) < 0 ){
            baseTime.setTime(dataHigh);
            curTime.setTime(dataLow);
            dif_multiplier = 1;
        }else{
            baseTime.setTime(dataLow);
            curTime.setTime(dataHigh);
            dif_multiplier = -1;
        }
        int result_years = 0;
        int result_months = 0;
        int result_days = 0;
        // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando
        // no total de dias. Ja leva em consideracao ano bissesto
        while( curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR) ||
                curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)  )
        {
            int max_day = curTime.getActualMaximum( GregorianCalendar.DAY_OF_MONTH );
            result_months += max_day;
            curTime.add(GregorianCalendar.MONTH, 1);
        }
        // Marca que é um saldo negativo ou positivo
        result_months = result_months*dif_multiplier;
        // Retirna a diferenca de dias do total dos meses
        result_days += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));
        return result_years+result_months+result_days;
    }
}
