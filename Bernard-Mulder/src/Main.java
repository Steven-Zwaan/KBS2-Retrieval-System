import Route.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
//        MainScreen scherm = new MainScreen();
        //Noodstop schermNoodstop = new Noodstop();
//        ArduinoConnection arduino = new ArduinoConnection("/dev/ttyACM1");
//
//        arduino.openPort();
//        Thread.sleep(2000);


//        arduino.sendData((byte) 3, (byte) 1, (byte) 1);
//        arduino.sendData((byte) 3, (byte) 3, (byte) 2);
//        arduino.sendData((byte) 3, (byte) 4, (byte) 3);
//        arduino.sendData((byte) 2, (byte) 0, (byte) 0);
//        arduino.sendData((byte) 1, (byte) 2, (byte) 1);

        Point p1 = new Point(0,0);
        Point p2 = new Point(1,4);
        Point p3 = new Point(4,1);
        Point p4 = new Point(4,3);
        Point p5 = new Point(5,5);



        Bruteforce bruteforce = new Bruteforce(p1, p2 ,p3, p4, p5);

        System.out.println(bruteforce.calc());





    }
}