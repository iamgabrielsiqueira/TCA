package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Date;

public class TipoQuarto {
    private int id;
    private SimpleStringProperty nome;
    private SimpleDoubleProperty valor;
    private SimpleStringProperty descricao;
    private Date dataCriado;
    private Date dataAlterado;

    public TipoQuarto() {
        nome = new SimpleStringProperty();
        valor = new SimpleDoubleProperty();
        descricao = new SimpleStringProperty();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome.get();
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
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

    public String getDescricao() {
        return descricao.get();
    }

    public SimpleStringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
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
        return this.nome.getValue();
    }
}
