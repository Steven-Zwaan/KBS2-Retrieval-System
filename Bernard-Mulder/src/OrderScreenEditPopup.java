import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Entities.*;

public class OrderScreenEditPopup extends JDialog implements ActionListener {

    public OrderScreenEditPopup(OrderLine orderLine, String titel) {
        this.setSize(new Dimension(300,100));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(new GridLayout(2,2));
        this.setModal(false);
        this.setTitle(titel);
        setVisible(true);

        JLabel gepickteAantalLabel = new JLabel("Gepickte aantal");
        JLabel totaleAantalLabel = new JLabel("totale aantal");
        JSpinner gepickteAantalSpinner = new JSpinner(new SpinnerNumberModel(, 0, 1000000000, 1));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
