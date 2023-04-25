import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Order extends JPanel { ;
	JLabel naam;
	JSpinner aantal;
	InfoCard card;
	JButton aanpassen;

	String productNaam;
	int productVoorraad;
	int productArtikelnummer;

	public Order(String productNaam, int productVoorraad, int productArtikelnummer){
		// Panel setup
		this.setSize(600,50);
		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 60,10));
		this.setMaximumSize(new Dimension(600, 50));
		this.setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.black));

		// variable setup
		this.productNaam = productNaam;
		this.productVoorraad = productVoorraad;
		this.productArtikelnummer = productArtikelnummer;

		// Screen content
		naam = new JLabel(productNaam);
//		naam.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.add(naam);

		card = new InfoCard(productVoorraad, productArtikelnummer);
		this.add(card);

		// Button
		aantal = new JSpinner(new SpinnerNumberModel(productVoorraad, 0, 100, 1));
		aantal.setSize(60,30);
		this.add(aantal);

		// Button
		aanpassen = new JButton("Aanpassen");
		this.add(aanpassen);

		setVisible(true);


	}
}
