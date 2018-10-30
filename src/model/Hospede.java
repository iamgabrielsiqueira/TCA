package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class Hospede {

    private int id;
    private SimpleStringProperty nome;
    private SimpleStringProperty cpf;
    private SimpleStringProperty rg;
    private SimpleStringProperty telefone;
    private java.sql.Date dataNasc;
    private SimpleIntegerProperty idCidade;
    private Timestamp dataCriado;
    private Timestamp dataAlterado;

    public Hospede() {
        nome = new SimpleStringProperty();
        cpf=  new SimpleStringProperty();
        rg = new SimpleStringProperty();
        telefone = new SimpleStringProperty();
        idCidade = new SimpleIntegerProperty();
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

    public String getCpf() {
        return cpf.get();
    }

    public SimpleStringProperty cpfProperty() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf.set(cpf);
    }

    public String getRg() {
        return rg.get();
    }

    public SimpleStringProperty rgProperty() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg.set(rg);
    }

    public String getTelefone() {
        return telefone.get();
    }

    public SimpleStringProperty telefoneProperty() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public int getIdCidade() {
        return idCidade.get();
    }

    public SimpleIntegerProperty idCidadeProperty() {
        return idCidade;
    }

    public void setIdCidade(int idCidade) {
        this.idCidade.set(idCidade);
    }


    public java.sql.Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(java.sql.Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Timestamp getDataCriado() {
        return dataCriado;
    }

    public void setDataCriado(Timestamp dataCriado) {
        this.dataCriado = dataCriado;
    }

    public Timestamp getDataAlterado() {
        return dataAlterado;
    }

    public void setDataAlterado(Timestamp dataAlterado) {
        this.dataAlterado = dataAlterado;
    }
}
