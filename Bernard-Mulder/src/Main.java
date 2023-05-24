import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //MainScreen scherm = new MainScreen();
        //Noodstop schermNoodstop = new Noodstop();
        ArduinoConnection arduino = new ArduinoConnection("COM5");

        arduino.openPort();
        Thread.sleep(2000);


        if(PacketListener.getIncoming_message().equals("600")) {
            arduino.sendData((byte) 2, (byte) 0, (byte) 0);
        } else {
            arduino.sendData((byte) 1, (byte) 1, (byte) 4);
        }
    }
}