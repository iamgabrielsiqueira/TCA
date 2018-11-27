package model.classes;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Date;

public class Quarto {

    private int id;
    private SimpleIntegerProperty numero;
    private SimpleStringProperty descricao;
    private TipoQuarto tipoQuarto;
    private Date dataCriado;
    private Date dataAlterado;

    public Quarto() {
        numero = new SimpleIntegerProperty();
        descricao=  new SimpleStringProperty();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero.get();
    }

    public SimpleIntegerProperty numeroProperty() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero.set(numero);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public SimpleStringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public TipoQuarto getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(TipoQuarto tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
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
        return "" + this.numero.getValue() + " - " + this.tipoQuarto.getNome();
    }
}
