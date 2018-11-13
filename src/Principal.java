import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Principal extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/janelaPrincipal.fxml"));
        primaryStage.setTitle("Sistema de Gerenciamento de Hotéis");
        primaryStage.setScene(new Scene(root, 800, 600));
        //primaryStage.getScene().getStylesheets().add("skin.css");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
