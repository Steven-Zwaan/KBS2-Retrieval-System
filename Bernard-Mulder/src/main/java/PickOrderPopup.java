import Models.*;
import Route.Bruteforce;
import Route.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PickOrderPopup extends JDialog{ //deze popup dient ervoor orders toe te voegen aan de wachtrij

    Order order;
    ArrayList<OrderLine> orderLines = new ArrayList<>();
    ArrayList<JSpinner> xPosSpinners = new ArrayList<>();
    ArrayList<JSpinner> yPosSpinners = new ArrayList<>();
    ArrayList<JComboBox> weightComboBox = new ArrayList<>();


    public PickOrderPopup(Order selectedOrder) { //het geselecteerde order wordt bij het aanmaken van een popup meegegeven voor het verkrijgen van de orderlines
        this.setSize(new Dimension(400,250));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setModal(false);
        this.setTitle("Pickorder toevoegen");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        //een nieuwe jpanel wordt aangemaakt met een boxlayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.add(panel, BorderLayout.CENTER);

        //de meegegeven order en de bijbehorende orderlines worden in een locale variabele gestopt
        order = selectedOrder;
        orderLines = selectedOrder.getOrderLines();

        //de opties voor het gewicht van een product
        String[] weightOptions = {"2", "5", "7"};

        //deze foreach voegt een rij toe voor het instellen van de locatie en het gewicht voor elke index van de arraylist met orderlines
        //de spinners en comboboxen zitten allemaal in een aparte arraylist, zodat via de index voor elke orderline de ingevulde waarden opgehaald kunnen worden
        for (OrderLine orderLine : orderLines) {
            JPanel orderLinePanel = new JPanel();
            orderLinePanel.setLayout(new FlowLayout());
            panel.add(orderLinePanel);

            orderLinePanel.add(new JLabel("Pickorder ID: " + orderLine.getId()));
            orderLinePanel.add(new JLabel("X-as: "));
            xPosSpinners.add(new JSpinner(new SpinnerNumberModel(0, 0, 4, 1)));
            orderLinePanel.add(xPosSpinners.get(xPosSpinners.size()-1));
            orderLinePanel.add(new JLabel("Y-as: "));
            yPosSpinners.add(new JSpinner(new SpinnerNumberModel(0, 0, 4, 1)));
            orderLinePanel.add(yPosSpinners.get(yPosSpinners.size()-1));
            orderLinePanel.add(new JLabel("Gewicht: "));
            weightComboBox.add(new JComboBox(weightOptions));
            orderLinePanel.add(weightComboBox.get(weightComboBox.size()-1));
        }

        //er wordt een error laten zien wanneer er een order geselecteerd is die al gepickt wordt
        JLabel errorLabel = new JLabel("Deze orderlines worden al gepickt", SwingConstants.CENTER);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setForeground(Color.red);
        panel.add(errorLabel);
        errorLabel.setVisible(false);

        //paneel met de knoppen
        JPanel buttonPanel = new JPanel(new FlowLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);

        //afsluiten van de popup
        JButton cancelButton = new JButton("Annuleren");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        //toevoegen van de pickorders
        JButton addButton = new JButton("Toevoegen");
        addButton.addActionListener(e -> {
            //er word teen tijdelijke arraylist toegevoegd voor het opslaan van de pickorders
            ArrayList<PickOrder> pickOrders = new ArrayList<>();
            //deze foreach checkt of een van de orderlines al in een pickorder is gezet en laat de error zien als dat het geval is
            for (OrderLine orderLine : orderLines) {
                if (WeergavePanel.pickOrders.stream().anyMatch(p -> p.getOrderLine().getId() == orderLine.getId())) {
                    errorLabel.setVisible(true);
                    return;
                }
            }
            //deze foreach voegt voor elke orderline een pickroder toe aan de tijdelijke array met de waarden uit de spinners en combobox
            for (int i = 0; i < orderLines.size(); i++) {
                int selectedWeight = Integer.parseInt(weightComboBox.get(i).getSelectedItem().toString());
                PickOrder pickOrder = new PickOrder(orderLines.get(i), (Integer) xPosSpinners.get(i).getValue(), (Integer) yPosSpinners.get(i).getValue(),selectedWeight, selectedOrder.getId());
                pickOrders.add(pickOrder);
            }
            //hier wordt het bin packing algoritme toegepast om de pickorders in te delen in dozen
            allocateItems(pickOrders);
            //de pickorders worden toegevoegd aan de lijst met pickorders en het ordernummer wordt toegevoegd aan de wachtrij
            WeergavePanel.pickOrders.addAll(pickOrders);
            WeergavePanel.pickedOrderNummers.add(selectedOrder.getId());

            //hier wordt het travelling salesman algoritme toegepast
            if (selectedOrder.getOrderLines().size() >= 4) {

                Point Startpoint = new Point(0,0);
                ArrayList<Point> points = new ArrayList<>();
                for (int i = 0; i < selectedOrder.getOrderLines().size(); i++){
                    points.add(new Point((Integer) xPosSpinners.get(i).getValue(), (Integer) yPosSpinners.get(i).getValue()));
                }
                Point Endpoint = new Point(5,5);

                for (int i = 0; i <= (points.size() / 3); i++){
                    if ((points.size() / 3) == 1){

                    }
                    Bruteforce bruteforce = new Bruteforce(Startpoint, points.get(0), points.get(1), points.get(2), Endpoint);
                    points.remove(0);
                    points.remove(1);
                    points.remove(2);


                    System.out.println(bruteforce.calc());
                }

            } else if (selectedOrder.getOrderLines().size() == 3){
                Point Startpoint = new Point(0,0);
                Point p1 = new Point((Integer) xPosSpinners.get(0).getValue(), (Integer) yPosSpinners.get(0).getValue());
                Point p2 = new Point((Integer) xPosSpinners.get(1).getValue(), (Integer) yPosSpinners.get(1).getValue());
                Point p3 = new Point((Integer) xPosSpinners.get(2).getValue(), (Integer) yPosSpinners.get(2).getValue());
                Point Endpoint = new Point(5,5);
                Bruteforce bruteforce = new Bruteforce(Startpoint, p1 ,p2, p3, Endpoint);

                System.out.println(bruteforce.calc());

            } else if (selectedOrder.getOrderLines().size() == 2) {
                Point Startpoint = new Point(0,0);
                Point p1 = new Point((Integer) xPosSpinners.get(0).getValue(), (Integer) yPosSpinners.get(0).getValue());
                Point p2 = new Point((Integer) xPosSpinners.get(1).getValue(), (Integer) yPosSpinners.get(1).getValue());
                Point Endpoint = new Point(5,5);
                Bruteforce bruteforce = new Bruteforce(Startpoint, p1 ,p2, Endpoint);

                System.out.println(bruteforce.calc());
            } else if (selectedOrder.getOrderLines().size() == 1) {
                System.out.println(new Point((Integer) xPosSpinners.get(0).getValue(), (Integer) yPosSpinners.get(0).getValue()));
            }

            dispose();
        });

        buttonPanel.add(addButton);

        setVisible(true);
    }

    //deze methode deelt de pickorders in dozen op doormiddel van het best-fit algoritme
    public void allocateItems(ArrayList<PickOrder> pickOrders) {
        Doos doos1 = new Doos();
        Doos doos2 = new Doos();
        for (PickOrder pickOrder : pickOrders) {
            boolean isAllocated = false;

            if (doos1.addPickOrder(pickOrder)) {
                isAllocated = true;
                pickOrder.setDoos(1);
            } else if (doos2.addPickOrder(pickOrder)) {
                isAllocated = true;
                pickOrder.setDoos(2);
            } else {
                Doos besteDoos = null;
                int minSizeDifference = Integer.MAX_VALUE;

                int sizeDifference = doos1.getGewicht() - pickOrder.getWeight();
                if (sizeDifference >= 0 && sizeDifference < minSizeDifference) {
                    minSizeDifference = sizeDifference;
                    pickOrder.setDoos(1);
                }

                sizeDifference = doos2.getGewicht() - pickOrder.getWeight();
                if (sizeDifference >= 0 && sizeDifference < minSizeDifference) {
                    minSizeDifference = sizeDifference;
                    pickOrder.setDoos(2);
                }
            }

            if (!isAllocated) {
                System.out.println("Unable to allocate pickorder " + pickOrder);
            }
        }
    }
}
