package model.dao;

import javafx.collections.ObservableList;
import model.classes.Quarto;

public interface QuartoDAO {
    void create(Quarto quarto) throws Exception;
    ObservableList<Quarto> list() throws Exception;
    void delete(Quarto quarto) throws Exception;
    void update(Quarto quarto, Quarto q) throws Exception;
}
