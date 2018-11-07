package model;

import javafx.collections.ObservableList;

public interface EstadoDAO {
    void create(Estado estado) throws Exception;
    ObservableList<Estado> list() throws Exception;
    void delete(Estado estado) throws Exception;
    void update(Estado estado, Estado e) throws Exception;
    Estado search(int id) throws Exception;
}
