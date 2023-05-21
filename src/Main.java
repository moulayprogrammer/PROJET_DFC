import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Views/loginView.fxml")));
        primaryStage.setTitle("");
        primaryStage.resizableProperty().setValue(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image("Icons/logo.png"));
        primaryStage.show();*/


//        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainView.fxml"));
        AnchorPane temp = loader.load();
        Scene scene = new Scene(temp);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image("Icons/logo.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
