import javax.swing.*;
import java.awt.*;

public class WeergaveDrawPanel extends JPanel {
//	int[] xPos = {25, 125, 225, 325, 425};
//	int[] yPos = {25, 125, 225, 325, 425};
	int xPos = 0;
	int yPos = 0;
	int zPos = 0;

	public WeergaveDrawPanel() {
		this.setBackground(Color.lightGray);
		this.setPreferredSize(new Dimension(750,500));

	}

	public void paintComponent(Graphics g){
		// backgrounds
		g.setColor(Color.lightGray);
		g.fillRect(0,0,500,500);
		g.setColor(Color.lightGray);
		g.fillRect(500,0,250,500);

		// grid
		g.setColor(Color.gray);
		g.drawLine(100,0, 100,500);
		g.drawLine(200,0, 200,500);
		g.drawLine(300,0, 300,500);
		g.drawLine(400,0, 400,500);
		g.drawLine(0, 100, 500,100);
		g.drawLine(0, 200, 500,200);
		g.drawLine(0, 300, 500,300);
		g.drawLine(0, 400, 500,400);

		g.drawLine(500,0,500,500);

		// Z axis
		g.setColor(new Color(255,100,100));
		g.fillRect(600,300 - zPos, 20,200);
		g.fillRect(650,300 - zPos, 20,200);
		g.setColor(new Color(93, 93, 93));
		g.fillRect(590,480 - zPos,90,500);



		// circle
		g.setColor(Color.RED);
		g.fillOval(xPos, yPos ,50,50);
	}

	public void updatePos(int x, int y, int z){
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
	}
}
