import Entities.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
	CardLayout cardLayout;
	JPanel root;
	JList voorraadList;
	ProductList productList;
	int index;




	public MainScreen(){
		// Screen setup
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setMinimumSize(new Dimension(1200, 500));
		this.setTitle("HML-application");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cardLayout = new CardLayout();

		root = new JPanel();
		root.setLayout(cardLayout);
		this.add(root);



		// Database order import
		productList = new ProductList();
		productList.getProductsFromDatabase();

		OrderList orderList = new OrderList();

		// Menubar Setup
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		menuButtonVoorraad = new JMenuItem("Voorraad");
		menuButtonVoorraad.setActionCommand("Voorraad");
		menuButtonVoorraad.addActionListener(this);

		menuButtonOrders = new JMenuItem("Orders");
		menuButtonOrders.setActionCommand("Orders");
		menuButtonOrders.addActionListener(this);

		menuButtonWeergave = new JMenuItem("Weergave");
		menuButtonWeergave.setActionCommand("Weergave");
		menuButtonWeergave.addActionListener(this);

		menuButton1 = new JMenuItem("-");
		menuButton1.setActionCommand("button1");
		menuButton1.addActionListener(this);

		menuButton2 = new JMenuItem("-");
		menuButton2.setActionCommand("button2");
		menuButton2.addActionListener(this);

		menuButtonHelp = new JMenuItem("Help");
		menuButtonHelp.setActionCommand("Help");
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
		StockPanel.setLayout(new BorderLayout());

		voorraadList = new JList(productList.getProducts().toArray());
		JScrollPane scrollPaneStockScreen = new JScrollPane(voorraadList);

		scrollPaneStockScreen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneStockScreen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneStockScreen.getVerticalScrollBar().setUnitIncrement(16);

		JPanel selectedStockScreen = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// Buttom bar (Selected bar)
		JButton buttonAanpassen = new JButton("Aanpassen");
		buttonAanpassen.setActionCommand("Aanpassen");
		buttonAanpassen.addActionListener(this);

		JButton buttonZoeken = new JButton("Zoeken");
		buttonZoeken.setActionCommand("Zoeken");
		buttonZoeken.addActionListener(this);

		JTextField JTextField_Zoeken = new JTextField(10);

		JLabel selectedProductLabel = new JLabel(" ");

		voorraadList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedProductLabel.setText(productList.getProducts().get(voorraadList.getSelectedIndex()).toString());
				index = voorraadList.getSelectedIndex();
			}
		});

		selectedStockScreen.add(buttonAanpassen);
		selectedStockScreen.add(buttonZoeken);
		selectedStockScreen.add(JTextField_Zoeken);
		selectedStockScreen.add(selectedProductLabel);


		StockPanel.add(scrollPaneStockScreen, BorderLayout.CENTER);
		StockPanel.add(selectedStockScreen, BorderLayout.SOUTH);


		root.add("Voorraad", StockPanel);

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
		} else if (e.getActionCommand().equals("Aanpassen")){
			StockScreenEditPopup popup = new StockScreenEditPopup(productList.getProducts().get(index), "Change stock of '" + productList.getProducts().get(index).getName() + "'", productList.getProducts().get(index).getStock());
//			productList.getProducts().get(index).setStockFromDatabase();
//			this.voorraadList.revalidate();
		}
		this.voorraadList.revalidate();
		this.revalidate();
	}

	public String ShortenString(String string, int length){
		if (string.length() < length){
			return string;
		} else {
			return string.substring(0, length) + "...";
		}
	}
}

