import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.security.ProtectionDomain;


public class InpakkenDrawScreen extends JPanel {
    public InpakkenDrawScreen() {
        this.setLayout(new GridLayout(2,1));
        this.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.black)); //top,left,bottom,right

        //stukje voor het label van doos 1
        JLabel doos1 = new JLabel("Doos 1:");
        JLabel doos1Max = new JLabel("max 10");
        doos1.setBorder(new EmptyBorder(0,100,0,0));
        doos1.setFont(new Font("Arial", Font.PLAIN, 18));
        doos1Max.setBorder(new EmptyBorder(0,140,0,0));
        doos1Max.setFont(new Font("Arial", Font.PLAIN, 16));

        //stukje voor het label van doos 2
        JLabel doos2 = new JLabel("Doos 2:");
        JLabel doos2Max = new JLabel("max 10");
        doos2.setBorder(new EmptyBorder(0,100,0,0));
        doos2.setFont(new Font("Arial", Font.PLAIN, 18));
        doos2Max.setBorder(new EmptyBorder(0,140,0,0));
        doos2Max.setFont(new Font("Arial", Font.PLAIN, 16));

        this.add(doos1);
        this.add(doos1Max);
        this.add(doos2);
        this.add(doos2Max);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);

        //Doos 1, boven (x, y, width, height)
        g.drawRect((int)(this.getHeight() * 0.3), (int)(this.getWidth() /9.2),(int)(this.getWidth() /3),(int)(this.getHeight() /3));

        //lijn in midden
        g.drawLine(0, (int)(this.getHeight() /2),(int)(this.getWidth()),(int)(this.getHeight() /2));

        //Doos 2, onder
        g.drawRect((int)(this.getHeight() * 0.3),(int)(this.getWidth() /1.8), (int)(this.getWidth() /3),(int)(this.getHeight() /3));

        this.setVisible(true);
    }
}