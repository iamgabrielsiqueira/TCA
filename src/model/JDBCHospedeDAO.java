package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class JDBCHospedeDAO implements HospedeDAO {

    private static JDBCHospedeDAO instance;
    private ObservableList<Hospede> list;

    private JDBCHospedeDAO(){
        list = FXCollections.observableArrayList();
    }

    public static JDBCHospedeDAO getInstance() {
        if(instance == null) {
            instance = new JDBCHospedeDAO();
        }
        return instance;
    }


    @Override
    public void create(Hospede hospede) throws Exception {

        Connection connection = FabricaConexao.getConnection();

        String sql = "insert into tca_hospede(nome, cpf, rg, data_nasc, telefone, " +
                "fk_cidade, data_criado) values(?, ?, ?, ?, ?, ?, ?)";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, hospede.getNome());
        preparedStatement.setString(2, hospede.getCpf());
        preparedStatement.setString(3, hospede.getRg());
        preparedStatement.setDate(4, hospede.getDataNasc());
        preparedStatement.setString(5, hospede.getTelefone());
        preparedStatement.setInt(6, hospede.getIdCidade());
        preparedStatement.setTimestamp(7, timestamp);

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    private Hospede carregarHospede(ResultSet resultSet) throws Exception {

        int id = resultSet.getInt("id");
        String nome = resultSet.getString("nome");
        String cpf = resultSet.getString("cpf");
        String rg = resultSet.getString("rg");
        Date dataNasc = resultSet.getDate("data_nasc");
        String telefone = resultSet.getString("telefone");
        int cidadeId = resultSet.getInt("fk_cidade");
        Timestamp dataCriado = resultSet.getTimestamp("data_criado");
        Timestamp dataAlterado = resultSet.getTimestamp("data_alterado");

        Hospede hospede = new Hospede();
        hospede.setId(id);
        hospede.setNome(nome);
        hospede.setCpf(cpf);
        hospede.setRg(rg);
        hospede.setDataNasc(dataNasc);
        hospede.setTelefone(telefone);
        hospede.setIdCidade(cidadeId);
        hospede.setDataCriado(dataCriado);
        hospede.setDataAlterado(dataAlterado);

        return hospede;
    }


    @Override
    public ObservableList<Hospede> list() throws Exception {

        list.clear();

        try {
            Connection connection = FabricaConexao.getConnection();
            String sql = "select * from tca_hospede";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Hospede hospede = carregarHospede(resultSet);
                list.add(hospede);
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
    public void delete(Hospede hospede) throws Exception {

        String sql = "delete from tca_hospede where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, hospede.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public void update(Hospede hospede, Hospede h) throws Exception {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String sql = "update tca_hospede set nome=?, cpf=?, rg=?, " +
                "data_nasc=?, telefone=?, fk_cidade=?, data_alterado=? where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setString(1, h.getNome());
        statement.setString(2, h.getCpf());
        statement.setString(3, h.getRg());
        statement.setDate(4, h.getDataNasc());
        statement.setString(5, h.getTelefone());
        statement.setInt(6, h.getIdCidade());
        statement.setTimestamp(7, timestamp);
        statement.setInt(8, hospede.getId());

        statement.execute();
        statement.close();
        c.close();

    }

}
