import Models.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderScreen extends JPanel{

    OrderList orderList;
    JButton pakbonPrinten;
    JList orders;
    JTextField zoekenOrder;
    OrderLine selectedOrderLine;
    JButton aanpassenOrderLine;
    Order selectedOrder;
    ArrayList<Order> orderResult;
    JList orderLines;

    public OrderScreen() {

        this.setLayout(new BorderLayout()); // zet de layout van het orderscherm naar een borderlayout

        orderList = new OrderList(); //Er wordt een nieuwe instantie van orderlist aangemaakt en de orders van de database worden in een arraylist van de orderlist gestopt
        orderList.getOrdersFromDatabase();

        orderResult = orderList.getOrderList(); //om de zoekfunctie te laten werken worden de in de lijst vanuit orderResult geladen. Deze krijgt aan het begin de lijst van orderlist mee
        orders = new JList(orderResult.toArray()); // de arraylist wordt in een jlist gestopt. Deze zal links in het orderscherm alle orders laten zien
        JScrollPane scrollPaneOrderScreen = new JScrollPane(orders); //de ljist wordt in een scrollpane gezet om scrollen mogelijk te maken

        scrollPaneOrderScreen.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //de scrollpane wordt ingesteld voor gebruik
        scrollPaneOrderScreen.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneOrderScreen.getVerticalScrollBar().setUnitIncrement(16);
        scrollPaneOrderScreen.setSize(new Dimension(this.getHeight(), 1500));

        this.add(scrollPaneOrderScreen, BorderLayout.WEST); //de scrollpane wordt links in de borderlayout toegevoegd

        //setup orderinfo scherm
        JPanel ProductView = new JPanel(); //in productview zal een lijst komen te staan met alle orderlines met een jlabel met het ordernummer erboven.
        ProductView.setLayout(new BoxLayout(ProductView, BoxLayout.Y_AXIS)); //hiervoor wordt een boxlayout gebruikt
        this.add(ProductView, BorderLayout.CENTER);

        selectedOrder = orderList.getOrderList().get(0); //om errors te voorkomen wordt voor het object selectedorder eerst het eerste order in de lijst gebruikt als plaatsvervanger om nullpointerexceptions te voorkomen
        JLabel OrderNummer = new JLabel();
        ProductView.add(OrderNummer);

        orderLines = new JList(selectedOrder.getOrderLines().toArray()); //van de geselecteerde order worden de orderlines opgehaald, naar een array omgezet en in een jlist gestopt
        JScrollPane scrollpaneOrderLines = new JScrollPane(orderLines);
        ProductView.add(scrollpaneOrderLines);

        ProductView.setVisible(false); //bij het starten van de code wordt productview onzichtbaar gemaakt, omdat de orderdetails pas op het scherm mogen komen wanneer er op een order wordt geklikt

        JPanel adressLines = new JPanel(); //in de adresslines panel zullen de orderdetails vermeldt worden. hiervoor wordt een gridlayout gebruikt
        adressLines.setLayout(new GridLayout(15, 1));

        JPanel adressLinesPanel = new JPanel(); //de adresslines worden in een aparte panel met een borderlayout geplaatst met een borderlayout, zodat er onrderin de paneel (borderlayout.SOUTH) een balk met de knoppen kan komen
        adressLinesPanel.setLayout(new BorderLayout());
        adressLinesPanel.add(adressLines, BorderLayout.CENTER);
        adressLinesPanel.setPreferredSize(new Dimension(350, getHeight())); //er wordt een vaste grootte meegegeven voor de breedte zodat de paneel breed geneog is voor de tekst

        this.add(adressLinesPanel, BorderLayout.EAST);

        JLabel naam = new JLabel(); //hier owrdne alle jlabels met de ordergegevens aangemaakt. Hier komen nog geen waarden in, omdat deze pas gevuld moeten worden bij het selecteren van een order
        adressLines.add(naam);

        JLabel adres = new JLabel();
        adressLines.add(adres);

        JLabel postcode = new JLabel();
        adressLines.add(postcode);

        JLabel woonplaats = new JLabel();
        adressLines.add(woonplaats);

        JLabel telnr = new JLabel();
        adressLines.add(telnr);

        JPanel adressLinesKnoppen = new JPanel(); //dit is de balk onderaan de ordergegevens met alle knoppen
        adressLinesKnoppen.setLayout(new GridLayout(1, 2));
        adressLinesKnoppen.setPreferredSize(new Dimension(adressLinesPanel.getWidth(), 25));
        adressLinesPanel.add(adressLinesKnoppen, BorderLayout.SOUTH);

        JButton pickOrder = new JButton("Order Picken"); //deze knop zorgt voor het popup menu om een pickorder mee toe te voegen
        adressLinesKnoppen.add(pickOrder);
        pickOrder.addActionListener(e -> { //deze actionlistener maakt een nieuwe instantie van de pickorderpopup klasse aan wanneer er op de knop wordt gedrukt
            PickOrderPopup popup = new PickOrderPopup(selectedOrder); // bij het openen van de popup wordt de geselecteerde order meegegeven, zodat de popup de orderlines kan ophalen
        });

        aanpassenOrderLine = new JButton("Product aanpassen"); // hier wordt een knop aangemaakt voor het aanpasse nvan een orderline
        aanpassenOrderLine.addActionListener(e -> { //in de actionlistener wordt een try catch gebruikt voor het geval er geen orderline geselecteerd is om aan te passen
            try { //indien dit er wel is wordt er een popup gemaakt waaraan de geselecteerde orderline, de titel, de voorraad van het product en het orderscreen frame voor het refreshen van de paneel
                aanpassenOrderLine.setBackground(null);
                int voorraad = selectedOrderLine.getProduct().getStock();
                OrderScreenEditPopup popup = new OrderScreenEditPopup(selectedOrderLine, "Change order " + selectedOrder.getId() + ", orderline " + selectedOrderLine.getId(), voorraad, this);
                this.orderLines.revalidate();
            } catch (NullPointerException npe) { //wanneer er geen orderline geselecteerd is wordt de knop rood gemaakt en opent de popup niet
                aanpassenOrderLine.setBackground(new Color(255, 0, 0));
            }
        });
        adressLinesKnoppen.add(aanpassenOrderLine);

        pakbonPrinten = new JButton("Pakbon"); //hier wordt de knop aangemaakt voor het printen van de pakbon. Deze opent een popup die de geselecteerde order en de titel meekrijgt
        pakbonPrinten.addActionListener(e -> {
            try {
                PakbonScreenPopup popup = new PakbonScreenPopup(selectedOrder, "Pakbon van ' " + selectedOrder.getId() + " ' ");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.revalidate();
        });
        adressLinesKnoppen.add(pakbonPrinten);


        adressLinesPanel.setVisible(false); //ook adresslinespanel wordt op onzichtbaar gezet, omdat deze pas zichtbaar mag worden wanneer er een order wordt geselecteerd

        orders.addListSelectionListener(e -> { //wanneer er een order wordt geselecteerd, krijgt selectedorder de order mee die er op dat moment wordt geselecteerd
            int selectedIndex = orders.getSelectedIndex();
            if(selectedIndex != 0) {
                try {
                    selectedOrder = orderList.getOrderList().get(selectedIndex);
                } catch (IndexOutOfBoundsException exception){
                    selectedOrder = (Order) orders.getModel().getElementAt(0);
                }
            } else {
                selectedOrder = (Order) orders.getModel().getElementAt(0);
            }

            orderLines.setListData(selectedOrder.getOrderLines().toArray()); //daarnaast krijgen alle labels van de orderdetails de jusite waarden mee
            OrderNummer.setText("Ordernummer: " + selectedOrder.getId());
            naam.setText("Naam: " + selectedOrder.getCustomer().getName());
            adres.setText("Adres: " + selectedOrder.getCustomer().getAddressLine2());
            postcode.setText("Postcode: " + selectedOrder.getCustomer().getPostalCode());
            woonplaats.setText("Woonplaats: " + selectedOrder.getCustomer().getCity());
            telnr.setText("Telefoonnummer: " + selectedOrder.getCustomer().getPhoneNumber());

            ProductView.setVisible(true); //vervolgens worden beide panelen zichtbaar gemaakt en opnieuw geladen, zodat deze zichtbaar worden
            adressLinesPanel.setVisible(true);
            revalidate();
            repaint();
        });

        orderLines.addListSelectionListener(e -> selectedOrderLine = selectedOrder.getOrderLines().get(orderLines.getSelectedIndex())); // wanneer er een orderlines wordt geselecteerd, krijgt de selectedorderline de orderline mee die er op dat moment wordt geselecteerd

        //setup zoekbalk
        JButton buttonAanpassenPickDatum = new JButton("Pickdatum aanpassen"); //hier wordt een knop aangemaakt voor het veranderen van de pickdatum
        buttonAanpassenPickDatum.addActionListener(e -> { //met de methode setpickingpopup wordt de pickdatum aangepast. Deze returnt true als dit is gelukt of false als dit niet het geval is
            SetPickingPopup popup = new SetPickingPopup(selectedOrder.setPickingCompletedWhen(), selectedOrder.getId()); //er wordt een nieuwe popup gemaakt die een confirmatie of error geeft na het veranderen van de pickdatum
        });

        zoekenOrder = new JTextField(10);
        zoekenOrder.setText("Zoeken...");
        zoekenOrder.addFocusListener(new java.awt.event.FocusAdapter() {
            // kijkt of de textfield is aan geklikt/ eruit is geklikt en verandert de text
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
            //
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
                //array waarin de orders die je zoekt worden in opgeslagen
                ArrayList<Order> foundOrders = new ArrayList<>();
                try {
                    int orderID = Integer.parseInt(zoekenOrder.getText());
                    for (Order foundOrder : orderList) {
                        if (String.valueOf(foundOrder.getId()).equals(String.valueOf(orderID))) {
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
                    //order niet aanwezig in de database maakt de textfield rood
                    zoekenOrder.setBackground(Color.RED);
                } else {
                    zoekenOrder.setBackground(Color.white);
                }
            }

        });


        JPanel selectedOrderScreen = new JPanel(new FlowLayout(FlowLayout.LEFT));

        selectedOrderScreen.add(buttonAanpassenPickDatum);
        selectedOrderScreen.add(zoekenOrder);

        this.add(selectedOrderScreen, BorderLayout.SOUTH);

    }

    public void refreshPanel() {
        orderLines.setListData(selectedOrder.getOrderLines().toArray());
        orderLines.revalidate();
        orderLines.repaint();
    }

}

