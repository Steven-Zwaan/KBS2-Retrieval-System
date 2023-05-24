import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockScreenEditPopup extends JDialog implements ActionListener{
	private JSpinner aantal;
	private int currentstock;
	private Product product;
	private JPanel frame;

	public StockScreenEditPopup(Product product, String title, int currentstock, JPanel frame){
//		super(frame, true);
		this.product = product;
		this.currentstock = currentstock;
		this.frame = frame;
		this.setSize(new Dimension(300,100));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new GridLayout(2,2));
		setVisible(true);
		this.setLocationRelativeTo(null);

		setTitle(title);

		JLabel currentStock = new JLabel("Stock:");
		aantal = new JSpinner(new SpinnerNumberModel(currentstock, 0, 1000000000, 1));
		aantal.setSize(60,30);

		JButton buttonAccept = new JButton("Change");
		buttonAccept.setActionCommand("Change");
		buttonAccept.addActionListener(this);


		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.setActionCommand("Cancel");
		buttonCancel.addActionListener(this);






		this.add(currentStock);
		this.add(aantal);
		this.add(buttonAccept);
		this.add(buttonCancel);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Change")){
			product.setStock((int) aantal.getValue());
			frame.repaint();
			frame.revalidate();
			this.setVisible(false);
		} else if (e.getActionCommand().equals("Cancel")){
			this.setVisible(false);
		}
	}
}
