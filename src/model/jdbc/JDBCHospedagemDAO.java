package model.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FabricaConexao;
import model.classes.*;
import model.dao.HospedagemDAO;
import java.sql.*;

public class JDBCHospedagemDAO implements HospedagemDAO {

    private static JDBCHospedagemDAO instance;
    private ObservableList<Hospedagem> list;
    public static Hospedagem h1;

    private JDBCHospedagemDAO(){
        list = FXCollections.observableArrayList();
    }

    public static JDBCHospedagemDAO getInstance() {
        if(instance == null) {
            instance = new JDBCHospedagemDAO();
        }
        return instance;
    }

    @Override
    public void create(Hospedagem hospedagem) throws Exception {

        Connection connection = FabricaConexao.getConnection();

        String sql = "insert into tca_hospedagem(dataCheckIn, dataCheckOut, statusCheckIn, statusCheckOut, " +
                "valor, status, fk_hospede1_id, fk_hospede2_id, fk_hospede3_id, fk_quarto_id, data_criado) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Date dataAtual = new Date(System.currentTimeMillis());

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setTimestamp(1, hospedagem.getDataCheckIn());
        preparedStatement.setTimestamp(2, hospedagem.getDataCheckOut());
        preparedStatement.setBoolean(3, hospedagem.isStatusCheckIn());
        preparedStatement.setBoolean(4, hospedagem.isStatusCheckOut());
        preparedStatement.setDouble(5, hospedagem.getValor());
        preparedStatement.setBoolean(6, hospedagem.isStatus());
        preparedStatement.setInt(7, hospedagem.getHospede01().getId());
        preparedStatement.setInt(8, hospedagem.getHospede02().getId());
        preparedStatement.setInt(9, hospedagem.getHospede03().getId());
        preparedStatement.setInt(10, hospedagem.getQuarto().getId());
        preparedStatement.setDate(11, dataAtual);

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    private Hospedagem carregarHospedagem(ResultSet resultSet) throws Exception {

        int id = resultSet.getInt("id");
        Timestamp dataCheckIn = resultSet.getTimestamp("dataCheckIn");
        Timestamp dataCheckOut = resultSet.getTimestamp("dataCheckOut");
        Boolean statusCheckIn = resultSet.getBoolean("statusCheckIn");
        Boolean statusCheckOut = resultSet.getBoolean("statusCheckOut");
        Double valor = resultSet.getDouble("valor");
        Boolean status = resultSet.getBoolean("status");
        int idHospede01 = resultSet.getInt("fk_hospede1_id");
        int idHospede02 = resultSet.getInt("fk_hospede2_id");
        int idHospede03 = resultSet.getInt("fk_hospede3_id");
        int idQuarto = resultSet.getInt("fk_quarto_id");
        Date dataCriado = resultSet.getDate("data_criado");
        Date dataAlterado = resultSet.getDate("data_alterado");

        Hospedagem hospedagem = new Hospedagem();
        hospedagem.setId(id);
        hospedagem.setDataCheckIn(dataCheckIn);
        hospedagem.setDataCheckOut(dataCheckOut);
        hospedagem.setStatusCheckIn(statusCheckIn);
        hospedagem.setStatusCheckOut(statusCheckOut);
        hospedagem.setValor(valor);
        hospedagem.setStatus(status);
        Hospede hospede01 = JDBCHospedeDAO.getInstance().search(idHospede01);
        hospedagem.setHospede01(hospede01);
        Hospede hospede02 = JDBCHospedeDAO.getInstance().search(idHospede02);
        hospedagem.setHospede02(hospede02);
        Hospede hospede03 = JDBCHospedeDAO.getInstance().search(idHospede03);
        hospedagem.setHospede02(hospede03);
        Quarto quarto = JDBCQuartoDAO.getInstance().search(idQuarto);
        hospedagem.setQuarto(quarto);
        hospedagem.setDataCriado(dataCriado);
        hospedagem.setDataAlterado(dataAlterado);

        return hospedagem;
    }


    @Override
    public ObservableList<Hospedagem> list() throws Exception {

        list.clear();

        try {
            Connection connection = FabricaConexao.getConnection();
            String sql = "select * from tca_hospedagem";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Hospedagem hospedagem = carregarHospedagem(resultSet);
                list.add(hospedagem);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return list;

    }

    @Override
    public void delete(Hospedagem hospedagem) throws Exception {

        String sql = "delete from tca_hospedagem where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, hospedagem.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public void update(Hospedagem hospedagem, Hospedagem h) throws Exception {

        Date dataAtual = new Date(System.currentTimeMillis());

        String sql = "update tca_hospedagem set dataCheckIn=?, dataCheckOut=?, statusCheckIn=?, " +
                "statusCheckOut=?, valor=?, status=?, fk_hospede1_id=?, fk_hospede2_id=?, fk_hospede3_id=?, " +
                "fk_quarto_id=?, data_alterado=? where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setTimestamp(1, h.getDataCheckIn());
        statement.setTimestamp(2, h.getDataCheckOut());
        statement.setBoolean(3, h.isStatusCheckIn());
        statement.setBoolean(4, h.isStatusCheckOut());
        statement.setDouble(5, h.getValor());
        statement.setBoolean(6, h.isStatus());
        statement.setInt(7, h.getHospede01().getId());
        statement.setInt(8, h.getHospede02().getId());
        statement.setInt(9, h.getHospede03().getId());
        statement.setInt(10, h.getQuarto().getId());
        statement.setDate(11, dataAtual);
        statement.setInt(12, hospedagem.getId());

        statement.execute();
        statement.close();
        c.close();
    }

    @Override
    public Hospedagem search(int id) throws Exception {
        Connection connection = FabricaConexao.getConnection();
        String sql = "select * from tca_hospedagem where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        Hospedagem hospedagem = carregarHospedagem(resultSet);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return hospedagem;
    }

}