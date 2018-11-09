package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class JDBCQuartoDAO implements QuartoDAO {

    private static JDBCQuartoDAO instance;
    private ObservableList<Quarto> list;
    public static Quarto q1;

    private JDBCQuartoDAO(){
        list = FXCollections.observableArrayList();
    }

    public static JDBCQuartoDAO getInstance() {
        if(instance == null) {
            instance = new JDBCQuartoDAO();
        }
        return instance;
    }


    @Override
    public void create(Quarto quarto) throws Exception {

        Connection connection = FabricaConexao.getConnection();

        String sql = "insert into tca_quarto(numero, descricao, fk_tipo_quarto_id, data_criado) values(?, ?, ?, ?)";

        Date dataAtual = new Date(System.currentTimeMillis());

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1, quarto.getNumero());
        preparedStatement.setString(2, quarto.getDescricao());
        preparedStatement.setInt(3, quarto.getTipoQuarto().getId());
        preparedStatement.setDate(4, dataAtual);

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();

    }

    private Quarto carregarQuarto(ResultSet resultSet) throws Exception {

        int id = resultSet.getInt("id");
        int numero = resultSet.getInt("numero");
        String descricao = resultSet.getString("descricao");
        int fk_tipo_quarto_id = resultSet.getInt("fk_tipo_quarto_id");
        Date dataCriado = resultSet.getDate("data_criado");
        Date dataAlterado = resultSet.getDate("data_alterado");

        Quarto quarto = new Quarto();
        quarto.setId(id);
        quarto.setNumero(numero);
        quarto.setDescricao(descricao);
        TipoQuarto tipoQuarto = JDBCTipoQuartoDAO.getInstance().search(fk_tipo_quarto_id);
        quarto.setTipoQuarto(tipoQuarto);
        quarto.setDataCriado(dataCriado);
        quarto.setDataAlterado(dataAlterado);

        return quarto;
    }

    @Override
    public ObservableList<Quarto> list() throws Exception {

        list.clear();

        try {
            Connection connection = FabricaConexao.getConnection();
            String sql = "select * from tca_quarto";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Quarto quarto = carregarQuarto(resultSet);
                list.add(quarto);
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
    public void delete(Quarto quarto) throws Exception {

        String sql = "delete from tca_quarto where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, quarto.getId());

        statement.execute();
        statement.close();
        c.close();

    }

    @Override
    public void update(Quarto quarto, Quarto q) throws Exception {

        Date dataAtual = new Date(System.currentTimeMillis());

        String sql = "update tca_quarto set numero=?, descricao=?, fk_tipo_quarto_id=?, data_alterado=? where id=?";

        Connection c = FabricaConexao.getConnection();
        PreparedStatement statement = c.prepareStatement(sql);

        statement.setInt(1, q.getNumero());
        statement.setString(2, q.getDescricao());
        statement.setInt(3, q.getTipoQuarto().getId());
        statement.setDate(4, dataAtual);
        statement.setInt(5, quarto.getId());

        statement.execute();
        statement.close();
        c.close();

    }

}
