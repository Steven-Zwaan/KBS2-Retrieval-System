import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class PacketListener implements SerialPortPacketListener {

    private int packetSize = 3;
    private static String incoming_message = "";

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public int getPacketSize() {
        return packetSize;
    }

    public static String getIncoming_message() {
        return incoming_message;
    }

    public static void setIncoming_message(String incoming_message) {
        PacketListener.incoming_message = incoming_message;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        byte[] newData = event.getReceivedData();
        String str = new String(newData).split("\n", 2)[0].replaceAll("\\s+", "");
        incoming_message = str;
        int byteSize = 0;
        try {
            byteSize = str.getBytes("ASCII").length;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PacketListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (byteSize == packetSize) {
            System.out.println("Received data: " + str);
        }
    }
}
