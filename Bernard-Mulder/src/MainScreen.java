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
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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
	OrderList orderList;
	ProductList productList;
	int index;
	Order selectedOrder;
	Order gezochteOrder;
	JList orderLines;
	JPanel OrderInfo;
	JTextField zoekenOrder;
	ArrayList<Order> orderResult;
	ArrayList<Product> stockResult;
	OrderLine selectedOrderLine;

	JButton aanpassenOrderLine;


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
/*
		// Setup StockScreen
		productList = new ProductList();
		productList.getProductsFromDatabase();

		JPanel StockPanel = new JPanel();
		StockPanel.setLayout(new BorderLayout());

		voorraadList = new JList(productList.getProducts().toArray());
		JScrollPane scrollPaneStockScreen = new JScrollPane(voorraadList);

		scrollPaneStockScreen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneStockScreen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneStockScreen.getVerticalScrollBar().setUnitIncrement(16);

		JPanel selectedStockScreen = new JPanel(new FlowLayout(FlowLayout.LEFT));

		StockPanel.add(scrollPaneStockScreen, BorderLayout.CENTER);

		// Buttom bar (Selected bar)
		JButton buttonAanpassenStock = new JButton("Aanpassen");
		buttonAanpassenStock.setActionCommand("AanpassenStock");
		buttonAanpassenStock.addActionListener(this);

		JTextField zoekenStock = new JTextField(10);
		zoekenStock.setText("Zoeken...");
		zoekenStock.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				if (zoekenStock.getText().equals("Zoeken...")) {
					zoekenStock.setText("");
				}
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				if (zoekenStock.getText().isEmpty()) {
					zoekenStock.setText("Zoeken...");
				}

			}
		});

		zoekenStock.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filterStock(voorraadList, productList.getProducts());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filterStock(voorraadList, productList.getProducts());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filterStock(voorraadList, productList.getProducts());
			}

			public void filterStock(JList<Product> product, List<Product> productList) {
				ArrayList<Product> foundStocks = new ArrayList<>();
				try {
					int orderID = Integer.parseInt(zoekenStock.getText());
					for (Product foundStock : productList) {
						if (String.valueOf(foundStock.getId()).contains(String.valueOf(orderID))) {
							foundStocks.add(foundStock);
						}
					}
//					if (foundStocks.size() >0){
					product.setListData(foundStocks.toArray(new Product[0]));
//
//					}
				} catch (NumberFormatException e) {
					String orderNaam = zoekenStock.getText();
					if (orderNaam.equals("Zoeken...")) {
						foundStocks.addAll(productList);
					} else {
						for (Product foundStock : productList) {
							if (String.valueOf(foundStock.getName()).contains(String.valueOf(orderNaam))) {
								foundStocks.add(foundStock);
							}
						}
						product.setListData(foundStocks.toArray(new Product[0]));

					}

				}
				product.setListData(foundStocks.toArray(new Product[0]));
				if(foundStocks.size() == 0) {
					zoekenStock.setBackground(Color.RED);
				} else {
					zoekenStock.setBackground(Color.white);
				}
			}
		});



		JLabel selectedProductLabel = new JLabel(" ");

		voorraadList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedProductLabel.setText(productList.getProducts().get(voorraadList.getSelectedIndex()).toString());
				index = voorraadList.getSelectedIndex();
			}
		});



		selectedStockScreen.add(buttonAanpassenStock);
		selectedStockScreen.add(zoekenStock);
		selectedStockScreen.add(selectedProductLabel);

		StockPanel.add(selectedStockScreen, BorderLayout.SOUTH);

		root.add("Voorraad", StockPanel);
*/
		// Setup OrderScreen
		orderList = new OrderList();
		orderList.getOrdersFromDatabase();

		JPanel OrderPanel = new JPanel();
		OrderPanel.setLayout(new BorderLayout());

		orderResult =orderList.getOrders();
		orders = new JList(orderResult.toArray());
		JScrollPane scrollPaneOrderScreen = new JScrollPane(orders);

		scrollPaneOrderScreen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPaneOrderScreen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneOrderScreen.getVerticalScrollBar().setUnitIncrement(16);
		scrollPaneOrderScreen.setSize(new Dimension(this.getHeight(), 1500));

		OrderPanel.add(scrollPaneOrderScreen, BorderLayout.WEST);

		//setup orderinfo scherm

		JPanel ProductView = new JPanel();
		ProductView.setLayout(new BoxLayout(ProductView, BoxLayout.Y_AXIS));
		OrderPanel.add(ProductView, BorderLayout.CENTER);

		selectedOrder = orderList.getOrders().get(0);
		JLabel OrderNummer = new JLabel();
		ProductView.add(OrderNummer);

		orderLines = new JList(selectedOrder.getOrderLines().toArray());
		JScrollPane scrollpaneOrderLines = new JScrollPane(orderLines);
		ProductView.add(scrollpaneOrderLines);

		ProductView.setVisible(false);

		JPanel adressLines = new JPanel();
		adressLines.setLayout(new GridLayout(15, 1));
		adressLines.setPreferredSize(new Dimension(this.getWidth()/4, this.getHeight()));

		JPanel adressLinesPanel = new JPanel();
		adressLinesPanel.setLayout(new BorderLayout());
		adressLinesPanel.add(adressLines, BorderLayout.CENTER);

		OrderPanel.add(adressLinesPanel, BorderLayout.EAST);

		JLabel naam = new JLabel();
		adressLines.add(naam);

		JLabel adres = new JLabel();
		adressLines.add(adres);

		JLabel postcode = new JLabel();
		adressLines.add(postcode);

		JLabel woonplaats = new JLabel();
		adressLines.add(woonplaats);

		JLabel telnr = new JLabel();
		adressLines.add(telnr);

		JPanel adressLinesKnoppen = new JPanel();
		adressLinesKnoppen.setLayout(new GridLayout(1, 2));
		adressLinesKnoppen.setPreferredSize(new Dimension(adressLinesPanel.getWidth(), 25));
		adressLinesPanel.add(adressLinesKnoppen, BorderLayout.SOUTH);

		JButton pickOrder = new JButton("Order Picken");
		adressLinesKnoppen.add(pickOrder);

		aanpassenOrderLine = new JButton("Product aanpassen");
		aanpassenOrderLine.setActionCommand("AanpassenOrder");
		aanpassenOrderLine.addActionListener(this);
		adressLinesKnoppen.add(aanpassenOrderLine);


		adressLinesPanel.setVisible(false);

		orders.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedOrder = orderList.getOrders().get(orders.getSelectedIndex());
				orderLines.clearSelection();
				orderLines.setListData(selectedOrder.getOrderLines().toArray());
				OrderNummer.setText("Ordernummer: " + selectedOrder.getId());
				naam.setText("Naam: " + selectedOrder.getCustomer().getName());
				adres.setText("Adres: " + selectedOrder.getCustomer().getAddressLine2());
				postcode.setText("Postcode: " + selectedOrder.getCustomer().getPostalCode());
				woonplaats.setText("Woonplaats: " + selectedOrder.getCustomer().getCity());
				telnr.setText("Telefoonnummer: " + selectedOrder.getCustomer().getName());

				ProductView.setVisible(true);
				adressLinesPanel.setVisible(true);
				OrderPanel.revalidate();
				OrderPanel.repaint();
			}
		});

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
		selectedOrderScreen.add(zoekenOrder);

		OrderPanel.add(selectedOrderScreen, BorderLayout.SOUTH);

		root.add("Orders", OrderPanel);

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

