import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.JPanel;

public class InpakkenDrawScreen extends JPanel {
    public InpakkenDrawScreen() {
        this.setLayout(new FlowLayout(0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);

        //Doos 1, boven
        g.fillRect((int)(this.getWidth() * 0.35), 100, 3, 230);
        g.fillRect((int)(this.getWidth() * 0.65), 100, 3, 230);
        g.fillRect((int)(this.getHeight() * 0.404), 330, 284, 3);

        //Doos 2, onder
        g.fillRect((int)(this.getWidth() * 0.35), 500, 3, 230);
        g.fillRect((int)(this.getWidth() * 0.65), 500, 3, 230);
        g.fillRect((int)(this.getHeight() * 0.404), 730, 284, 3);

        this.setVisible(true);
    }
}