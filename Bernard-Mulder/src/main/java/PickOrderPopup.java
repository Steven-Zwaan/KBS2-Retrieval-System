import Models.*;
import Route.Bruteforce;
import Route.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PickOrderPopup extends JDialog{

    PickOrder pickOrder;
    Order order;
    int xAs = 0;
    int yAs = 0;
    int weight = 0;

    int[] weightOptions = {2, 5, 7};
    ArrayList<OrderLine> orderLines = new ArrayList<>();
    ArrayList<JSpinner> xPosSpinners = new ArrayList<>();
    ArrayList<JSpinner> yPosSpinners = new ArrayList<>();
    ArrayList<JComboBox> weightComboBox = new ArrayList<>();


    public PickOrderPopup(Order selectedOrder) {
        this.setSize(new Dimension(400,250));
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setModal(false);
        this.setTitle("Pickorder toevoegen");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.add(panel, BorderLayout.CENTER);

        order = selectedOrder;
        orderLines = selectedOrder.getOrderLines();
        String[] weightOptions = {"2", "5", "7"};

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

        JLabel errorLabel = new JLabel("Deze orderlines worden al gepickt", SwingConstants.CENTER);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorLabel.setForeground(Color.red);
        panel.add(errorLabel);
        errorLabel.setVisible(false);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);

        JButton cancelButton = new JButton("Annuleren");
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(cancelButton);

        JButton addButton = new JButton("Toevoegen");
        addButton.addActionListener(e -> {
            ArrayList<PickOrder> pickOrders = new ArrayList<>();
            for (OrderLine orderLine : orderLines) {
                if (WeergavePanel.pickOrders.stream().filter(p -> p.getOrderLine().getId() == orderLine.getId()).findAny().isPresent()) {
                    errorLabel.setVisible(true);
                    return;
                }
            }

            for (int i = 0; i < orderLines.size(); i++) {
                int selectedWeight = Integer.parseInt(weightComboBox.get(i).getSelectedItem().toString());
                PickOrder pickOrder = new PickOrder(orderLines.get(i), (Integer) xPosSpinners.get(i).getValue(), (Integer) yPosSpinners.get(i).getValue(),selectedWeight, selectedOrder.getId());
                pickOrders.add(pickOrder);
                //WeergavePanel.addPickOrder(pickOrder);
               // PackingScreen.addPickOrder(pickOrder);
            }
            allocateItems(pickOrders);
            WeergavePanel.pickOrders.addAll(pickOrders);
            WeergavePanel.pickedOrderNummers.add(selectedOrder.getId());

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
