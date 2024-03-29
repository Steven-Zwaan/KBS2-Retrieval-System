package ArduinoConnection;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;

/**
 * Simple application that is part of an tutorial.
 * The tutorial shows how to establish a serial connection between a Java and Arduino program.
 * @author Michael Schoeffler (www.mschoeffler.de)
 *
 */
public class Startup {

    public static void main(String[] args) throws IOException, InterruptedException {
        SerialPort sp = SerialPort.getCommPort("/dev/ttyACM1");
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
            return;
        }

        for (Integer i = 0; i < 5; ++i) {
            sp.getOutputStream().write(i.byteValue());
            sp.getOutputStream().flush();
            System.out.println("Sent number: " + i);
            Thread.sleep(1000);
        }

        if (sp.closePort()) {
            System.out.println("Port is closed :)");
        } else {
            System.out.println("Failed to close port :(");
            return;
        }


    }

}
