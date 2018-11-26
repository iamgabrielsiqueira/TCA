package model.dao;

import javafx.collections.ObservableList;
import model.classes.Hospedagem;
import model.classes.HospedagemServico;
import model.classes.Servico;

public interface HospedagemServicoDAO {
    void create(Hospedagem hospedagem, Servico servico) throws Exception;
    ObservableList<HospedagemServico> list(Hospedagem hospedagem) throws Exception;
    void delete(Hospedagem hospedagem, Servico servico) throws Exception;
}
