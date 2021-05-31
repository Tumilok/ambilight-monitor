package ambilight.v1;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.Arrays;


public class Algorithm {
    public static final int LEDS_NUMBER = 24;
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;
    public static final int SKIP = 5;
    private Robot robot;

    private void initRobot() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private Color[] getColors() {
        var screenshot = robot.createScreenCapture(new Rectangle(new Dimension(WIDTH, HEIGHT)));
        var ledsColors = new Color[LEDS_NUMBER];

        int[] xPositions = {1440, 1200, 960, 720, 480, 240, 0, 0, 0, 0, 0, 0, 240, 480, 720, 960, 1200, 1440, 1680, 1680, 1680, 1680, 1680, 1680};
        int[] yPositions = {900, 900, 900, 900, 900, 900, 900, 720, 540, 360, 180, 0, 0, 0, 0, 0, 0, 0, 0, 180, 360, 540, 720, 900};

        for (var i = 0; i < LEDS_NUMBER; i++) {
            var screenshotPart = screenshot.getSubimage(xPositions[i], yPositions[i], 240, 180);
            ledsColors[i] = getLedColor(screenshotPart);
        }
        return ledsColors;
    }

    private Color getLedColor(BufferedImage imgSection) {
        var width = imgSection.getWidth();
        var height = imgSection.getHeight();
        var r = 0;
        var g = 0;
        var b = 0;
        int loops = 0;
        for (var x = 0; x < width; x += SKIP) {
            for (var y = 0; y < height; y += SKIP) {
                var color = new Color(imgSection.getRGB(x, y));
                r += color.getRed();
                g += color.getGreen();
                b += color.getBlue();
                loops++;
            }
        }
        return new Color(r/loops, g/loops, b/loops);
    }

    private void loop() {
        while (true) {
            var ledsColors = getColors();
            Arrays.stream(ledsColors)
                    .forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        var ambilight = new Algorithm();
        ambilight.initRobot();
        ambilight.loop();
    }
}