import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //MainScreen scherm = new MainScreen();
        //Noodstop schermNoodstop = new Noodstop();
        ArduinoConnection arduino = new ArduinoConnection("COM5");

        arduino.openPort();
        Thread.sleep(2000);
        arduino.receiveData();
        arduino.sendData((byte) 1, (byte) 3, (byte) 5);
        if(PacketListener.getIncoming_message().equals("200")){

        } else {

        }
        Thread.sleep(1000);

    }
}