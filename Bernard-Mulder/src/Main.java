import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
//        MainScreen scherm = new MainScreen();
        //Noodstop schermNoodstop = new Noodstop();
        ArduinoConnection arduino = new ArduinoConnection("COM8");

        arduino.openPort();
        Thread.sleep(2000);


        arduino.sendData((byte) 1, (byte) 1, (byte) 3);
        arduino.sendData((byte) 2, (byte) 0, (byte) 0);
        arduino.sendData((byte) 1, (byte) 2, (byte) 1);
    }
}