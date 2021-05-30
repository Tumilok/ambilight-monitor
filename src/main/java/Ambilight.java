import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Algorithm;

import java.awt.*;
import java.io.IOException;

public class Ambilight extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            var loader = new FXMLLoader();
            loader.setLocation(Ambilight.class.getResource("MainView.fxml"));
            Pane rootLayout = loader.load();

            MainController controller = loader.getController();

            configureStage(primaryStage, rootLayout);
            primaryStage.show();
            var algorithm = new Algorithm(controller);
            algorithm.start();
        } catch (IOException | AWTException e) {
            e.printStackTrace();
        }
    }

    private void configureStage(Stage primaryStage, Pane rootLayout) {
        var scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ambilight");
        primaryStage.minWidthProperty().bind(rootLayout.minWidthProperty());
        primaryStage.minHeightProperty().bind(rootLayout.minHeightProperty());
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }
}