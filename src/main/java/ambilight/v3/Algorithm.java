package ambilight.v3;

import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

import static ambilight.v3.Ambilight.*;

class Algorithm {
    private final AlgorithmUtils algorithmUtils = new AlgorithmUtils();

    private Color getLedColor(BufferedImage imgSection) {
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

    Color[] getColors() {
        var screenshot = algorithmUtils.getRobot().createScreenCapture(algorithmUtils.getRectangle());
        var xPositions = algorithmUtils.getxPositions();
        var yPositions = algorithmUtils.getyPositions();
        var ledsColors = new Color[LEDS_NUMBER];

        for (var i = 0; i < LEDS_NUMBER; i++) {
            var screenshotPart = screenshot.getSubimage(xPositions[i], yPositions[i], SCREENSHOT_WIDTH, SCREENSHOT_HEIGHT);
            ledsColors[i] = getLedColor(screenshotPart);
        }
        return ledsColors;
    }
}
