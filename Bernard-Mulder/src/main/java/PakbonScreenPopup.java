

import Models.Customer;
import Models.Order;
import Models.OrderLine;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
        JPanel gegevens = new JPanel( new GridLayout(3,2));




        // buttons labels maken
        JLabel naam = new JLabel("Naam: " + customer.getName());
        gegevens.add(naam);

        JLabel adres = new JLabel("Adres: " + customer.getAddressLine2());
        gegevens.add(adres);

        JLabel postcode = new JLabel("Postcode: " + customer.getPostalCode());
        gegevens.add(postcode);

        JLabel woonplaats = new JLabel("Woonplaats: " + customer.getCity());
        gegevens.add(woonplaats);

//        JLabel telnr = new JLabel();
//        gegevens.add(telnr);

        JLabel artikelen = new JLabel("Artikelen:");
        gegevens.add(artikelen);


        JList orderLineList = new JList(order.getOrderLines().toArray());


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
            // Create a new font object selecting one of the PDF base fonts
            PDFont font = PDType1Font.HELVETICA_BOLD;
            // Start a new content stream which will "hold" the to be created content
            PDPageContentStream contentStream = null;
            try {
                PDPageContentStream content = new PDPageContentStream(document, page);
                content.beginText();
                PDFont hfont = PDType1Font.HELVETICA_BOLD;
                content.setFont(hfont, 20);
                content.newLineAtOffset(20,750);
                content.showText("Pakbon van ' " + order.getId() + " ' ");
                content.endText();

                content.beginText();
                content.setFont(font, 12);
                int  x=20;
                int y=700;
                content.newLineAtOffset(x, y);

                int yVar1 = 0;
                for (int i = 0; i < 1 ; i++) {
                    yVar1 = yVar1 - 25;
                    content.showText("Naam: " + customer.getName());
                    content.newLineAtOffset(0, yVar1);
                    content.showText("Adres: " + customer.getAddressLine2());
                    content.newLineAtOffset(0, yVar1);
                    content.showText("Postcode: " + customer.getPostalCode());
                    content.newLineAtOffset(0, yVar1);
                    content.showText("Woonplaats: " + customer.getCity());
                    content.newLineAtOffset(0, yVar1);
                    System.out.println(yVar1);
                }

                content.endText();

                content.beginText();
                content.setFont(font, 12);
                content.newLineAtOffset(20, 575);
                content.showText("Artikelen: ");
                content.endText();

                int  xO=20;
                int yO=550;
                content.beginText();
                content.setFont(font, 12);
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

                content.close();
                document.save( "Pakbon_" + order.getId() + "_.pdf");
                document.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getActionCommand().equals("Cancel")){
            this.setVisible(false);
        }

    }
}