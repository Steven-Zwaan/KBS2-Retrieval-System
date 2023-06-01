import Route.*;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        MainScreen scherm = new MainScreen(); // nieuw scherm aanmaken

        // ArduinoConnection arduino = new ArduinoConnection("COM8");

       Queue instance = Queue.getInstance(); // instance van queue opslaan zodat we er overal bijkunnen
       instance.setScreen(scherm);

       


        // arduino.openPort();
        // arduino.receiveData();

        // arduino.sendData((byte) 3, (byte) 1, (byte) 0);
        // arduino.sendData((byte) 3, (byte) 3, (byte) 2);




    }
}