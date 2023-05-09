import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Noodstop extends JFrame {
    static JMenuBar menuBarNoodstop;
    static JMenuItem menuButtonVoorraadNoodstop;
    static JMenuItem menuButtonOrdersNoodstop;
    static JMenuItem menuButtonWeergaveNoodstop;
    static JMenuItem menuButton1Noodstop;
    static JMenuItem menuButton2Noodstop;
    static JMenuItem menuButtonHelpNoodstop;
    public Noodstop() {
        this.setSize(600,400);
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 60,10));
        this.setMaximumSize(new Dimension(600, 50));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menubar Setup
        menuBarNoodstop = new JMenuBar();
        this.setJMenuBar(menuBarNoodstop);

        menuButtonVoorraadNoodstop = new JMenuItem("Voorraad");
        menuButtonVoorraadNoodstop.setActionCommand("Voorraad");


        menuButtonOrdersNoodstop = new JMenuItem("Orders");
        menuButtonOrdersNoodstop.setActionCommand("Orders");


        menuButtonWeergaveNoodstop = new JMenuItem("Weergave");
        menuButtonWeergaveNoodstop.setActionCommand("Weergave");


        menuButton1Noodstop = new JMenuItem("-");
        menuButton1Noodstop.setActionCommand("button1");


        menuButton2Noodstop = new JMenuItem("-");
        menuButton2Noodstop.setActionCommand("button2");


        menuButtonHelpNoodstop = new JMenuItem("Help");
        menuButtonHelpNoodstop.setActionCommand("Help");

        MainScreen.MenuItemListener menuItemListener = new MainScreen.MenuItemListener();

        menuButtonVoorraadNoodstop.addActionListener(menuItemListener);
        menuButtonOrdersNoodstop.addActionListener(menuItemListener);
        menuButtonWeergaveNoodstop.addActionListener(menuItemListener);
        menuButton1Noodstop.addActionListener(menuItemListener);
        menuButton2Noodstop.addActionListener(menuItemListener);
        menuButtonHelpNoodstop.addActionListener(menuItemListener);

        //adds the buttons in de menuBar
        menuBarNoodstop.add(menuButtonVoorraadNoodstop);
        menuBarNoodstop.add(menuButtonOrdersNoodstop);
        menuBarNoodstop.add(menuButtonWeergaveNoodstop);
        menuBarNoodstop.add(menuButton1Noodstop);
        menuBarNoodstop.add(menuButton2Noodstop);
        menuBarNoodstop.add(menuButtonHelpNoodstop);

        //Panel met border implementeren
        JPanel locatieRobot = new JPanel();
        locatieRobot.setBorder(BorderFactory.createLineBorder(Color.black));
        locatieRobot.setSize(100,80);
        locatieRobot.setBounds(100,80,5,5);
        locatieRobot.setBackground(Color.orange);

        this.add(locatieRobot);
        this.setVisible(true);
    }
}
