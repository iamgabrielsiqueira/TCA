package controller.tipo;

import controller.Mensagem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.jdbc.JDBCTipoQuartoDAO;
import model.classes.TipoQuarto;
import java.io.IOException;
import java.util.Optional;

public class ControllerJanelaTipoQuarto {

    @FXML
    public Parent mainWindow;

    @FXML
    private TableView tbTipos;

    @FXML
    private TableColumn tcNome;

    @FXML
    private TableColumn tcValor;

    @FXML
    private TextField tfBuscar;

    @FXML
    private TableColumn tcDescricao;

    @FXML
    private TableColumn tcOpcao;

    private ObservableList<TipoQuarto> masterData = FXCollections.observableArrayList();

    @FXML
    public void voltar() {
        trocarJanela("../../view/janelaMain.fxml");
    }

    @FXML
    public void cadastrar() {
        trocarJanela("../../view/tipo/janelaCadastrarTipo.fxml");
    }

    @FXML
    public void carregarHospedes() {
        trocarJanela("../../view/hospede/janelaHospede.fxml");
    }

    @FXML
    public void carregarTiposQuartos() {
        trocarJanela("../../view/tipo/janelaTipoQuarto.fxml");
    }

    @FXML
    public void carregarQuartos() {
        trocarJanela("../../view/quarto/janelaQuarto.fxml");
    }

    @FXML
    public void carregarServicos() {
        trocarJanela("../../view/servico/janelaServico.fxml");
    }

    public void initialize() throws Exception {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        addButtonToTable();

        tbTipos.setItems(JDBCTipoQuartoDAO.getInstance().list());

        try {
            masterData.addAll(JDBCTipoQuartoDAO.getInstance().list());
        } catch (Exception e) {
            e.printStackTrace();
        }

        tbTipos.setItems(masterData);

        tfBuscar.setPromptText("Buscar...");

        FilteredList<TipoQuarto> filteredData = new FilteredList<>(masterData, p -> true);

        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(tipo -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (tipo.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        SortedList<TipoQuarto> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbTipos.comparatorProperty());
        tbTipos.setItems(sortedData);
    }

    public void trocarJanela(String address){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(address));

                try {
                    Parent layoutWindow = loader.load();

                    Stage stage=(Stage)mainWindow.getScene().getWindow();

                    stage.setScene(new Scene(layoutWindow,800, 600));
                    stage.setResizable(false);

                }catch (IOException e){
                    mostrarMensagem("Erro!");
                }
            }
        });

    }

    public void remover(TipoQuarto tipoQuarto) throws Exception {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Remover");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/tipo/janelaRemoverTipo.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("../../estilo.css").toExternalForm());
            dialog.getDialogPane().getStyleClass().add("myDialog");

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK) {
                if(tipoQuarto!=null) {
                    try {
                        JDBCTipoQuartoDAO.getInstance().delete(tipoQuarto);
                    } catch (Exception e) {
                        mostrarMensagem("Erro!");
                    }
                }

            }
        } catch (IOException e) {
            mostrarMensagem("Erro!");
        } finally {
            carregarLista();
        }
    }

    private void addButtonToTable() {

        Callback<TableColumn<TipoQuarto, Void>, TableCell<TipoQuarto, Void>> cellFactory = new Callback<TableColumn<TipoQuarto, Void>, TableCell<TipoQuarto, Void>>() {
            @Override
            public TableCell<TipoQuarto, Void> call(final TableColumn<TipoQuarto, Void> param) {
                final TableCell<TipoQuarto, Void> cell = new TableCell<TipoQuarto, Void>() {

                    private Button btn = new Button("");
                    private Button btn2 = new Button("");
                    private Button btn3 = new Button("");
                    private final HBox pane = new HBox(btn, btn2, btn3);
                    private Image imagem = new Image(getClass().getResourceAsStream("../../imagens/editar.png"));
                    private Image imagem2 = new Image(getClass().getResourceAsStream("../../imagens/remover.png"));
                    private ImageView imgview = new ImageView(imagem);
                    private ImageView imgview2 = new ImageView(imagem2);
                    private Image imagem3 = new Image(getClass().getResourceAsStream("../../imagens/visualizar.png"));
                    private ImageView imgview3 = new ImageView(imagem3);

                    {
                        pane.alignmentProperty().set(Pos.CENTER);
                        pane.spacingProperty().setValue(5);

                        btn.setGraphic(imgview);
                        btn2.setGraphic(imgview2);
                        btn2.setStyle("-fx-background-color: transparent");
                        btn.setStyle("-fx-background-color: transparent");
                        btn3.setGraphic(imgview3);
                        btn3.setStyle("-fx-background-color: transparent");

                        btn.setOnAction((ActionEvent event) -> {
                            TipoQuarto tipoQuarto = getTableView().getItems().get(getIndex());
                            JDBCTipoQuartoDAO.t1 = tipoQuarto;
                            trocarJanela("../../view/tipo/janelaAlterarTipo.fxml");
                        });

                        btn2.setOnAction((ActionEvent event) -> {
                            TipoQuarto tipoQuarto = getTableView().getItems().get(getIndex());
                            JDBCTipoQuartoDAO.t1 = tipoQuarto;
                            try {
                                remover(tipoQuarto);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        btn3.setOnAction((ActionEvent event) -> {
                            TipoQuarto tipoQuarto = getTableView().getItems().get(getIndex());
                            JDBCTipoQuartoDAO.t1 = tipoQuarto;
                            trocarJanela("../../view/tipo/janelaVisualizarTipo.fxml");
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(pane);
                        }
                    }
                };
                return cell;
            }
        };

        tcOpcao.setCellFactory(cellFactory);

    }

    private void carregarLista() throws Exception {
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        addButtonToTable();
        tbTipos.setItems(JDBCTipoQuartoDAO.getInstance().list());
    }

    protected void mostrarMensagem(String mensagem) {

        Mensagem.mensagem = mensagem;

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Mensagem");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/janelaMensagem.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("../../estilo.css").toExternalForm());
            dialog.getDialogPane().getStyleClass().add("myDialog");

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK) {
                //System.out.println("1: " + mensagem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
