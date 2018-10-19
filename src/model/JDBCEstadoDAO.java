package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCEstadoDAO implements EstadoDAO {

    private static JDBCEstadoDAO instance;
    private ObservableList<Estado> list;

    private JDBCEstadoDAO(){
        list = FXCollections.observableArrayList();
    }

    public static JDBCEstadoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCEstadoDAO();
        }
        return instance;
    }


    @Override
    public void create(Estado estado) throws Exception {
        Connection connection = FabricaConexao.getConnection();

        String sql = "insert into tca_hospede(nome, cpf, rg, telefone, fk_cidade) values(?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, hospede.getNome());
        preparedStatement.setString(2, hospede.getCpf());
        preparedStatement.setString(3, hospede.getRg());
        preparedStatement.setString(4, hospede.getTelefone());
        preparedStatement.setInt(5, hospede.getIdCidade());

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    private Hospede carregarHospede(ResultSet resultSet) throws Exception {

        int id = resultSet.getInt("id");
        String nome = resultSet.getString("nome");
        String cpf = resultSet.getString("cpf");
        String rg = resultSet.getString("rg");
        String telefone = resultSet.getString("telefone");
        int cidadeId = resultSet.getInt("fk_cidade");

        Hospede hospede = new Hospede();
        hospede.setId(id);
        hospede.setNome(nome);
        hospede.setCpf(cpf);
        hospede.setRg(rg);
        hospede.setTelefone(telefone);
        hospede.setIdCidade(cidadeId);

        return hospede;
    }


    @Override
    public ObservableList<Estado> list() throws Exception {

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
    public void delete(Estado estado) throws Exception {

        String sql = "delete from tca_hospede where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, hospede.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public void update(Estado estado, Estado e) throws Exception {
        String sql = "update tca_hospede set nome=?, cpf=?, rg=?, telefone=?, fk_cidade=? where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setString(1, h.getNome());
        statement.setString(2, h.getCpf());
        statement.setString(3, h.getRg());
        statement.setString(4, h.getTelefone());
        statement.setInt(5, h.getIdCidade());
        statement.setInt(6, hospede.getId());

        statement.execute();
        statement.close();
        c.close();

    }

}
