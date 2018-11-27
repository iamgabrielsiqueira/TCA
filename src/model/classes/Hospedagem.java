package model.classes;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Date;

public class Hospedagem {
    private int id;
    private SimpleStringProperty dataCheckIn;
    private SimpleStringProperty dataCheckOut;
    private SimpleBooleanProperty statusCheckIn;
    private SimpleBooleanProperty statusCheckOut;
    private SimpleDoubleProperty valor;
    private SimpleBooleanProperty status;
    private Hospede hospede01;
    private Hospede hospede02;
    private Hospede hospede03;
    private Quarto quarto;
    private Date dataCriado;
    private Date dataAlterado;

    public Hospedagem() {
        dataCheckIn = new SimpleStringProperty();
        dataCheckOut = new SimpleStringProperty();
        valor = new SimpleDoubleProperty();
        status = new SimpleBooleanProperty();
        statusCheckIn = new SimpleBooleanProperty();
        statusCheckOut = new SimpleBooleanProperty();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor.get();
    }

    public SimpleDoubleProperty valorProperty() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor.set(valor);
    }

    public boolean isStatus() {
        return status.get();
    }

    public SimpleBooleanProperty statusProperty() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status.set(status);
    }

    public Hospede getHospede01() {
        return hospede01;
    }

    public void setHospede01(Hospede hospede01) {
        this.hospede01 = hospede01;
    }

    public Hospede getHospede02() {
        return hospede02;
    }

    public void setHospede02(Hospede hospede02) {
        this.hospede02 = hospede02;
    }

    public Hospede getHospede03() {
        return hospede03;
    }

    public void setHospede03(Hospede hospede03) {
        this.hospede03 = hospede03;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public Date getDataCriado() {
        return dataCriado;
    }

    public void setDataCriado(Date dataCriado) {
        this.dataCriado = dataCriado;
    }

    public Date getDataAlterado() {
        return dataAlterado;
    }

    public void setDataAlterado(Date dataAlterado) {
        this.dataAlterado = dataAlterado;
    }

    @Override
    public String toString() {
        return "Hóspede: " + this.hospede01.getNome();
    }

    public boolean isStatusCheckIn() {
        return statusCheckIn.get();
    }

    public SimpleBooleanProperty statusCheckInProperty() {
        return statusCheckIn;
    }

    public void setStatusCheckIn(boolean statusCheckIn) {
        this.statusCheckIn.set(statusCheckIn);
    }

    public boolean isStatusCheckOut() {
        return statusCheckOut.get();
    }

    public SimpleBooleanProperty statusCheckOutProperty() {
        return statusCheckOut;
    }

    public void setStatusCheckOut(boolean statusCheckOut) {
        this.statusCheckOut.set(statusCheckOut);
    }


    public String getDataCheckIn() {
        return dataCheckIn.get();
    }

    public SimpleStringProperty dataCheckInProperty() {
        return dataCheckIn;
    }

    public void setDataCheckIn(String dataCheckIn) {
        this.dataCheckIn.set(dataCheckIn);
    }

    public String getDataCheckOut() {
        return dataCheckOut.get();
    }

    public SimpleStringProperty dataCheckOutProperty() {
        return dataCheckOut;
    }

    public void setDataCheckOut(String dataCheckOut) {
        this.dataCheckOut.set(dataCheckOut);
    }
}
