package model.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dao.EstadoDAO;
import model.FabricaConexao;
import model.classes.Estado;
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

        String sql = "insert into tca_estado(nome) values(?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, estado.getNome());

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    private Estado carregarEstado(ResultSet resultSet) throws Exception {

        int id = resultSet.getInt("id");
        String nome = resultSet.getString("nome");

        Estado estado = new Estado();
        estado.setId(id);
        estado.setNome(nome);

        return estado;
    }


    @Override
    public ObservableList<Estado> list() throws Exception {

        list.clear();

        try {
            Connection connection = FabricaConexao.getConnection();
            String sql = "select * from tca_estado";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Estado estado = carregarEstado(resultSet);
                list.add(estado);
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

        String sql = "delete from tca_estado where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, estado.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public void update(Estado estado, Estado e) throws Exception {
        String sql = "update tca_estado set nome=? where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setString(1, e.getNome());
        statement.setInt(2, estado.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public Estado search(int id) throws Exception {
        Connection connection = FabricaConexao.getConnection();
        String sql = "select * from tca_estado where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        Estado estado = carregarEstado(resultSet);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return estado;
    }

}
