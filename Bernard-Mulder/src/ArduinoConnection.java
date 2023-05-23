import java.io.IOException;
import java.util.Arrays;

import com.fazecast.jSerialComm.*;

public class ArduinoConnection {

    private String port;
    public ArduinoConnection(String port) {
        this.port = port;
    }

    SerialPort sp = SerialPort.getCommPort("COM5"); // device name
    public static int PACKET_SIZE_IN_BYTES = 4;
    public void sendData (byte x, byte y, byte z) throws IOException {
        sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

        byte data[] = {x, y, z};
        sp.getOutputStream().write(data);
        sp.getOutputStream().flush();
    }

    public void receiveData() {
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written
        PacketListener listener = new PacketListener();
        sp.addDataListener(listener);
    }

    public void openPort() {
        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
        }
    }

    public void closePort() {
        if (sp.closePort()) {
            System.out.println("Port is closed :)");
        } else {
            System.out.println("Failed to close port :(");
        }
    }
}