import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockScreenEditPopup extends JDialog implements ActionListener{
	private JSpinner aantal;
	private int currentstock;
	private Product product;
	private boolean isOK = false;

	public StockScreenEditPopup(Product product, String title, int currentstock){
		this.product = product;
		this.currentstock = currentstock;
		this.setSize(new Dimension(300,100));
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setLayout(new GridLayout(2,2));
		setVisible(true);
		this.setModal(false);
		this.setLocationRelativeTo(null);

		this.setTitle(title);

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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Change")){
			product.setStock((int) aantal.getValue());
			this.setVisible(false);
		} else if (e.getActionCommand().equals("Cancel")){
			this.setVisible(false);
		}
	}
}
