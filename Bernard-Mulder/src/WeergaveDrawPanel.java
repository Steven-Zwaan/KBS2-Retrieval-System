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
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));

	}
	public void paintComponent(Graphics g){
		// backgrounds
		g.setColor(Color.lightGray);
		g.fillRect(0,0,this.getWidth(),this.getHeight());

		// grid
		g.setColor(Color.gray);
		g.drawLine((int) (this.getHeight() * 0.20),0, (int) (this.getHeight() * 0.20), this.getHeight());
		g.drawLine((int) (this.getHeight() * 0.40),0, (int) (this.getHeight() * 0.40), this.getHeight());
		g.drawLine((int) (this.getHeight() * 0.60),0, (int) (this.getHeight() * 0.60), this.getHeight());
		g.drawLine((int) (this.getHeight() * 0.80),0, (int) (this.getHeight() * 0.80), this.getHeight());

		g.drawLine(0, (int) (this.getHeight() * 0.2), (int) (this.getHeight()),(int) (this.getHeight() * 0.2));
		g.drawLine(0, (int) (this.getHeight() * 0.4), (int) (this.getHeight()),(int) (this.getHeight() * 0.4));
		g.drawLine(0, (int) (this.getHeight() * 0.6), (int) (this.getHeight()),(int) (this.getHeight() * 0.6));
		g.drawLine(0, (int) (this.getHeight() * 0.8), (int) (this.getHeight()),(int) (this.getHeight() * 0.8));
//		g.drawLine(0, 100, this.getWidth(),100);
//		g.drawLine(0, 200, this.getWidth(),200);
//		g.drawLine(0, 300, this.getWidth(),300);
//		g.drawLine(0, 400, this.getWidth(),400);

		g.drawLine((int) (this.getHeight()),0, (int) (this.getHeight()), this.getHeight());

		// Z axis
		g.setColor(new Color(255,100,100));
//		g.setColor(new GradientPaint(0,0, new));
		g.fillRect((int) (getHeight() * 1.1), (int) (getHeight() * 0.6) - zPos, (int) (getHeight() * 0.03),(int) (getHeight() * 0.5));
		g.fillRect((int) (getHeight() * 1.2),(int) (getHeight() * 0.6) - zPos, (int) (getHeight() * 0.03),(int) (getHeight() * 0.5));
		g.setColor(new Color(93, 93, 93));
		g.fillRect((int) (getHeight() * 1.09),(int) (getHeight() * 0.95) - zPos,(int) (getHeight() * 0.15),(int) (getHeight() * 0.5));



		// circle
		g.setColor(new Color(255,100,100));
		g.fillOval(xPos, yPos ,(int) (getHeight() * 0.03),(int) (getHeight() * 0.03));
		g.fillOval(xPos + (int) (getHeight() * 0.08), yPos ,(int) (getHeight() * 0.03),(int) (getHeight() * 0.03));
	}

	public void updatePos(int x, int y, int z){
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
	}
}
