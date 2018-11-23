package controller.servico;

import javafx.application.Platform;
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
import model.jdbc.JDBCServicoDAO;
import model.classes.Servico;
import java.io.IOException;
import java.util.Optional;

public class ControllerJanelaServico {

    @FXML
    public Parent mainWindow;

    @FXML
    private TableView tbServico;

    @FXML
    private TableColumn tcNome;

    @FXML
    private TableColumn tcValor;

    @FXML
    private TableColumn tcDescricao;

    @FXML
    private TableColumn tcOpcao;

    @FXML
    public void voltar() {
        trocarJanela("../../view/janelaMain.fxml");
    }
//
//    @FXML
//    public void cadastrar() {
//        trocarJanela("../../view/servico/janelaCadastrarServico.fxml");
//    }

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

    public void initialize() {
        try {
            carregarLista();
        } catch (Exception e) {
            mensagem(Alert.AlertType.ERROR, "Erro!");
        }
    }

    public void redirecionar(int id) {

        Dialog<ButtonType> dialog = new Dialog();

        switch(id) {
            case 1:
                dialog.setTitle("Alterar");
                break;
            case 2:
                dialog.setTitle("Visualizar");
                break;
            case 3:
                dialog.setTitle("Remover");
                break;
        }

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../view/servico/janelaRedirecionamentoServico.fxml"));
            dialog.getDialogPane().setContent(fxmlLoader.load());

            ControllerRedirecionamentoServico controle = fxmlLoader.getController();

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent() && result.get()==ButtonType.OK){

                Servico s = controle.processResult();

                JDBCServicoDAO.s1 = s;

                if(s!=null) {
                    try {
                        switch (id) {
                            case 1:
                                trocarJanela("../../view/servico/janelaAlterarServico.fxml");
                                break;
                            case 2:
                                trocarJanela("../../view/servico/janelaVisualizarServico.fxml");
                                break;
                            case 3:
                                trocarJanela("../../view/servico/janelaRemoverServico.fxml");
                                break;
                        }
                    } catch (Exception e) {
                        mensagem(Alert.AlertType.ERROR, "Erro!");
                    }
                }
            }
        }catch (IOException e){
            mensagem(Alert.AlertType.ERROR, "Erro!");
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
                    mensagem(Alert.AlertType.ERROR, "Erro!");
                }
            }
        });

    }

    private void addButtonToTable() {

        Callback<TableColumn<Servico, Void>, TableCell<Servico, Void>> cellFactory = new Callback<TableColumn<Servico, Void>, TableCell<Servico, Void>>() {
            @Override
            public TableCell<Servico, Void> call(final TableColumn<Servico, Void> param) {
                final TableCell<Servico, Void> cell = new TableCell<Servico, Void>() {

                    private Button btn = new Button("");
                    private Button btn2 = new Button("");
                    private final HBox pane = new HBox(btn, btn2);
                    private Image imagem = new Image(getClass().getResourceAsStream("../../imagens/editar.png"));
                    private Image imagem2 = new Image(getClass().getResourceAsStream("../../imagens/remover.png"));
                    private ImageView imgview = new ImageView(imagem);
                    private ImageView imgview2 = new ImageView(imagem2);

                    {
                        pane.alignmentProperty().set(Pos.CENTER);
                        pane.spacingProperty().setValue(5);

                        btn.setGraphic(imgview);
                        btn2.setGraphic(imgview2);
                        btn2.setStyle("-fx-background-color: transparent");
                        btn.setStyle("-fx-background-color: transparent");


                        btn.setOnAction((ActionEvent event) -> {
                            Servico servico = getTableView().getItems().get(getIndex());
                        });

                        btn2.setOnAction((ActionEvent event) -> {
                            Servico servico = getTableView().getItems().get(getIndex());
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
        tbServico.setItems(JDBCServicoDAO.getInstance().list());
    }

    protected void mensagem(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Mensagem!");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
