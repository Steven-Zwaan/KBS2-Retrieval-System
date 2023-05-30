import javax.swing.*;
import java.awt.*;

public class InpakkenDrawScreen extends JPanel {
     public InpakkenDrawScreen() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Boxes
        g.setColor(Color.BLACK);
        g.fillRect(495, 100, 3, 300);
        g.fillRect(795, 100, 3, 300);
        g.fillRect(495, 400, 303, 3);

        g.fillRect(495, 500, 3, 300);
        g.fillRect(795, 500, 3, 300);
        g.fillRect(495, 800, 303, 3);

        this.setVisible(true);
    }
}

