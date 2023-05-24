import com.fazecast.jSerialComm.*;

public class comPortListener implements SerialPortDataListener {
    private static String bufferReadToString = "";
    private static int cutoffASCII = 10;
    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        byte[] buffer = new byte[event.getSerialPort().bytesAvailable()];
        event.getSerialPort().readBytes(buffer, buffer.length);

        String s = new String(buffer);
        bufferReadToString = bufferReadToString.concat(s); //converts the bytes read from the Serial port to a string

        if ((bufferReadToString.indexOf(cutoffASCII) + 1) > 0) {
            System.out.println(bufferReadToString); //prints out the recived data
        }
    }
}
