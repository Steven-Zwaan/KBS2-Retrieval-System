import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Entities.*;
public class SetPickingPopup extends JDialog implements ActionListener {

    public SetPickingPopup(boolean setPickingDate, int orderID) {
        this.setSize(new Dimension(300,100));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout (new GridLayout(2,1));
        this.setModal(false);
        this.setTitle((setPickingDate) ? "datum aangepast" : "error");
        this.setLocationRelativeTo(null);

        JLabel message = new JLabel();
        message.setText((setPickingDate) ? "De Picking datum is succesvol aangepast" : "De picking datum is niet aangepast");
        JButton ok = new JButton("OK");
        ok.addActionListener(this);
        this.add(message);
        this.add(ok);
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
