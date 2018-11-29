package controller.hospede;

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
import model.classes.Hospede;
import model.jdbc.JDBCHospedagemDAO;
import model.jdbc.JDBCHospedeDAO;
import java.io.IOException;
import java.util.Optional;

public class ControllerJanelaHospede {

    @FXML
    public Parent mainWindow;

    @FXML
    private TableView tbHospedes;

    @FXML
    private TableColumn tcNome;

    @FXML
    private TableColumn tcCPF;

    @FXML
    private TableColumn tcData;

    @FXML
    private TextField tfBuscar;

    @FXML
    private TableColumn tcOpcao;

    private ObservableList<Hospede> masterData = FXCollections.observableArrayList();

    @FXML
    public void voltar() {
        trocarJanela("../../view/janelaMain.fxml");
    }

    @FXML
    public void cadastrar() {
        trocarJanela("../../view/hospede/janelaCadastrarHospede.fxml");
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


    public void initialize() {

        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));

        addButtonToTable();

        try {
            masterData.addAll(JDBCHospedeDAO.getInstance().list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        tbHospedes.setItems(masterData);

        tfBuscar.setPromptText("Buscar...");

        FilteredList<Hospede> filteredData = new FilteredList<>(masterData, p -> true);

        tfBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(hospede -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (hospede.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (hospede.getCpf().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (hospede.getDataNasc().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });

        SortedList<Hospede> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tbHospedes.comparatorProperty());
        tbHospedes.setItems(sortedData);
    }

    public void remover(Hospede hospede) throws Exception {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Remover");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/hospede/janelaRemoverHospede.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("../../estilo.css").toExternalForm());
            dialog.getDialogPane().getStyleClass().add("myDialog");

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK) {
                if(hospede!=null) {
                    int flag = 0;
                    for (Hospedagem hospedagem: JDBCHospedagemDAO.getInstance().list()) {
                        if(hospedagem.getHospede01().getId() == hospede.getId()) {
                            flag = 1;
                        }
                    }

                    if(flag == 0) {
                        try {
                            JDBCHospedeDAO.getInstance().delete(hospede);
                        } catch (Exception e) {
                            mostrarMensagem("Erro!");
                        }
                    } else {
                        mostrarMensagem("Não é possível deletar este hóspede!");
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            carregarLista();
        }
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

    private void addButtonToTable() {

        Callback<TableColumn<Hospede, Void>, TableCell<Hospede, Void>> cellFactory = new Callback<TableColumn<Hospede, Void>, TableCell<Hospede, Void>>() {
            @Override
            public TableCell<Hospede, Void> call(final TableColumn<Hospede, Void> param) {
                final TableCell<Hospede, Void> cell = new TableCell<Hospede, Void>() {

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
                        btn3.setGraphic(imgview3);
                        btn3.setStyle("-fx-background-color: transparent");
                        btn2.setStyle("-fx-background-color: transparent");
                        btn.setStyle("-fx-background-color: transparent");


                        btn.setOnAction((ActionEvent event) -> {
                            Hospede hospede = getTableView().getItems().get(getIndex());
                            JDBCHospedeDAO.h1 = hospede;
                            trocarJanela("../../view/hospede/janelaAlterarHospede.fxml");
                        });

                        btn2.setOnAction((ActionEvent event) -> {
                            Hospede hospede = getTableView().getItems().get(getIndex());
                            JDBCHospedeDAO.h1 = hospede;
                            try {
                                remover(hospede);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        btn3.setOnAction((ActionEvent event) -> {
                            Hospede hospede = getTableView().getItems().get(getIndex());
                            JDBCHospedeDAO.h1 = hospede;
                            trocarJanela("../../view/hospede/janelaVisualizarHospede.fxml");
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
        tcCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
        addButtonToTable();
        tbHospedes.setItems(JDBCHospedeDAO.getInstance().list());
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

            if(result.isPresent() && result.get()==ButtonType.OK) { }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
