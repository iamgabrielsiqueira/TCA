package model;

import javafx.collections.ObservableList;

public interface TipoQuartoDAO {
    void create(TipoQuarto tipoQuarto) throws Exception;
    ObservableList<TipoQuarto> list() throws Exception;
    void delete(TipoQuarto tipoQuarto) throws Exception;
    void update(TipoQuarto tipoQuarto, TipoQuarto t) throws Exception;
}
