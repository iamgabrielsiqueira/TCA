package model.jdbc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FabricaConexao;
import model.dao.TipoQuartoDAO;
import model.classes.TipoQuarto;
import java.sql.*;

public class JDBCTipoQuartoDAO implements TipoQuartoDAO {

    private static JDBCTipoQuartoDAO instance;
    private ObservableList<TipoQuarto> list;
    public static TipoQuarto t1;

    private JDBCTipoQuartoDAO(){
        list = FXCollections.observableArrayList();
    }

    public static JDBCTipoQuartoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCTipoQuartoDAO();
        }
        return instance;
    }


    @Override
    public void create(TipoQuarto tipoQuarto) throws Exception {

        Connection connection = FabricaConexao.getConnection();

        String sql = "insert into tca_tipo_quarto(nome, valor, descricao, data_criado) values(?, ?, ?, ?)";

        Date dataAtual = new Date(System.currentTimeMillis());

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, tipoQuarto.getNome());
        preparedStatement.setDouble(2, tipoQuarto.getValor());
        preparedStatement.setString(3, tipoQuarto.getDescricao());
        preparedStatement.setDate(4, dataAtual);

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    private TipoQuarto carregarTipoQuarto(ResultSet resultSet) throws Exception {

        int id = resultSet.getInt("id");
        String nome = resultSet.getString("nome");
        Double valor = resultSet.getDouble("valor");
        String descricao = resultSet.getString("descricao");
        if(descricao.isEmpty()) {
            descricao = "Sem descrição";
        }
        Date dataCriado = resultSet.getDate("data_criado");
        Date dataAlterado = resultSet.getDate("data_alterado");

        TipoQuarto tipoQuarto = new TipoQuarto();
        tipoQuarto.setId(id);
        tipoQuarto.setNome(nome);
        tipoQuarto.setValor(valor);
        tipoQuarto.setDescricao(descricao);
        tipoQuarto.setDataCriado(dataCriado);
        tipoQuarto.setDataAlterado(dataAlterado);

        return tipoQuarto;
    }

    @Override
    public ObservableList<TipoQuarto> list() throws Exception {

        list.clear();

        try {
            Connection connection = FabricaConexao.getConnection();
            String sql = "select * from tca_tipo_quarto";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                TipoQuarto tipoQuarto = carregarTipoQuarto(resultSet);
                list.add(tipoQuarto);
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
    public void delete(TipoQuarto tipoQuarto) throws Exception {

        String sql = "delete from tca_tipo_quarto where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, tipoQuarto.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public void update(TipoQuarto tipoQuarto, TipoQuarto t) throws Exception {

        Date dataAtual = new Date(System.currentTimeMillis());

        String sql = "update tca_tipo_quarto set nome=?, valor=?, descricao=?, data_alterado=? where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setString(1, t.getNome());
        statement.setDouble(2, t.getValor());
        statement.setString(3, t.getDescricao());
        statement.setDate(4, dataAtual);
        statement.setInt(5, tipoQuarto.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public TipoQuarto search(int id) throws Exception {
        Connection connection = FabricaConexao.getConnection();
        String sql = "select * from tca_tipo_quarto where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        TipoQuarto tipoQuarto = carregarTipoQuarto(resultSet);

        resultSet.close();
        preparedStatement.close();
        connection.close();

        return tipoQuarto;
    }

}
