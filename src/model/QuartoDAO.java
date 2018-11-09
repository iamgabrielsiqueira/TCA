package model;

import javafx.collections.ObservableList;

public interface QuartoDAO {
    void create(Quarto quarto) throws Exception;
    ObservableList<Quarto> list() throws Exception;
    void delete(Quarto quarto) throws Exception;
    void update(Quarto quarto, Quarto q) throws Exception;
}
