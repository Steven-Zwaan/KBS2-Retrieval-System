import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductStock extends JPanel implements ActionListener { ;
	JLabel naam;
	JSpinner aantal;
	JButton aanpassen;
	String productNaam;
	int productVoorraad;
	int productArtikelnummer;

	public ProductStock(String productNaam, int productVoorraad, int productArtikelnummer){
		// Panel setup
		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 60,10));

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth() - 500;
		int height = (int) screenSize.getHeight();

		this.setMaximumSize(new Dimension(width, height));
		this.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.black));

		// variable setup
		this.productNaam = productNaam;
		this.productVoorraad = productVoorraad;
		this.productArtikelnummer = productArtikelnummer;

		// Screen content
		naam = new JLabel(productNaam);
		this.add(naam);

		JPanel card = new JPanel();

		card.setSize(80,50);
		card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

		card.add(new JLabel("Voorraad: " + productVoorraad));
		card.add(new JLabel("ID: " + String.valueOf(productArtikelnummer)));

		this.add(card);

		// Button
		aantal = new JSpinner(new SpinnerNumberModel(productVoorraad, 0, 1000000000, 1));
		aantal.setSize(60,30);
		this.add(aantal);

		// Button
		aanpassen = new JButton("Aanpassen");
		aanpassen.setActionCommand("Aanpassen");
		this.add(aanpassen);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Aanpassen")){
			if ( (Integer) aantal.getValue() != productVoorraad) {
				this.productVoorraad = (Integer) aantal.getValue();
			}
		}
	}
}
