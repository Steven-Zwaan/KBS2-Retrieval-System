import Models.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PickOrderPopupToegevoegd extends JDialog implements ActionListener {

    PickOrderPopup pickOrderPopup;

    public PickOrderPopupToegevoegd() {
        this.setSize(new Dimension(300, 200));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setModal(false);
        this.setTitle("Order toegevoegd");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.add(panel, BorderLayout.CENTER);

        JLabel toegevoegdTekst = new JLabel("De order is toegevoegd");
        JButton klaarButton = new JButton("Klaar");
        klaarButton.addActionListener(this);

        panel.add(toegevoegdTekst);
        panel.add(klaarButton);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Klaar")) {

        }
    }
}
