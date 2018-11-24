package model.classes;

import javafx.beans.property.SimpleStringProperty;
import java.sql.Date;

public class Hospede {
    private int id;
    private SimpleStringProperty nome;
    private SimpleStringProperty cpf;
    private SimpleStringProperty rg;
    private SimpleStringProperty telefone;
    private SimpleStringProperty dataNasc;
    private Cidade cidade;
    private Estado estado;
    private Date dataCriado;
    private Date dataAlterado;

    public Hospede() {
        nome = new SimpleStringProperty();
        cpf=  new SimpleStringProperty();
        rg = new SimpleStringProperty();
        telefone = new SimpleStringProperty();
        dataNasc = new SimpleStringProperty();
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
        return this.nome.getValue() + " - [" + this.cpf.getValue() + "]";
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getDataNasc() {
        return dataNasc.get();
    }

    public SimpleStringProperty dataNascProperty() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc.set(dataNasc);
    }
}
