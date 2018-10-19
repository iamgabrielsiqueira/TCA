package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cidade {

    private int id;
    private SimpleStringProperty nome;
    private SimpleIntegerProperty fk_estado;

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

    public int getFk_estado() {
        return fk_estado.get();
    }

    public SimpleIntegerProperty fk_estadoProperty() {
        return fk_estado;
    }

    public void setFk_estado(int fk_estado) {
        this.fk_estado.set(fk_estado);
    }

}
