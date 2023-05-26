import Models.*;

import javax.sound.sampled.BooleanControl;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PickOrderPopup extends JDialog{

    PickOrder pickOrder;
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

        JPanel buttonPanel = new JPanel(new FlowLayout());
        this.add(buttonPanel, BorderLayout.SOUTH);

        JButton cancelButton = new JButton("Annuleren");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        buttonPanel.add(cancelButton);

        JButton addButton = new JButton("Toevoegen");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < orderLines.size(); i++) {
                    int selectedWeight = Integer.parseInt(weightComboBox.get(i).getSelectedItem().toString());
                    PickOrder pickOrder = new PickOrder(orderLines.get(i), (Integer) xPosSpinners.get(i).getValue(), (Integer) yPosSpinners.get(i).getValue(),selectedWeight);
                    WeergavePanel.addPickOrder(pickOrder);
                    System.out.println(WeergavePanel.getPickOrders().toString());
                }
                dispose();
            }
        });

        buttonPanel.add(addButton);


        setVisible(true);
    }
}
