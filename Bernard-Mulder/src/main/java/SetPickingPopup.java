import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetPickingPopup extends JDialog {
    //popup voor de confirmatie van het aanpassen van de pickdatum

    public SetPickingPopup(boolean setPickingDate, int orderID) {
        this.setSize(new Dimension(300,100));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout (new GridLayout(2,1));
        this.setModal(false);
        this.setTitle((setPickingDate) ? "datum aangepast" : "error"); //de titel is "datum aangepast" wanneer het succesvol was en "error" wanneer het niet succesvol was
        this.setLocationRelativeTo(null);

        JLabel message = new JLabel();
        message.setText((setPickingDate) ? "De Picking datum is succesvol aangepast" : "De picking datum is niet aangepast");
        JButton ok = new JButton("OK");
        ok.addActionListener(e -> { this.dispose(); }); //wanneer er op ok wordt gedrukt wordt de applicatie gesloten
        this.add(message);
        this.add(ok);
        setVisible(true);

    }
}
