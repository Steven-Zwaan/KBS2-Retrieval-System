import Entities.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainScreen extends JFrame implements ActionListener {

	private JMenuBar menuBar;
	private JMenuItem menuButtonVoorraad;
	private JMenuItem menuButtonOrders;
	private JMenuItem menuButtonWeergave;
	private JMenuItem menuButton1;
	private JMenuItem menuButton2;
	private JMenuItem menuButtonHelp;
	public JScrollPane scrollPaneStockScreen;
	CardLayout cardLayout;
	JPanel root;




	public MainScreen(){
		// Screen setup
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(1200, 500));
		this.setTitle("HML-application");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cardLayout = new CardLayout();

		root = new JPanel();
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		jSplitPane.add(root);
		root.setLayout(cardLayout);

		this.add(jSplitPane);


		// Database order import
		Products products = new Products();
		products.getProductsFromDatabase();

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

		menuButtonVoorraad.addActionListener(this);
		menuButtonOrders.addActionListener(this);
		menuButtonWeergave.addActionListener(this);
		menuButton1.addActionListener(this);
		menuButton2.addActionListener(this);
		menuButtonHelp.addActionListener(this);

		// Add buttons to menu bar
		menuBar.add(menuButtonVoorraad);
		menuBar.add(menuButtonOrders);
		menuBar.add(menuButtonWeergave);
		menuBar.add(menuButton1);
		menuBar.add(menuButton2);
		menuBar.add(menuButtonHelp);



		// Setup StockScreen
		JPanel StockPanel = new JPanel();
		this.scrollPaneStockScreen = new JScrollPane(StockPanel);
		StockPanel.setLayout(new BoxLayout(StockPanel, BoxLayout.Y_AXIS));
		StockPanel.add(Box.createVerticalGlue());
		this.scrollPaneStockScreen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.scrollPaneStockScreen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		for (Product p: products.getProducts()){
			StockPanel.add(new ProductStock(ShortenString(p.getName(), 70), p.getStock(), p.getId()));
		}

		root.add("Voorraad", scrollPaneStockScreen);



		// Setup OrderScreen
		JPanel OrderPanel = new JPanel();
		JScrollPane scrollPaneOrderScreen = new JScrollPane(OrderPanel);

		OrderPanel.setLayout(new BoxLayout(OrderPanel, BoxLayout.Y_AXIS));
		OrderPanel.add(Box.createVerticalGlue());

		scrollPaneOrderScreen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneOrderScreen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JLabel OrdersLabel = new JLabel("Orders");
		OrderPanel.add(OrdersLabel);

		root.add("Orders", scrollPaneOrderScreen);



		// Setup WeergaveScreen
		JPanel WeergavePanel = new JPanel();
		JScrollPane scrollPaneWeergaveScreen = new JScrollPane(WeergavePanel);

		WeergavePanel.setLayout(new BoxLayout(WeergavePanel, BoxLayout.Y_AXIS));
		WeergavePanel.add(Box.createVerticalGlue());

		scrollPaneWeergaveScreen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneWeergaveScreen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JLabel WeergaveLabel = new JLabel("Weergave");
		WeergavePanel.add(WeergaveLabel);

		root.add("Weergave", scrollPaneWeergaveScreen);



		// Setup HelpScreen
		JPanel HelpPanel = new JPanel();
		JScrollPane scrollPaneHelpScreen = new JScrollPane(HelpPanel);

		HelpPanel.setLayout(new BoxLayout(HelpPanel, BoxLayout.Y_AXIS));
		HelpPanel.add(Box.createVerticalGlue());

		scrollPaneHelpScreen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneHelpScreen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JLabel HelpLabel = new JLabel("Help");
		HelpPanel.add(HelpLabel);

		root.add("Help", scrollPaneHelpScreen);



		setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Voorraad")){
			cardLayout.show(root, "Voorraad");
		} else if (e.getActionCommand().equals("Orders")){
			cardLayout.show(root, "Orders");
		} else if (e.getActionCommand().equals("Weergave")){
			cardLayout.show(root, "Weergave");
		} else if (e.getActionCommand().equals("Help")){
			cardLayout.show(root, "Help");
		}
	}

	public String ShortenString(String string, int length){
		if (string.length() < length){
			return string;
		} else {
			return string.substring(0, length) + "...";
		}
	}
}

