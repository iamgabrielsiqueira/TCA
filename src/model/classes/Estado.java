package model.classes;

import javafx.beans.property.SimpleStringProperty;

public class Estado {

    private int id;
    private SimpleStringProperty nome;

    public Estado() {
        nome = new SimpleStringProperty();
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

    @Override
    public String toString() {
        return this.getNome();
    }
}
