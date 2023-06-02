import java.awt.*;
import javax.swing.JPanel;

public class InpakkenDrawScreen extends JPanel {
    public InpakkenDrawScreen() {
        this.setLayout(new FlowLayout(0));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        // int x= de width van het scherm /4
        // int y = de height deelt door / 4

        //Doos 1, boven
        g.fillRect(((int)(width * 0.35)), (int)(height * 0.2), 3, 230);   // Linker lijn
        g.fillRect((int) (width * 0.50), (int)(height * 0.1), 3, 200);   // rechter lijn
        g.fillRect((int)(height * 0.404), (int) (height *0.1), 284, 3); // onderkant

        //Doos 2, onder
        g.fillRect((int)(width * 0.35), 500, 3, 230);   //
        g.fillRect((int)(width * 0.65), 500, 3, 230);   //
        g.fillRect((int)(height * 0.404), 730, 284, 3); // onderkant

        this.setVisible(true);
    }
}