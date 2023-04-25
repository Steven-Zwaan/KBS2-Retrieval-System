import javax.swing.*;

class InfoCard extends JPanel {
	JLabel Voorraad;
	JLabel Artikelnummer;
	int productVoorraad;
	String productArtikelnummer;


	public InfoCard(int productVoorraad, int productArtikelnummer){
		this.productVoorraad = productVoorraad;
		this.productArtikelnummer =  Integer.toString(productArtikelnummer);

		this.setSize(80,50);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		Voorraad = new JLabel("Voorraad: " + productVoorraad);
		Artikelnummer = new JLabel(String.valueOf(productArtikelnummer));

		this.add(Voorraad);
		this.add(Artikelnummer);
	}
}
