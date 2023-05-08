import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {

	static JMenuBar menuBar;
	static JMenuItem menuButtonVoorraad;
	static JMenuItem menuButtonOrders;
	static JMenuItem menuButtonWeergave;
	static JMenuItem menuButton1;
	static JMenuItem menuButton2;
	static JMenuItem menuButtonHelp;


	public MainScreen(){
		// Screen setup
		this.setSize(600,500);
		this.setTitle("HML-application");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		// Menubar Setup
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		menuButtonVoorraad = new JMenuItem("Voorraad");
		menuButtonVoorraad.setActionCommand("Voorraad");


		menuButtonOrders = new JMenuItem("Orders");
		menuButtonOrders.setActionCommand("Orders");


		menuButtonWeergave = new JMenuItem("Weergave");
		menuButtonWeergave.setActionCommand("Weergave");


		menuButton1 = new JMenuItem("-");
		menuButton1.setActionCommand("button1");


		menuButton2 = new JMenuItem("-");
		menuButton2.setActionCommand("button2");


		menuButtonHelp = new JMenuItem("Help");
		menuButtonHelp.setActionCommand("Help");

		MenuItemListener menuItemListener = new MenuItemListener();

		menuButtonVoorraad.addActionListener(menuItemListener);
		menuButtonOrders.addActionListener(menuItemListener);
		menuButtonWeergave.addActionListener(menuItemListener);
		menuButton1.addActionListener(menuItemListener);
		menuButton2.addActionListener(menuItemListener);
		menuButtonHelp.addActionListener(menuItemListener);

		// Add buttons to menu bar
		menuBar.add(menuButtonVoorraad);
		menuBar.add(menuButtonOrders);
		menuBar.add(menuButtonWeergave);
		menuBar.add(menuButton1);
		menuBar.add(menuButton2);
		menuBar.add(menuButtonHelp);

		OrderScreen orderScreenScherm = new OrderScreen("Teddybear", 10, 69);
		OrderScreen orderScreenScherm2 = new OrderScreen("sokken", 1, 420);

		this.add(orderScreenScherm);
		this.add(orderScreenScherm2);

		setVisible(true);
	}


	static class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand() + "JMenuButton clicked");
		}
	}
}

