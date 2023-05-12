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
		buttonAanpassenStock.setActionCommand("Aanpassen");
		buttonAanpassenStock.addActionListener(this);

		JButton buttonZoekenStock = new JButton("Zoeken");
		buttonZoekenStock.setActionCommand("Zoeken");
		buttonZoekenStock.addActionListener(this);

		JTextField zoekenStock = new JTextField(10);

		JLabel selectedProductLabel = new JLabel(" ");

		voorraadList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedProductLabel.setText(productList.getProducts().get(voorraadList.getSelectedIndex()).toString());
				index = voorraadList.getSelectedIndex();
			}
		});

		selectedStockScreen.add(buttonAanpassenStock);
		selectedStockScreen.add(buttonZoekenStock);
		selectedStockScreen.add(zoekenStock);
		selectedStockScreen.add(selectedProductLabel);

		StockPanel.add(selectedStockScreen, BorderLayout.SOUTH);


		root.add("Voorraad", StockPanel);

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

		OrderPanel.add(scrollPaneOrderScreen, BorderLayout.WEST);

		//setup orderinfo scherm
		JPanel OrderInfo = new JPanel();
		OrderInfo.setLayout(new BorderLayout());
		OrderPanel.add(OrderInfo, BorderLayout.EAST);

		JPanel ProductView = new JPanel();
		ProductView.setLayout(new BoxLayout(ProductView, BoxLayout.Y_AXIS));
		OrderInfo.add(ProductView, BorderLayout.WEST);

		JLabel OrderNummer = new JLabel("");
		ProductView.add(OrderNummer);

		selectedOrder = orderList.getOrders().get(0);
		orderLines = new JList(selectedOrder.getOrderLines().toArray());
		JScrollPane scrollpaneOrderLines = new JScrollPane(orderLines);
		scrollpaneOrderLines.setVisible(false);

		ProductView.add(scrollpaneOrderLines);

		orders.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedOrder = orderList.getOrders().get(orders.getSelectedIndex());
				orderLines.clearSelection();
				orderLines.setListData(selectedOrder.getOrderLines().toArray());
				scrollpaneOrderLines.setVisible(true);
				OrderPanel.revalidate();
				OrderPanel.repaint();
			}
		});

		//setup zoekbalk
		JButton buttonzoekenOrder = new JButton("Zoeken");
		buttonzoekenOrder.setActionCommand("Zoeken");
		buttonzoekenOrder.addActionListener(this);

		zoekenOrder = new JTextField(10);
		zoekenOrder.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				filter(orders, orderResult);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				filter(orders, orderResult);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				filter(orders, orderResult);
			}

			public void filter (JList<Order> order, List<Order> orderList){
				ArrayList<Order> foundOrders= new ArrayList<>();
				int orderID = Integer.parseInt(zoekenOrder.getText());
				for (Order foundOrder : orderList){
					if (String.valueOf(foundOrder.getId()).contains(String.valueOf(orderID))){
						foundOrders.add(foundOrder);
					}
				}
				if (foundOrders.size() >0){
					order.setListData(foundOrders.toArray(new Order[0]));
				} else {
					order.setListData(orderList.toArray(new Order[0]));
				}
			}

		});


		JPanel selectedOrderScreen = new JPanel(new FlowLayout(FlowLayout.LEFT));

		selectedOrderScreen.add(buttonzoekenOrder);
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
		} else if (e.getActionCommand().equals("Aanpassen")){
			StockScreenEditPopup popup = new StockScreenEditPopup(productList.getProducts().get(index), "Change stock of '" + productList.getProducts().get(index).getName() + "'", productList.getProducts().get(index).getStock());
//			productList.getProducts().get(index).setStockFromDatabase();
//			this.voorraadList.revalidate();
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

