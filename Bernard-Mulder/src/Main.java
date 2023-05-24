import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //MainScreen scherm = new MainScreen();
        //Noodstop schermNoodstop = new Noodstop();
        ArduinoConnection arduino = new ArduinoConnection("/dev/ttyACM0");

        arduino.openPort();
        Thread.sleep(2000);
        arduino.receiveData();
        while(true){
            if(PacketListener.getIncoming_message().equals("200")){
                break;
            } else {
                arduino.sendData((byte) 1, (byte) 3, (byte) 5);
                Thread.sleep(1000);
            }
        }

    }
}