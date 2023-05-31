import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame implements ActionListener {

	//menuBar variables
	private JMenuBar menuBar;
	private JMenuItem menuButtonVoorraad;
	private JMenuItem menuButtonOrders;
	private JMenuItem menuButtonWeergave;
	private JMenuItem menuButtonInpakken;
	private JMenuItem menuButton2;
	private JMenuItem menuButtonHelp;
	CardLayout cardLayout;
	JPanel root;
	WeergaveDrawPanel drawPanel;
	WeergavePanel weergavePanel;
	StockScreenEditPopup popup;
	PackingScreen packingScreen;

	public MainScreen(){
		// Screen setup
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(1200, 500));
		this.setTitle("HMI-application");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cardLayout = new CardLayout();

		root = new JPanel();
		root.setLayout(cardLayout);
		this.add(root);

		// Menubar Setup
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		menuBar.setPreferredSize(new Dimension(getWidth(),30));
		menuBar.setBorder(new MatteBorder(1,0,1,0,Color.lightGray));

		menuButtonVoorraad = new JMenuItem("Voorraad");
		menuButtonVoorraad.setActionCommand("Voorraad");
		menuButtonVoorraad.addActionListener(this);
		menuButtonVoorraad.setFont(new Font("Arial", Font.BOLD, 14));
		menuButtonVoorraad.setBorder(new MatteBorder(1,1,0,1, Color.lightGray));

		menuButtonOrders = new JMenuItem("Orders");
		menuButtonOrders.setActionCommand("Orders");
		menuButtonOrders.addActionListener(this);
		menuButtonOrders.setFont(new Font("Arial", Font.BOLD, 14));
		menuButtonOrders.setBorder(new MatteBorder(1,1,0,1, Color.lightGray));


		menuButtonWeergave = new JMenuItem("Weergave");
		menuButtonWeergave.setActionCommand("Weergave");
		menuButtonWeergave.addActionListener(this);
		menuButtonWeergave.setFont(new Font("Arial", Font.BOLD, 14));
		menuButtonWeergave.setBorder(new MatteBorder(1,1,0,1, Color.lightGray));


		menuButtonInpakken = new JMenuItem("Inpakken");
		menuButtonInpakken.setActionCommand("Inpakken");
		menuButtonInpakken.addActionListener(this);
		menuButtonInpakken.setFont(new Font("Arial", Font.BOLD, 14));
		menuButtonInpakken.setBorder(new MatteBorder(1,1,0,0, Color.lightGray));


		menuButton2 = new JMenuItem(" ");
		menuButton2.setActionCommand("button2");
		menuButton2.addActionListener(this);
		menuButton2.setBorder(new MatteBorder(1,0,0,0, Color.lightGray));
		menuButton2.setOpaque(true);

		menuButtonHelp = new JMenuItem(" ");
		menuButtonHelp.setActionCommand("Help");
		menuButtonHelp.addActionListener(this);
		menuButtonHelp.setFont(new Font("Arial", Font.BOLD, 14));
		menuButtonHelp.setBorder(new MatteBorder(1,0,0,1, Color.lightGray));
		menuButtonHelp.setOpaque(true);



		// Add buttons to menu bar
		menuBar.add(menuButtonVoorraad);
		menuBar.add(menuButtonOrders);
		menuBar.add(menuButtonWeergave);
		menuBar.add(menuButtonInpakken);
		menuBar.add(menuButton2);
		menuBar.add(menuButtonHelp);

		// Setup CardLayout cards
		StockScreen stockScreen = new StockScreen();
		root.add("Voorraad", stockScreen);

		OrderScreen orderScreen = new OrderScreen();
		root.add("Orders", orderScreen);


		weergavePanel = new WeergavePanel();

		root.add("Weergave", weergavePanel);

		packingScreen = new PackingScreen();
		root.add("Inpakken", packingScreen);

		JPanel HelpPanel = new JPanel();
		JScrollPane scrollPaneHelpScreen = new JScrollPane(HelpPanel);

		JButton testbutton = new JButton("Test");
		testbutton.setActionCommand("UpdatePos");
		testbutton.addActionListener(this);
		HelpPanel.add(testbutton);

		root.add("Help", HelpPanel);

		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Voorraad")){
			cardLayout.show(root, "Voorraad");
			System.out.println(cardLayout);
		} else if (e.getActionCommand().equals("Orders")){
			cardLayout.show(root, "Orders");
		} else if (e.getActionCommand().equals("Weergave")){
			cardLayout.show(root, "Weergave");
			weergavePanel.refreshPanel();
		} else if (e.getActionCommand().equals("Inpakken")){
			cardLayout.show(root, "Inpakken");
			packingScreen.refreshPanel();
		}else if (e.getActionCommand().equals("Help")){
			cardLayout.show(root, "Help");
		}
		this.revalidate();
	}

//	public void getAantalPixels() {
//		aantalPixelsHeight = getHeight();
//		aantalPixelsWidth = getWidth();
//	}

	public String ShortenString(String string, int length){
		if (string.length() < length){
			return string;
		} else {
			return string.substring(0, length) + "...";
		}
	}

}

