import Route.*;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        MainScreen scherm = new MainScreen();
        ArduinoConnection arduino = new ArduinoConnection("/dev/ttyACM0");
//
        arduino.openPort();
        arduino.receiveData();
        Thread.sleep(2000);
//
//
        arduino.sendData((byte) 3, (byte) 1, (byte) 0);
        arduino.sendData((byte) 3, (byte) 3, (byte) 2);
//        arduino.sendData((byte) 2, (byte) 0, (byte) 0);
//        arduino.sendData((byte) 1, (byte) 2, (byte) 1);



    }
}