import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Models.*;

public class OrderScreenEditPopup extends JDialog implements ActionListener { //deze klasse maakt een popup waar voor een orderline het gepickte en totale aantal aangepast kunnen worden

    OrderLine orderLine;
    JSpinner gepickteAantalSpinner;
    JSpinner totaleAantalSpinner;
    private OrderScreen frame;

    public OrderScreenEditPopup(OrderLine orderLine, String titel, int voorraad, OrderScreen frame) { //in de constructor krijgt de popup de orderline, de titel, de voorraad van het product en het orderscreen frame mee
        this.frame= frame;
        this.setSize(new Dimension(300,100));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(3,2));
        this.setModal(false);
        this.setTitle(titel);
        this.orderLine = orderLine;
        this.setLocationRelativeTo(null);

        //hier worden alle labels en spinners toegevoegd
        JLabel gepickteAantalLabel = new JLabel("Gepickt aantal");
        JLabel totaleAantalLabel = new JLabel("totaal aantal");
        //de spinners hebben als standaard aantal de gegevens vanuit de database. de gepickteaantalspinner heeft als maximumwaarde de totale quantity, terwijl de totaleaantalspinner bovenop het totale aantal nog maximum de huidige voorraad erbij mag krijgen
        gepickteAantalSpinner = new JSpinner(new SpinnerNumberModel(orderLine.getPickedQuantity(), 0, orderLine.getQuantity(), 1));
        totaleAantalSpinner = new JSpinner(new SpinnerNumberModel(orderLine.getQuantity(), 0, voorraad + orderLine.getQuantity(), 1));
        JButton annuleren = new JButton("Annuleren");
        annuleren.setActionCommand("annuleren");
        annuleren.addActionListener(this);
        JButton ok = new JButton("OK");
        ok.setActionCommand("akkoord");
        ok.addActionListener(this);

        this.add(gepickteAantalLabel);
        this.add(totaleAantalLabel);
        this.add(gepickteAantalSpinner);
        this.add(totaleAantalSpinner);
        this.add(annuleren);
        this.add(ok);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) { //de actionlistener handelt de knoppen annuleren en ok af. bij ok worden de waarden ind e database veranderd en bij annuleren wordt het venste alleen afgesloten
        if (e.getActionCommand() == "akkoord") {
            this.setVisible(false);
            orderLine.setPickedQuantity((Integer) gepickteAantalSpinner.getValue());
            orderLine.setQuantity((Integer) totaleAantalSpinner.getValue());
            frame.refreshPanel();
            //dispose();
        } else if (e.getActionCommand() == "annuleren") {
            this.dispose();
        }
    }
}
