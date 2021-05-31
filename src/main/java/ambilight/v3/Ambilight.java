package ambilight.v3;

class Ambilight {

    public static final int X_LEDS_NUMBER = 8;
    public static final int Y_LEDS_NUMBER = 4;
    public static final int LEDS_NUMBER = X_LEDS_NUMBER * 2 + Y_LEDS_NUMBER * 2;

    public static final int SKIP = 30;
    public static final int SCREENSHOT_WIDTH = 240;
    public static final int SCREENSHOT_HEIGHT = 180;

    private static final long DELAY = 10;

    private final Algorithm algorithm = new Algorithm();
    private final DataEmitter emitter = new DataEmitter();

    private void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loop() {
        while (true) {
            var ledsColors = algorithm.getColors();
            emitter.sendColors(ledsColors);
            sleep();
        }
    }

    private void start() {
        loop();
    }

    public static void main(String[] args) {
        Ambilight ambilight = new Ambilight();
        ambilight.start();
    }
}
