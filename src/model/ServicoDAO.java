package model;

import javafx.collections.ObservableList;

public interface ServicoDAO {
    void create(Servico servico) throws Exception;
    ObservableList<Servico> list() throws Exception;
    void delete(Servico servico) throws Exception;
    void update(Servico servico, Servico s) throws Exception;
    Servico search(int id) throws Exception;
}
