package model.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FabricaConexao;
import model.dao.ServicoDAO;
import model.classes.Servico;

import java.sql.*;

public class JDBCServicoDAO implements ServicoDAO {

    private static JDBCServicoDAO instance;
    private ObservableList<Servico> list;
    public static Servico s1;

    private JDBCServicoDAO(){
        list = FXCollections.observableArrayList();
    }

    public static JDBCServicoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCServicoDAO();
        }
        return instance;
    }


    @Override
    public void create(Servico servico) throws Exception {

        Connection connection = FabricaConexao.getConnection();

        String sql = "insert into tca_servico(nome, valor, descricao, data_criado) values(?, ?, ?, ?)";

        Date dataAtual = new Date(System.currentTimeMillis());

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, servico.getNome());
        preparedStatement.setDouble(2, servico.getValor());
        preparedStatement.setString(3, servico.getDescricao());
        preparedStatement.setDate(4, dataAtual);

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    private Servico carregarServico(ResultSet resultSet) throws Exception {

        int id = resultSet.getInt("id");
        String nome = resultSet.getString("nome");
        Double valor = resultSet.getDouble("valor");
        String descricao = resultSet.getString("descricao");
        if(descricao.isEmpty()) {
            descricao = "Sem descrição";
        }
        Date dataCriado = resultSet.getDate("data_criado");
        Date dataAlterado = resultSet.getDate("data_alterado");

        Servico servico = new Servico();
        servico.setId(id);
        servico.setNome(nome);
        servico.setValor(valor);
        servico.setDescricao(descricao);
        servico.setDataCriado(dataCriado);
        servico.setDataAlterado(dataAlterado);

        return servico;
    }

    @Override
    public ObservableList<Servico> list() throws Exception {

        list.clear();

        try {
            Connection connection = FabricaConexao.getConnection();
            String sql = "select * from tca_servico";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Servico servico = carregarServico(resultSet);
                list.add(servico);
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
    public void delete(Servico servico) throws Exception {

        String sql = "delete from tca_servico where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, servico.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public void update(Servico servico, Servico s) throws Exception {

        Date dataAtual = new Date(System.currentTimeMillis());

        String sql = "update tca_servico set nome=?, valor=?, descricao=?, data_alterado=? where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setString(1, s.getNome());
        statement.setDouble(2, s.getValor());
        statement.setString(3, s.getDescricao());
        statement.setDate(4, dataAtual);
        statement.setInt(5, servico.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public Servico search(int id) throws Exception {
        Connection connection = FabricaConexao.getConnection();
        String sql = "select * from tca_servico where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        Servico servico = carregarServico(resultSet);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return servico;
    }

}
