package model.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dao.CidadeDAO;
import model.FabricaConexao;
import model.classes.Cidade;
import model.classes.Estado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCCidadeDAO implements CidadeDAO {

    private static JDBCCidadeDAO instance;
    private ObservableList<Cidade> list;
    private ObservableList<Cidade> list2;

    private JDBCCidadeDAO(){
        list = FXCollections.observableArrayList();
        list2 = FXCollections.observableArrayList();
    }

    public static JDBCCidadeDAO getInstance() {
        if(instance == null) {
            instance = new JDBCCidadeDAO();
        }
        return instance;
    }


    @Override
    public void create(Cidade cidade) throws Exception {
        Connection connection = FabricaConexao.getConnection();

        String sql = "insert into tca_cidade(nome, fk_estado) values(?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, cidade.getNome());
        preparedStatement.setInt(2, cidade.getFk_estado());

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    private Cidade carregarCidade(ResultSet resultSet) throws Exception {

        int id = resultSet.getInt("id");
        String nome = resultSet.getString("nome");
        int fk_estado = resultSet.getInt("fk_estado");

        Cidade cidade = new Cidade();
        cidade.setId(id);
        cidade.setNome(nome);
        cidade.setFk_estado(fk_estado);

        return cidade;
    }


    @Override
    public ObservableList<Cidade> list() throws Exception {

        list.clear();

        try {
            Connection connection = FabricaConexao.getConnection();
            String sql = "select * from tca_cidade";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Cidade cidade = carregarCidade(resultSet);
                list.add(cidade);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return list;

    }

    public ObservableList<Cidade> listaPorEstado(Estado estado) throws Exception {

        list2.clear();

        try {
            Connection connection = FabricaConexao.getConnection();
            String sql = "select * from tca_cidade where fk_estado = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, estado.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Cidade cidade = carregarCidade(resultSet);
                list2.add(cidade);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return list2;
    }



    @Override
    public void delete(Cidade cidade) throws Exception {

        String sql = "delete from tca_cidade where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, cidade.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public void update(Cidade cidade, Cidade e) throws Exception {
        String sql = "update tca_cidade set nome=?, fk_estado=? where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setString(1, e.getNome());
        statement.setInt(2, e.getFk_estado());
        statement.setInt(3, cidade.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public Cidade search(int id) throws Exception {
        Connection connection = FabricaConexao.getConnection();
        String sql = "select * from tca_cidade where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        Cidade cidade = carregarCidade(resultSet);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return cidade;
    }


}
