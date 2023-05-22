import Entities.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainScreen extends JFrame implements ActionListener {

	//menuBar variables
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
	JButton aanpassenOrderLine;
	JList orders;
	OrderLine selectedOrderLine;
	Order selectedOrder;


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

		StockScreen stockScreen = new StockScreen();
		root.add("Voorraad", stockScreen);

		OrderScreen orderscreen = new OrderScreen();
		root.add("Order", orderscreen);



		// Setup WeergaveScreen
		root.add("Weergave", new WeergavePanel());

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
		} else if (e.getActionCommand().equals("AanpassenStock")){
//			StockScreenEditPopup popup = new StockScreenEditPopup(productList.getProducts().get(index), "Change stock of '" + productList.getProducts().get(index).getName() + "'", productList.getProducts().get(index).getStock());
//			productList.getProducts().get(index).setStockFromDatabase();
//			this.voorraadList.revalidate();
		} else if (e.getActionCommand().equals("AanpassenOrder")){
			try {
				aanpassenOrderLine.setBackground(null);
				int voorraad = selectedOrderLine.getProduct().getStock();
				OrderScreenEditPopup popup = new OrderScreenEditPopup(selectedOrderLine, "Change order " + selectedOrder.getId() + ", orderline " + selectedOrderLine.getId(), voorraad);
			} catch (NullPointerException npe) {
				aanpassenOrderLine.setBackground(new Color(255, 0, 0));
			}
		} else if (e.getActionCommand().equals("AanpassenPickDatum")) {
			SetPickingPopup popup = new SetPickingPopup(selectedOrder.setPickingCompletedWhen(), selectedOrder.getId());
		}
		this.voorraadList.revalidate();
		this.orders.revalidate();
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

