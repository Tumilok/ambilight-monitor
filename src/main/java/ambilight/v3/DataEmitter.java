package ambilight.v3;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import static ambilight.v3.Ambilight.LEDS_NUMBER;

class DataEmitter {

    public static final int DATA_RATE = 115200;
    public static final int TIMEOUT = 2000;

    private SerialPort serial;
    private OutputStream output;

    {
        initSerial();
        initOutputStream();
    }

    private void initSerial() {
        CommPortIdentifier serialPortId = getSerialPortId();
        try {
            serial = (SerialPort) serialPortId.open(this.getClass().getName(), TIMEOUT);
            serial.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (PortInUseException | UnsupportedCommOperationException e) {
            e.printStackTrace();
        }
    }

    private CommPortIdentifier getSerialPortId() {
        CommPortIdentifier serialPortId = null;
        Enumeration enumComm = CommPortIdentifier.getPortIdentifiers();
        while (enumComm.hasMoreElements() && serialPortId == null) {
            serialPortId = (CommPortIdentifier) enumComm.nextElement();
        }
        return serialPortId;
    }

    private void initOutputStream() {
        try {
            output = serial.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendColors(Color[] leds) {
        try {
            output.write(0xff);
            for (int i = 0; i < LEDS_NUMBER; i++) {
                output.write((int) leds[i].getRed());
                output.write((int) leds[i].getGreen());
                output.write((int) leds[i].getBlue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
