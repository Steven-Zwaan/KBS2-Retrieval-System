import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class WeergavePanel extends JPanel {

    WeergaveDrawPanel viewPanel;
    JList pickOrderList;
    static ArrayList<PickOrder> pickOrders = new ArrayList<>();
    static ArrayList<Integer> pickedOrderNummers = new ArrayList<>();
    JScrollPane orderLineScrollPane;
    JPanel coordinateBar;
    JLabel xLabel;
    JLabel yLabel;

    public WeergavePanel() {

        //in deze klasse wordt de paneel gemaakt voor het weergeven van de robot
        this.setLayout(new BorderLayout());
        viewPanel = new WeergaveDrawPanel();
        this.add(viewPanel, BorderLayout.CENTER);

        //de rechter paneel wordt aangemaakt en de lijst van de pickorders wordt getoond
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(375, getHeight()));
        this.add(eastPanel, BorderLayout.EAST);
        pickOrderList = new JList(pickOrders.toArray());
        orderLineScrollPane = new JScrollPane(pickOrderList);
        eastPanel.add(orderLineScrollPane, BorderLayout.CENTER);

        //knop die van de geselecteerde orderline de order verwijdert doormiddel van de removeOrderFromQueue methode
        JButton deleteOrderLine = new JButton("Verwijderen");
        eastPanel.add(deleteOrderLine, BorderLayout.SOUTH);
        deleteOrderLine.addActionListener(e -> {
            try {
                removeOrderFromQueue(pickOrders.get(pickOrderList.getSelectedIndex()).getOrderNummer());
                refreshPanel();
                deleteOrderLine.setBackground(null);
            } catch(IndexOutOfBoundsException i) {
                deleteOrderLine.setBackground(Color.red);
            }
        });

        //hier wordt de balk met coordinaten in het onderste gedeelte van de borderlayout geplaatst met labels voor de x en y positie
        coordinateBar = new JPanel();
        coordinateBar.setPreferredSize(new Dimension(this.getWidth(), 40));
        coordinateBar.setLayout(new FlowLayout());
        this.add(coordinateBar, BorderLayout.SOUTH);

        xLabel = new JLabel("X-as: " + viewPanel.getxPos());
        yLabel = new JLabel("y-as: " + viewPanel.getxPos());

        coordinateBar.add(xLabel);
        coordinateBar.add(yLabel);
    }

    //deze methode herlaadt de listdata van de jlist en ververst daarna het scherm
    public void refreshPanel() {
        pickOrderList.setListData(pickOrders.toArray());
        pickOrderList.revalidate();
        pickOrderList.repaint();
    }

    //met deze methode kan de positie van de robot in de HMI worden geupdate
    public void updatePos(int x, int y) {
        viewPanel.updatePos(x,y);
        xLabel.setText("X-as: " + viewPanel.getxPos());
        yLabel.setText("Y-as: " + viewPanel.getyPos());
        revalidate();
        repaint();
    }

    //deze methode verwijdert alle pickorders van het meegegeven ordernummer
    public static void removeOrderFromQueue(int orderNummer) {
        pickOrders.removeAll(pickOrders.stream().filter(p -> p.getOrderNummer() == orderNummer).collect(Collectors.toList()));
        pickedOrderNummers.remove(pickedOrderNummers.indexOf(orderNummer));
    }
}
