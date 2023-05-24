//package Screen;
//
//import Entities.Order;
//import Entities.OrderLine;
//import Entities.OrderList;
//import Entities.ProductList;
//
//import javax.swing.*;
//import javax.swing.event.DocumentEvent;
//import javax.swing.event.DocumentListener;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import java.util.List;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.FocusAdapter;
//import java.awt.event.FocusEvent;
//import java.util.ArrayList;
//
//public class Orderscreen extends JPanel implements ActionListener {
//    JList orders;
//    OrderList orderList;
//    ProductList productList;
//    int index;
//    Order selectedOrder;
//    Order gezochteOrder;
//    JList orderLines;
//    JPanel OrderInfo;
//    JTextField zoekenOrder;
//    ArrayList<Order> orderResult;
//    OrderLine selectedOrderLine;
//
//    JButton aanpassenOrderLine;
//    JButton PakbonPrinten;
//
//
//public Orderscreen () {
//    orderList = new OrderList();
//    orderList.getOrdersFromDatabase();
//
//    JPanel OrderPanel = new JPanel();
//    OrderPanel.setLayout(new BorderLayout());
//
//    orderResult =orderList.getOrders();
//    orders = new JList(orderResult.toArray());
//    JScrollPane scrollPaneOrderScreen = new JScrollPane(orders);
//
//    scrollPaneOrderScreen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//    scrollPaneOrderScreen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//    scrollPaneOrderScreen.getVerticalScrollBar().setUnitIncrement(16);
//    scrollPaneOrderScreen.setSize(new Dimension(this.getHeight(), 1500));
//
//    OrderPanel.add(scrollPaneOrderScreen, BorderLayout.WEST);
//    JPanel ProductView = new JPanel();
//    ProductView.setLayout(new BoxLayout(ProductView, BoxLayout.Y_AXIS));
//    this.add(ProductView, BorderLayout.CENTER);
//
//    selectedOrder = orderList.getOrders().get(0);
//    JLabel OrderNummer = new JLabel();
//    ProductView.add(OrderNummer);
//
//    orderLines = new JList(selectedOrder.getOrderLines().toArray());
//    JScrollPane scrollpaneOrderLines = new JScrollPane(orderLines);
//    ProductView.add(scrollpaneOrderLines);
//
//    ProductView.setVisible(false);
//
//    JPanel adressLines = new JPanel();
//    adressLines.setLayout(new GridLayout(15, 1));
//    adressLines.setPreferredSize(new Dimension(this.getWidth() / 4, this.getHeight()));
//
//    JPanel adressLinesPanel = new JPanel();
//    adressLinesPanel.setLayout(new BorderLayout());
//    adressLinesPanel.add(adressLines, BorderLayout.CENTER);
//
//    this.add(adressLinesPanel, BorderLayout.EAST);
//
//    JLabel naam = new JLabel();
//    adressLines.add(naam);
//
//    JLabel adres = new JLabel();
//    adressLines.add(adres);
//
//    JLabel postcode = new JLabel();
//    adressLines.add(postcode);
//
//    JLabel woonplaats = new JLabel();
//    adressLines.add(woonplaats);
//
//    JLabel telnr = new JLabel();
//    adressLines.add(telnr);
//
//    JPanel adressLinesKnoppen = new JPanel();
//    adressLinesKnoppen.setLayout(new GridLayout(1, 2));
//    adressLinesKnoppen.setPreferredSize(new Dimension(adressLinesPanel.getWidth(), 25));
//    adressLinesPanel.add(adressLinesKnoppen, BorderLayout.SOUTH);
//
//    JButton pickOrder = new JButton("Order Picken");
//    adressLinesKnoppen.add(pickOrder);
//
//    aanpassenOrderLine = new JButton("Product aanpassen");
//    aanpassenOrderLine.setActionCommand("AanpassenOrder");
//    aanpassenOrderLine.addActionListener(this);
//    adressLinesKnoppen.add(aanpassenOrderLine);
//
//    JButton Pakbon = new JButton("Pakbon");
//
//    adressLinesKnoppen.add(Pakbon);
//
//
//    adressLinesPanel.setVisible(false);
//
//    orders.addListSelectionListener(new ListSelectionListener() {
//        public void valueChanged(ListSelectionEvent e) {
//            selectedOrder = orderList.getOrders().get(orders.getSelectedIndex());
//            orderLines.clearSelection();
//            orderLines.setListData(selectedOrder.getOrderLines().toArray());
//            OrderNummer.setText("Ordernummer: " + selectedOrder.getId());
//            naam.setText("Naam: " + selectedOrder.getCustomer().getName());
//            adres.setText("Adres: " + selectedOrder.getCustomer().getAddressLine2());
//            postcode.setText("Postcode: " + selectedOrder.getCustomer().getPostalCode());
//            woonplaats.setText("Woonplaats: " + selectedOrder.getCustomer().getCity());
//            telnr.setText("Telefoonnummer: " + selectedOrder.getCustomer().getName());
//
//            ProductView.setVisible(true);
//            adressLinesPanel.setVisible(true);
//            revalidate();
//            repaint();
//        }
//    });
//
//    orderLines.addListSelectionListener(new ListSelectionListener() {
//        @Override
//        public void valueChanged(ListSelectionEvent e) {
//            selectedOrderLine = selectedOrder.getOrderLines().get(orderLines.getSelectedIndex());
//        }
//    });
//
//    //setup zoekbalk
//    JButton buttonAanpassenPickDatum = new JButton("Pickdatum aanpassen");
//    buttonAanpassenPickDatum.setActionCommand("AanpassenPickDatum");
//    buttonAanpassenPickDatum.addActionListener(this);
//
//    JButton buttonzoekenOrder = new JButton("Zoeken");
//    buttonzoekenOrder.setActionCommand("Zoeken");
//    buttonzoekenOrder.addActionListener(this);
//
//    zoekenOrder = new JTextField(10);
//    zoekenOrder.setText("Zoeken...");
//    zoekenOrder.addFocusListener(new FocusAdapter() {
//        public void focusGained(FocusEvent evt) {
//            if (zoekenOrder.getText().equals("Zoeken...")) {
//                zoekenOrder.setText("");
//            }
//        }
//
//        public void focusLost(FocusEvent evt) {
//            if (zoekenOrder.getText().isEmpty()) {
//                zoekenOrder.setText("Zoeken...");
//            }
//
//        }
//    });
//
//    zoekenOrder.getDocument().addDocumentListener(new DocumentListener() {
//        @Override
//        public void insertUpdate(DocumentEvent e) {
//            filterOrder(orders, orderResult);
//        }
//
//        @Override
//        public void removeUpdate(DocumentEvent e) {
//            filterOrder(orders, orderResult);
//        }
//
//        @Override
//        public void changedUpdate(DocumentEvent e) {
//
//            filterOrder(orders, orderResult);
//        }
//
//        public void filterOrder(JList<Order> order, List<Order> orderList) {
//            ArrayList<Order> foundOrders = new ArrayList<>();
//            try {
//                int orderID = Integer.parseInt(zoekenOrder.getText());
//                for (Order foundOrder : orderList) {
//                    if (String.valueOf(foundOrder.getId()).contains(String.valueOf(orderID))) {
//                        foundOrders.add(foundOrder);
//                    }
//                }
//                order.setListData(foundOrders.toArray(new Order[0]));
//
//            } catch (NumberFormatException e) {
//                String orderNaam = zoekenOrder.getText();
//                if (orderNaam.equals("Zoeken...")) {
//                    foundOrders.addAll(orderList);
//                } else if (orderNaam.equals("")) {
//                    foundOrders.addAll(orderList);
//                } else {
//                    order.setListData(foundOrders.toArray(new Order[0]));
//                }
//            }
//            order.setListData(foundOrders.toArray(new Order[0]));
//            if (foundOrders.size() == 0) {
//                zoekenOrder.setBackground(Color.RED);
//            } else {
//                zoekenOrder.setBackground(Color.white);
//            }
//        }
//
//    });
//
//
//    JPanel selectedOrderScreen = new JPanel(new FlowLayout(FlowLayout.LEFT));
//
//    selectedOrderScreen.add(buttonAanpassenPickDatum);
//    selectedOrderScreen.add(buttonzoekenOrder);
//    selectedOrderScreen.add(zoekenOrder);
//
//    this.add(selectedOrderScreen, BorderLayout.SOUTH);
//}
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }
//}
