package model.dao;

import javafx.collections.ObservableList;
import model.classes.Cidade;

public interface CidadeDAO {
    void create(Cidade cidade) throws Exception;
    ObservableList<Cidade> list() throws Exception;
    void delete(Cidade cidade) throws Exception;
    void update(Cidade cidade, Cidade c) throws Exception;
    Cidade search(int id) throws Exception;
}
