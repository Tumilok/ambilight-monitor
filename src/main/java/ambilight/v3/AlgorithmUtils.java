package ambilight.v3;

import java.awt.*;

import static ambilight.v3.Ambilight.*;

public class AlgorithmUtils {
    private final Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private final Rectangle rectangle = new Rectangle(dimension);

    private final Robot robot;
    private final int[] xPositions;
    private final int[] yPositions;

    public AlgorithmUtils() {
        robot = initRobot();
        xPositions = initXPositions();
        yPositions = initYPositions();
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Robot getRobot() {
        return robot;
    }

    public int[] getxPositions() {
        return xPositions;
    }

    public int[] getyPositions() {
        return yPositions;
    }

    private Robot initRobot() {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.err.println("Couldn't create robot instance...");
            System.exit(1);
        }
        return robot;
    }

    private int[] initXPositions() {
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

    private int[] initYPositions() {
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
}
