package controller.hospedagem;

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
import model.classes.Hospedagem;
import model.jdbc.JDBCHospedagemDAO;
import java.io.IOException;
import java.util.Optional;

public class ControllerJanelaHospedagem {

    @FXML
    public Parent mainWindow;

    @FXML
    private TableView tbHospedagens;

    @FXML
    private TableColumn tcHospede;

    @FXML
    private TableColumn tcCheckIn;

    @FXML
    private TableColumn tcCheckOut;

    @FXML
    private TableColumn tcQuarto;

    @FXML
    private TextField tfBuscar;

    @FXML
    private TableColumn tcOpcao;

    private ObservableList<Hospedagem> masterData = FXCollections.observableArrayList();

    @FXML
    public void voltar() {
        trocarJanela("../../view/janelaMain.fxml");
    }

    @FXML
    public void cadastrar() {
        trocarJanela("../../view/hospedagem/janelaCadastrarHospedagem.fxml");
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

    @FXML
    public void carregarHospedagens() {
        trocarJanela("../../view/hospedagem/janelaHospedagem.fxml");
    }

    public void initialize() throws Exception {
        tcHospede.setCellValueFactory(new PropertyValueFactory<>("hospede01"));
        tcCheckIn.setCellValueFactory(new PropertyValueFactory<>("dataCheckIn"));
        tcCheckOut.setCellValueFactory(new PropertyValueFactory<>("dataCheckOut"));
        tcQuarto.setCellValueFactory(new PropertyValueFactory<>("quarto"));

        addButtonToTable();

        tbHospedagens.setItems(JDBCHospedagemDAO.getInstance().list());

        tfBuscar.setPromptText("Buscar...");

        try {
            masterData.addAll(JDBCHospedagemDAO.getInstance().list());
        } catch (Exception e) {
            e.printStackTrace();
        }

        tbHospedagens.setItems(masterData);

        FilteredList<Hospedagem> filteredData = new FilteredList<>(masterData, p -> true);

        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(hospedagem -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (hospedagem.getHospede01().getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (hospedagem.getDataCheckIn().equals(lowerCaseFilter)) {
                    return true;
                } else if (hospedagem.getDataCheckOut().equals(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Hospedagem> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbHospedagens.comparatorProperty());
        tbHospedagens.setItems(sortedData);
    }

    public void carregarLista() throws Exception {
        tcHospede.setCellValueFactory(new PropertyValueFactory<>("hospede01"));
        tcCheckIn.setCellValueFactory(new PropertyValueFactory<>("dataCheckIn"));
        tcCheckOut.setCellValueFactory(new PropertyValueFactory<>("dataCheckOut"));
        tcQuarto.setCellValueFactory(new PropertyValueFactory<>("quarto"));

        addButtonToTable();

        tbHospedagens.setItems(JDBCHospedagemDAO.getInstance().list());
    }

    public void remover(Hospedagem hospedagem) throws Exception {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Remover");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/hospedagem/janelaRemoverHospedagem.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("../../estilo.css").toExternalForm());
            dialog.getDialogPane().getStyleClass().add("myDialog");

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK) {
                if(hospedagem!=null) {
                    try {
                        JDBCHospedagemDAO.getInstance().delete(hospedagem);
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

        Callback<TableColumn<Hospedagem, Void>, TableCell<Hospedagem, Void>> cellFactory = new Callback<TableColumn<Hospedagem, Void>, TableCell<Hospedagem, Void>>() {
            @Override
            public TableCell<Hospedagem, Void> call(final TableColumn<Hospedagem, Void> param) {
                final TableCell<Hospedagem, Void> cell = new TableCell<Hospedagem, Void>() {

                    private Button btn = new Button("");
                    private Button btn2 = new Button("");
                    private Button btn3 = new Button("");
                    private Button btn4 = new Button("");

                    private final HBox pane = new HBox(btn4, btn, btn3, btn2);

                    private Image imagem = new Image(getClass().getResourceAsStream("../../imagens/editar.png"));
                    private Image imagem2 = new Image(getClass().getResourceAsStream("../../imagens/remover.png"));
                    private Image imagem3 = new Image(getClass().getResourceAsStream("../../imagens/visualizar.png"));
                    private Image imagem4 = new Image(getClass().getResourceAsStream("../../imagens/add.png"));

                    private ImageView imgview = new ImageView(imagem);
                    private ImageView imgview2 = new ImageView(imagem2);
                    private ImageView imgview3 = new ImageView(imagem3);
                    private ImageView imgview4 = new ImageView(imagem4);

                    {
                        pane.alignmentProperty().set(Pos.CENTER);
                        pane.spacingProperty().setValue(5);

                        btn.setGraphic(imgview);
                        btn2.setGraphic(imgview2);
                        btn3.setGraphic(imgview3);
                        btn4.setGraphic(imgview4);

                        btn.setStyle("-fx-background-color: transparent");
                        btn2.setStyle("-fx-background-color: transparent");
                        btn3.setStyle("-fx-background-color: transparent");
                        btn4.setStyle("-fx-background-color: transparent");

                        btn.setOnAction((ActionEvent event) -> {
                            Hospedagem hospedagem = getTableView().getItems().get(getIndex());
                            JDBCHospedagemDAO.h1 = hospedagem;
                            trocarJanela("../../view/hospedagem/janelaAlterarHospedagem.fxml");
                        });

                        btn2.setOnAction((ActionEvent event) -> {
                            Hospedagem hospedagem = getTableView().getItems().get(getIndex());
                            JDBCHospedagemDAO.h1 = hospedagem;
                            try {
                                remover(hospedagem);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        btn3.setOnAction((ActionEvent event) -> {
                            Hospedagem hospedagem = getTableView().getItems().get(getIndex());
                            JDBCHospedagemDAO.h1 = hospedagem;
                            trocarJanela("../../view/hospedagem/janelaVisualizarHospedagem.fxml");
                        });

                        btn4.setOnAction((ActionEvent event) -> {
                            Hospedagem hospedagem = getTableView().getItems().get(getIndex());
                            JDBCHospedagemDAO.h1 = hospedagem;
                            trocarJanela("../../view/hospedagem/janelaHospedagemServico.fxml");
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
                    e.printStackTrace();
                }
            }
        });

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
