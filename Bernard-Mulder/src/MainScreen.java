import Models.*;

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
	JList orders;
	OrderLine selectedOrderLine;

	JButton aanpassenOrderLine;
	WeergaveDrawPanel drawPanel;
	WeergavePanel weergavePanel;
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

		orderLines.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedOrderLine = selectedOrder.getOrderLines().get(orderLines.getSelectedIndex());
			}
		});

		//setup zoekbalk
		JButton buttonAanpassenPickDatum = new JButton("Pickdatum aanpassen");
		buttonAanpassenPickDatum.setActionCommand("AanpassenPickDatum");
		buttonAanpassenPickDatum.addActionListener(this);

		JButton buttonzoekenOrder = new JButton("Zoeken");
		buttonzoekenOrder.setActionCommand("Zoeken");
		buttonzoekenOrder.addActionListener(this);

		zoekenOrder = new JTextField(10);
		zoekenOrder.setText("Zoeken...");
		zoekenOrder.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (zoekenOrder.getText().equals("Zoeken...")) {
					zoekenOrder.setText("");
				}
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				if (zoekenOrder.getText().isEmpty()) {
					zoekenOrder.setText("Zoeken...");
				}

			}
		});

		zoekenOrder.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filterOrder(orders, orderResult);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filterOrder(orders, orderResult);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

				filterOrder(orders, orderResult);
			}

			public void filterOrder(JList<Order> order, List<Order> orderList) {
				ArrayList<Order> foundOrders = new ArrayList<>();
				try {
					int orderID = Integer.parseInt(zoekenOrder.getText());
					for (Order foundOrder : orderList) {
						if (String.valueOf(foundOrder.getId()).contains(String.valueOf(orderID))) {
							foundOrders.add(foundOrder);
						}
					}
					order.setListData(foundOrders.toArray(new Order[0]));

				} catch (NumberFormatException e) {
					String orderNaam = zoekenOrder.getText();
					if (orderNaam.equals("Zoeken...")) {
						foundOrders.addAll(orderList);
					}else if (orderNaam.equals("")){
						foundOrders.addAll(orderList);
					} else{
						order.setListData(foundOrders.toArray(new Order[0]));
					}
				}
				order.setListData(foundOrders.toArray(new Order[0]));
				if(foundOrders.size() == 0) {
					zoekenOrder.setBackground(Color.RED);
				} else {
					zoekenOrder.setBackground(Color.white);
				}
			}

		});


		JPanel selectedOrderScreen = new JPanel(new FlowLayout(FlowLayout.LEFT));

		selectedOrderScreen.add(buttonAanpassenPickDatum);
		selectedOrderScreen.add(buttonzoekenOrder);
		selectedOrderScreen.add(zoekenOrder);

		OrderPanel.add(selectedOrderScreen, BorderLayout.SOUTH);

		root.add("Orders", OrderPanel);

		// Setup WeergaveScreen
		weergavePanel = new WeergavePanel();
		root.add("Weergave", weergavePanel);

		// Setup HelpScreen
		JPanel HelpPanel = new JPanel();
		JScrollPane scrollPaneHelpScreen = new JScrollPane(HelpPanel);


		drawPanel = new WeergaveDrawPanel();

		HelpPanel.add(drawPanel);

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
		} else if (e.getActionCommand().equals("Orders")){
			cardLayout.show(root, "Orders");
		} else if (e.getActionCommand().equals("Weergave")){
			weergavePanel.refreshPanel();
			cardLayout.show(root, "Weergave");
		} else if (e.getActionCommand().equals("Help")){
			cardLayout.show(root, "Help");
		} else if (e.getActionCommand().equals("AanpassenStock")){
			StockScreenEditPopup popup = new StockScreenEditPopup(productList.getProductList().get(index), "Change stock of '" + productList.getProductList().get(index).getName() + "'", productList.getProductList().get(index).getStock());
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
		} else if (e.getActionCommand().equals("UpdatePos")){
			drawPanel.updatePos(250, 320, 200);
			drawPanel.repaint();
		}
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

