package model;

import javafx.collections.ObservableList;

public interface HospedeDAO {
    void create(Hospede hospede) throws Exception;
    ObservableList<Hospede> list() throws Exception;
    void delete(Hospede hospede) throws Exception;
    void update(Hospede hospede, Hospede h) throws Exception;
}
