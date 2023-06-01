import java.io.IOException;
import java.util.Arrays;

import com.fazecast.jSerialComm.*;

public class ArduinoConnection {

    private boolean messageReceived = false;
    private boolean messageCompletedReceived = false;

    SerialPort sp = SerialPort.getCommPort("/dev/ttyACM0"); //hier wordt de naam van de poort waar de arduino in zit gegeven voor de verbinding

    public void sendData (byte x, byte y, byte z) throws IOException, InterruptedException { //met deze methode worden er drie bytes verstuurd naar de arduino
        sp.setComPortParameters(9600, 8, 1, 0); // hier wordt de verbinding met de arduino ingesteld
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
        byte data[] = {x, y, z}; //de meegegeven bytes worden hier in een array geplaatst

        receiveData(); //er wordt een thread gestart die data leest, zodat de methode weet wanneer de arduino het signaal heeft ontvangen
        while(!messageReceived){ //deze while loop verstuurt het signaal naar de arduino totdat deze het signaal "100" krijgt en uit de while loop gaat
            if(PacketListener.getIncoming_message().equals("100")){
                PacketListener.setIncoming_message("");
                messageReceived = true;
            } else {
                System.out.println("sending data");
                sp.getOutputStream().write(data);
                sp.getOutputStream().flush();
                Thread.sleep(750);
            }
        }
        while(!messageCompletedReceived){ //wanneer de arduino de taak heeft uitgevoerd wordt er 600 teruggestuurd om te laten weten dat de taak compleet is.
            if(PacketListener.getIncoming_message().equals("600")) {
                System.out.println("Switch mode");
                messageCompletedReceived = true;
            }
        }
        messageReceived = false;
        messageCompletedReceived = false;
    }

    public void receiveData() { //met deze methode wordt data via de serial uitgelezen
        sp.setComPortParameters(9600, 8, 1, 0);
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0); // block until bytes can be written
        PacketListener listener = new PacketListener(); //hiervoor wordt een packetlistener aangemaakt, deze klasse implementeert Serialportpacketlistener
        sp.addDataListener(listener); //deze methode start een thread die begint met data lezen doormiddel van de packetlistener
    }

    public void openPort() { //deze methode opent de poort van de arduino. Dit is vereist voordat er data ontvangen of verstuurd kan worden
        if (sp.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
        }
    }

    public void closePort() { //deze methode sluit de poort
        if (sp.closePort()) {
            System.out.println("Port is closed :)");
        } else {
            System.out.println("Failed to close port :(");
        }
    }
}