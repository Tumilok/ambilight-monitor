package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;

import static model.Algorithm.*;

public class MainController {
    private final int WIDTH = 800;
    private final int HEIGHT = 400;

    @FXML
    private Pane mainPane;
    private Circle[] circles;

    @FXML
    public void initialize() {
        circles = new Circle[LEDS_NUMBER];
        var startingPoint = 0;
        for (int i = 0; i < LEDS_NUMBER; i++) {
            if (i < X_LEDS_NUMBER) {
                circles[i] = new Circle((WIDTH - 50) - i * (WIDTH / X_LEDS_NUMBER), HEIGHT - 50, 20);
                startingPoint = (WIDTH - 50) - i * (WIDTH / X_LEDS_NUMBER);
            } else if (i < X_LEDS_NUMBER + Y_LEDS_NUMBER) {
                circles[i] = new Circle(startingPoint, (HEIGHT - 120) - (i % (X_LEDS_NUMBER)) * ((HEIGHT - 240) / (Y_LEDS_NUMBER - 1)), 20);
            } else if (i < X_LEDS_NUMBER * 2 + Y_LEDS_NUMBER) {
                circles[(LEDS_NUMBER + X_LEDS_NUMBER) - (i + 1)] = new Circle((WIDTH - 50) - (i % (X_LEDS_NUMBER + Y_LEDS_NUMBER)) * (WIDTH / X_LEDS_NUMBER), 50, 20);
            } else if (i < X_LEDS_NUMBER * 2 + Y_LEDS_NUMBER * 2) {
                circles[(2 * LEDS_NUMBER - Y_LEDS_NUMBER) - (i + 1)] = new Circle(WIDTH - 50, (HEIGHT - 120) - (i % (2 * X_LEDS_NUMBER + Y_LEDS_NUMBER)) * ((HEIGHT - 240) / (Y_LEDS_NUMBER - 1)), 20);
            }
        }
        Arrays.stream(circles).forEach(circle -> mainPane.getChildren().add(circle));
    }

    public void setControllerColors(Color[] colors) {
        for (int i = 0; i < LEDS_NUMBER; i++) {
            circles[i].setFill(colors[i]);
        }
    }
}
