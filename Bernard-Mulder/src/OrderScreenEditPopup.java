import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Models.*;

public class OrderScreenEditPopup extends JDialog implements ActionListener {

    OrderLine orderLine;
    JSpinner gepickteAantalSpinner;
    JSpinner totaleAantalSpinner;

    public OrderScreenEditPopup(OrderLine orderLine, String titel, int voorraad) {
        this.setSize(new Dimension(300,100));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(new GridLayout(3,2));
        this.setModal(false);
        this.setTitle(titel);
        this.orderLine = orderLine;
        this.setLocationRelativeTo(null);

        JLabel gepickteAantalLabel = new JLabel("Gepickte aantal");
        JLabel totaleAantalLabel = new JLabel("totale aantal");
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
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "akkoord") {
            orderLine.setPickedQuantity((Integer) gepickteAantalSpinner.getValue());
            orderLine.setQuantity((Integer) totaleAantalSpinner.getValue());
            this.dispose();
        } else if (e.getActionCommand() == "annuleren") {
            this.dispose();
        }
    }
}
