package model;

import controller.MainController;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Algorithm {

    public static final int X_LEDS_NUMBER = 8;
    public static final int Y_LEDS_NUMBER = 4;
    public static final int LEDS_NUMBER = X_LEDS_NUMBER * 2 + Y_LEDS_NUMBER * 2;

    public static final int SKIP = 30;
    public static final int SCREENSHOT_WIDTH = 240;
    public static final int SCREENSHOT_HEIGHT = 180;

    private final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private final Robot robot = new Robot();

    private final int[] xPositions;
    private final int[] yPositions;

    private final MainController controller;


    public Algorithm(MainController controller) throws AWTException {
        this.controller = controller;
        xPositions = getXPositions();
        yPositions = getYPositions();
    }

    private int[] getYPositions() {
        int screenHeight = (int) dimension.getHeight();
        int[] yPositions = new int[LEDS_NUMBER];

        int upperHeight = screenHeight - SCREENSHOT_HEIGHT;
        int lowerHeight = 0;
        for (int position = -1; position < X_LEDS_NUMBER; position++) {
            if (position == -1) {
                yPositions[LEDS_NUMBER - 1] = upperHeight;
            } else {
                yPositions[position] = upperHeight;
            }
            yPositions[position + X_LEDS_NUMBER + Y_LEDS_NUMBER] = lowerHeight;
        }

        int diff = (screenHeight - SCREENSHOT_HEIGHT) / (Y_LEDS_NUMBER + 1);
        for (int i = 0; i < Y_LEDS_NUMBER; i++) {
            int value = diff * (i + 1);
            yPositions[X_LEDS_NUMBER - 1 + Y_LEDS_NUMBER - i - 1] = value;
            yPositions[X_LEDS_NUMBER * 2 - 1 + Y_LEDS_NUMBER + i] = value;
        }

        return yPositions;
    }

    private int[] getXPositions() {
        int screenWidth = (int) dimension.getWidth();
        int[] xPositions = new int[LEDS_NUMBER];

        int upperWidth = screenWidth - SCREENSHOT_WIDTH;
        int lowerWidth = 0;
        for (int position = 0; position < Y_LEDS_NUMBER; position++) {
            xPositions[X_LEDS_NUMBER + position - 1] = lowerWidth;
            xPositions[X_LEDS_NUMBER * 2 + Y_LEDS_NUMBER + position - 1] = upperWidth;
        }

        int diff = (screenWidth - SCREENSHOT_WIDTH) / (X_LEDS_NUMBER - 1);
        for (int i = 0; i < X_LEDS_NUMBER; i++) {
            int value = diff * i;
            if (i == X_LEDS_NUMBER - 1) {
                xPositions[LEDS_NUMBER - 1] = value;
            } else {
                xPositions[X_LEDS_NUMBER - 2 - i] = value;
            }
            xPositions[X_LEDS_NUMBER - 1 + Y_LEDS_NUMBER + i] = value;
        }

        return xPositions;
    }

    private javafx.scene.paint.Color getLedColor(BufferedImage imgSection) {
        var width = imgSection.getWidth();
        var height = imgSection.getHeight();
        var r = 0;
        var g = 0;
        var b = 0;
        int loops = 0;
        for (var x = 0; x < width; x += SKIP) {
            for (var y = 0; y < height; y += SKIP) {
                var color = new java.awt.Color(imgSection.getRGB(x, y));
                r += color.getRed();
                g += color.getGreen();
                b += color.getBlue();
                loops++;
            }
        }
        return Color.rgb(r / loops, g / loops, b / loops);
    }

    private javafx.scene.paint.Color[] getColors() {
        var screenshot = robot.createScreenCapture(new Rectangle(dimension));
        var ledsColors = new javafx.scene.paint.Color[LEDS_NUMBER];

        for (var i = 0; i < LEDS_NUMBER; i++) {
            var screenshotPart = screenshot.getSubimage(xPositions[i], yPositions[i], SCREENSHOT_WIDTH, SCREENSHOT_HEIGHT);
            ledsColors[i] = getLedColor(screenshotPart);
        }
        return ledsColors;
    }

    private void loop() {
        while (true) {
            var ledsColors = getColors();
            controller.setLedsColors(ledsColors);
        }
    }

    public void start() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(this::loop);
    }
}
