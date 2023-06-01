import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PacketListener implements SerialPortPacketListener { //deze klasse is verantwoordelijk voor het ontvangen van data

    private int packetSize = 3; //hier wordt de grootte van de ontvangen data gespecificeerd. 3 betekent dat de data in stukejs van 3 bytes wordt opgedeeld
    private static String incoming_message = "";

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }


    @Override
    public int getPacketSize() { //deze methode geeft het aantal bytes terug dat er per bericht worden ontvangen
        return packetSize;
    }

    public static String getIncoming_message() {
        return incoming_message;
    }

    public static void setIncoming_message(String incoming_message) {
        PacketListener.incoming_message = incoming_message;
    }

    @Override
    public void serialEvent(SerialPortEvent event) { //deze methode handelt het ontvangen van een bericht af bij het ontvangen van nieuwe seriele data
        byte[] newData = event.getReceivedData(); //in deze array worden de ontvangen bytes opgeslagen
        String str = new String(newData).split("\n", 2)[0].replaceAll("\\s+", ""); //hier wordt de array van bytes omgezet naar een string
        incoming_message = str;
        int byteSize = 0;
        try {
            byteSize = str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PacketListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (byteSize == packetSize) { //nadat er wordt gecontroleerd of de grootte van het bericht net zo groot is als de gespecificeerde packetsize wordt het bericht geprint
            System.out.println("Received data: " + str);
        }
    }
}
