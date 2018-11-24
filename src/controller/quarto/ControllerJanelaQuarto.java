package controller.quarto;

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
import model.jdbc.JDBCQuartoDAO;
import model.classes.Quarto;
import java.io.IOException;
import java.util.Optional;

public class ControllerJanelaQuarto {

    @FXML
    public Parent mainWindow;

    @FXML
    private TableView tbQuartos;

    @FXML
    private TableColumn tcNumero;

    @FXML
    private TableColumn tcDescricao;

    @FXML
    private TableColumn tcTipo;

    @FXML
    private TableColumn tcOpcao;

    @FXML
    private TextField tfBuscar;

    private ObservableList<Quarto> masterData = FXCollections.observableArrayList();

    @FXML
    public void voltar() {
        trocarJanela("../../view/janelaMain.fxml");
    }

    @FXML
    public void cadastrar() {
        trocarJanela("../../view/quarto/janelaCadastrarQuarto.fxml");
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
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipoQuarto"));

        addButtonToTable();

        tbQuartos.setItems(JDBCQuartoDAO.getInstance().list());

        try {
            masterData.addAll(JDBCQuartoDAO.getInstance().list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbQuartos.setItems(masterData);

        tfBuscar.setPromptText("Buscar...");

        FilteredList<Quarto> filteredData = new FilteredList<>(masterData, p -> true);

        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(quarto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (quarto.getTipoQuarto().getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });

        SortedList<Quarto> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbQuartos.comparatorProperty());
        tbQuartos.setItems(sortedData);
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

    private void carregarLista() throws Exception {
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipoQuarto"));
        addButtonToTable();
        tbQuartos.setItems(JDBCQuartoDAO.getInstance().list());
    }

    public void remover(Quarto quarto) throws Exception {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Remover");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/quarto/janelaRemoverQuarto.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("../../estilo.css").toExternalForm());
            dialog.getDialogPane().getStyleClass().add("myDialog");

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK) {
                if(quarto!=null) {
                    try {
                        JDBCQuartoDAO.getInstance().delete(quarto);
                    } catch (Exception e) {
                        mostrarMensagem("Erro!");
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            carregarLista();
        }
    }

    private void addButtonToTable() {

        Callback<TableColumn<Quarto, Void>, TableCell<Quarto, Void>> cellFactory = new Callback<TableColumn<Quarto, Void>, TableCell<Quarto, Void>>() {
            @Override
            public TableCell<Quarto, Void> call(final TableColumn<Quarto, Void> param) {
                final TableCell<Quarto, Void> cell = new TableCell<Quarto, Void>() {

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
                            Quarto quarto = getTableView().getItems().get(getIndex());
                            JDBCQuartoDAO.q1 = quarto;
                            trocarJanela("../../view/quarto/janelaAlterarQuarto.fxml");
                        });

                        btn2.setOnAction((ActionEvent event) -> {
                            Quarto quarto = getTableView().getItems().get(getIndex());
                            JDBCQuartoDAO.q1 = quarto;
                            try {
                                remover(quarto);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        btn3.setOnAction((ActionEvent event) -> {
                            Quarto quarto = getTableView().getItems().get(getIndex());
                            JDBCQuartoDAO.q1 = quarto;
                            trocarJanela("../../view/quarto/janelaVisualizarQuarto.fxml");
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
