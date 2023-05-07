import java.io.IOException;
import com.fazecast.jSerialComm.*;

public class ArduinoConnection {

    private String port;
    public ArduinoConnection(String port) {
        this.port = port;
    }
    public void sendByte(byte data) {
        SerialPort sp = SerialPort.getCommPort(port); // device name
        sp.setComPortParameters(9600, 8, 1, 0); // default connection settings for Arduino
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written

        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
            return;
        }

        while(true) {
            try {
                sp.getOutputStream().write(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}