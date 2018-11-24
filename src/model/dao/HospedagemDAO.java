package model.dao;

import javafx.collections.ObservableList;
import model.classes.Hospedagem;

public interface HospedagemDAO {
    void create(Hospedagem hospedagem) throws Exception;
    ObservableList<Hospedagem> list() throws Exception;
    void delete(Hospedagem hospedagem) throws Exception;
    void update(Hospedagem hospedagem, Hospedagem h) throws Exception;
    Hospedagem search(int id) throws Exception;
}
