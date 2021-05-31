package ambilight.v2;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Arrays;

import static ambilight.v2.Algorithm.*;

public class MainController {
    private final static int FRAME_WIDTH = 800;
    private final static int FRAME_HEIGHT = 450;
    private final static int LED_RADIUS = 20;
    private final static int MARGIN = 50;

    @FXML
    private Pane mainPane;

    private Circle[] leds;

    @FXML
    public void initialize() {
        leds = new Circle[LEDS_NUMBER];

        int distanceX = FRAME_WIDTH / X_LEDS_NUMBER;
        int distanceY = (FRAME_HEIGHT - 2 * MARGIN) / (Y_LEDS_NUMBER + 1);
        int startX = FRAME_WIDTH - MARGIN;
        int startY = FRAME_HEIGHT - MARGIN - distanceY;

        int x, y, position;
        for (int ledNumber = 0; ledNumber < LEDS_NUMBER; ledNumber++) {
            position = ledNumber;

            if (ledNumber < X_LEDS_NUMBER) {
                x = getPoint(startX, ledNumber, distanceX);
                y = (FRAME_HEIGHT - MARGIN);
            } else if (ledNumber < X_LEDS_NUMBER + Y_LEDS_NUMBER) {
                x = MARGIN;
                y = getPoint(startY, ledNumber % X_LEDS_NUMBER, distanceY);
            } else if (ledNumber < X_LEDS_NUMBER * 2 + Y_LEDS_NUMBER) {
                position = (LEDS_NUMBER + X_LEDS_NUMBER) - (ledNumber + 1);
                x = getPoint(startX, ledNumber % (X_LEDS_NUMBER + Y_LEDS_NUMBER), distanceX);
                y = MARGIN;
            } else {
                position = (2 * LEDS_NUMBER - Y_LEDS_NUMBER) - (ledNumber + 1);
                x = FRAME_WIDTH - MARGIN;
                y = getPoint(startY, ledNumber % (2 * X_LEDS_NUMBER + Y_LEDS_NUMBER), distanceY);
            }

            setCircle(position, x, y);
        }

        Arrays.stream(leds).forEach(circle -> mainPane.getChildren().add(circle));
    }

    private int getPoint(int start, int ledNumber, int distance) {
        return start - ledNumber * distance;
    }

    private void setCircle(int position, int x, int y) {
        leds[position] = new Circle(x, y, LED_RADIUS);
    }

    public void setLedsColors(Color[] colors) {
        for (int i = -1; i < LEDS_NUMBER - 1; i++) {
            Color color = i == -1 ? colors[LEDS_NUMBER - 1] : colors[i];
            leds[i + 1].setFill(color);
        }
    }
}
