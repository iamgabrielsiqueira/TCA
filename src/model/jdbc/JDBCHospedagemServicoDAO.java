package model.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FabricaConexao;
import model.classes.Hospedagem;
import model.classes.HospedagemServico;
import model.classes.Servico;
import model.dao.HospedagemServicoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCHospedagemServicoDAO implements HospedagemServicoDAO {

    private static JDBCHospedagemServicoDAO instance;
    private ObservableList<HospedagemServico> conta;
    public static HospedagemServico hs1;

    private JDBCHospedagemServicoDAO(){
        conta = FXCollections.observableArrayList();
    }

    public static JDBCHospedagemServicoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCHospedagemServicoDAO();
        }
        return instance;
    }

    private HospedagemServico carregarServicoHospedagem(ResultSet resultSet) throws Exception {

        int hospedagem_id = resultSet.getInt("fk_hospedagem_id");
        int servico_id = resultSet.getInt("fk_servico_id");

        HospedagemServico hospedagemServico = new HospedagemServico();
        Hospedagem hospedagem = JDBCHospedagemDAO.getInstance().search(hospedagem_id);
        Servico servico = JDBCServicoDAO.getInstance().search(servico_id);
        hospedagemServico.setHospedagem(hospedagem);
        hospedagemServico.setServico(servico);

        return hospedagemServico;
    }

    @Override
    public void create(Hospedagem hospedagem, Servico servico) throws Exception {

        Connection connection = FabricaConexao.getConnection();

        String sql = "insert into tca_hospedagem_has_servico(fk_hospedagem_id, fk_servico_id) values(?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, hospedagem.getId());
        preparedStatement.setInt(2, servico.getId());

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
    }

    @Override
    public ObservableList<HospedagemServico> list(Hospedagem hospedagem) throws Exception {

        conta.clear();

        try {
            Connection connection = FabricaConexao.getConnection();
            String sql = "call listaServicosHospedagens(?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, hospedagem.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                HospedagemServico servico = carregarServicoHospedagem(resultSet);
                conta.add(servico);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return conta;
    }

    @Override
    public void delete(Hospedagem hospedagem, Servico servico) throws Exception {

        String sql = "delete from tca_hospedagem_has_servico where fk_hospedagem_id=? and fk_servico_id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, hospedagem.getId());
        statement.setInt(2, servico.getId());

        statement.execute();
        statement.close();
        c.close();

    }
}
