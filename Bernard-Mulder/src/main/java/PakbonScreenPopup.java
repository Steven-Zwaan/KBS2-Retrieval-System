

import Models.Customer;
import Models.Order;
import Models.OrderLine;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class PakbonScreenPopup extends JDialog implements ActionListener {
    private Order order;
    private Customer customer;
    private int aantal;

    PDDocument document = new PDDocument();
    PDPage page = new PDPage();
    PDImageXObject pdImage = PDImageXObject.createFromFile("C://Users//enteb//OneDrive//Documents//Karolien school//logo.png", document);





    public PakbonScreenPopup(Order order, String title) throws IOException {
        this.setLayout(new GridLayout(3, 1));

        this.order = order;
        this.customer = order.getCustomer();
        this.aantal = order.getOrderLines().toArray().length;

        this.setSize(new Dimension(500,500));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        // setVisible(true);
        this.setTitle(title);
        this.setModal(false);

        // panel
        JPanel gegevens = new JPanel( new GridLayout(4,2));




        // gegevens labels maken
        JLabel naam = new JLabel("Naam: " + customer.getName());
        gegevens.add(naam);

        JLabel adres = new JLabel("Adres: " + customer.getAddressLine2());
        gegevens.add(adres);

        JLabel postcode = new JLabel("Postcode: " + customer.getPostalCode());
        gegevens.add(postcode);

        JLabel woonplaats = new JLabel("Woonplaats: " + customer.getCity());
        gegevens.add(woonplaats);

        JLabel telnr = new JLabel("Telefoonnummer: " + customer.getPhoneNumber());
        gegevens.add(telnr);

        // filler label
        JLabel filler = new JLabel(" ");
        gegevens.add(filler);


        JLabel artikelen = new JLabel("Artikelen: ");
        gegevens.add(artikelen);


        JList orderLineList = new JList(order.getOrderLines().toArray());


        // panel met de buttons/layout/ aanmaak buttons
        JPanel buttons = new JPanel(new GridLayout(1, 2, 30, 30));
        buttons.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.setPreferredSize(new Dimension(5,5));
        buttonCancel.setMargin(new Insets(5,5,10,10));
        buttonCancel.setActionCommand("Cancel");
        buttonCancel.addActionListener(this);
        buttons.add(buttonCancel);


        JButton buttonPrinten = new JButton("Printen");
        buttonPrinten.setActionCommand("Printen");
        buttonPrinten.setPreferredSize(new Dimension(5,5));
        buttonPrinten.addActionListener(this);
        buttons.add(buttonPrinten);






        // alles toevoegen
        this.add(gegevens);
        this.add(orderLineList);
        this.add(buttons);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Printen")){
            this.setVisible(true);
            //Document aanmaken etcc.
            document.addPage( page );
            // font object aanmaken
            PDFont font = PDType1Font.HELVETICA_BOLD;
            // contentstream bevat alle informatie die je wilt laten zien
            PDPageContentStream contentStream = null;
            try {
                PDPageContentStream content = new PDPageContentStream(document, page);

                //begint nieuwe lijn text
                content.beginText();
                PDFont hfont = PDType1Font.HELVETICA_BOLD;
                content.setFont(hfont, 20);
                //set de locatie van de text op de pagina
                content.newLineAtOffset(20,750);
                content.showText("Pakbon van ' " + order.getId() + " ' ");
                content.endText();

                content.drawImage(pdImage, 475, 675, 100, 100);





                content.beginText();
                content.setFont(font, 12);
                content.newLineAtOffset(20, 700);
                content.showText("Klant: ");
                content.endText();



                //begint de text voor de gegevens v.d. klant
                content.beginText();
                content.setFont(font, 10);

                //variabelen aanmaak voor de locatie van de 1e lijn
                int  x=20;
                int y=675;
                content.newLineAtOffset(x, y);

                int yVar1 = 0;
                for (int i = 0; i < 1 ; i++) {

                    //som om elk nieuw gegeven op een lijn y-25 onder de voorgaande lijn te zetten.
                    yVar1 = yVar1 - 25;
                    content.showText("Naam: " + customer.getName());
                    content.newLineAtOffset(0, yVar1);
                    content.showText("Adres: " + customer.getAddressLine2());
                    content.newLineAtOffset(0, yVar1);
                    content.showText("Postcode: " + customer.getPostalCode());
                    content.newLineAtOffset(0, yVar1);
                    content.showText("Woonplaats: " + customer.getCity());
                    content.newLineAtOffset(0, yVar1);
                    content.showText("Telefoonnummer: " + customer.getPhoneNumber());


                    //test lijn
                   // System.out.println(yVar1);
                }

                content.endText();

                content.beginText();
                content.setFont(font, 10);
                content.newLineAtOffset(20, 525);
                content.showText("Hierbij ontvangt u de pakbon voor orderID " + order.getId() + " en onderstaande bestelling.");
                content.endText();

                content.beginText();
                content.setFont(font, 15);
                content.newLineAtOffset(20, 475);
                content.showText("Artikelen: ");
                content.endText();


                //artikelen printen:

                content.moveTo(20, 470);
                content.lineTo(590, 470);
                content.stroke();



                int  xO=20;
                int yO=450;
                content.beginText();
                content.setFont(font, 10);
                content.newLineAtOffset(xO, yO);
                // gaat door de orderlines heen en print ze

                int yVar = 0;
                ArrayList<OrderLine> orderLines = order.getOrderLines();
                for (OrderLine orderLine : orderLines) {
                    yVar = yVar -25;
                    content.showText(orderLine.toString());
                    content.newLineAtOffset(0, yVar);
                    System.out.println(yVar);

                }
                content.endText();
                System.out.println(yVar);
                System.out.println(yO-yVar+5);
                content.moveTo(20, yO+yVar+5);
                content.lineTo(590, yO+yVar+5);
                content.stroke();

                content.moveTo(20, 40);
                content.lineTo(590, 40);
                content.stroke();

                content.beginText();
                content.setFont(font, 8);
                content.newLineAtOffset(20, 30);
                content.showText("Kiezenstraat 40");
                content.endText();
                content.beginText();
                content.newLineAtOffset(20, 20);
                content.showText("9165 HT");
                content.endText();
                content.beginText();
                content.newLineAtOffset(20, 10);
                content.showText("Nederland");
                content.endText();

                content.beginText();
                content.setFont(font, 8);
                content.newLineAtOffset(250, 20);
                content.showText("NerdyGadgets");
                content.endText();

                //contentstream closen
                content.close();
                //document opslaan en closen
                document.save( "Pakbon_" + order.getId() + "_.pdf");
                document.close();
                this.setVisible(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getActionCommand().equals("Cancel")){
            this.setVisible(false);
        }

    }
}