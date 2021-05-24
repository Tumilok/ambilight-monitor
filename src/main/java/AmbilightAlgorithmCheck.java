import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Algorithm;

import java.io.IOException;

public class AmbilightAlgorithmCheck extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            var loader = new FXMLLoader();
            loader.setLocation(AmbilightAlgorithmCheck.class.getResource("MainView.fxml"));
            Pane rootLayout = loader.load();

            MainController controller = loader.getController();

            configureStage(primaryStage, rootLayout);
            primaryStage.show();
            var algorithm = new Algorithm(controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureStage(Stage primaryStage, Pane rootLayout) {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ambilight algorithm check");
        primaryStage.minWidthProperty().bind(rootLayout.minWidthProperty());
        primaryStage.minHeightProperty().bind(rootLayout.minHeightProperty());
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }
}