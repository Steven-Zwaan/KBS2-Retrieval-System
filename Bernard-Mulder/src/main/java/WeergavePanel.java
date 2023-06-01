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
        this.setLayout(new BorderLayout());
        viewPanel = new WeergaveDrawPanel();
//        viewPanel.setBackground(new Color(255, 0,0));
        this.add(viewPanel, BorderLayout.CENTER);

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(375, getHeight()));
        this.add(eastPanel, BorderLayout.EAST);

        pickOrderList = new JList(pickOrders.toArray());
        orderLineScrollPane = new JScrollPane(pickOrderList);
        eastPanel.add(orderLineScrollPane, BorderLayout.CENTER);

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

        coordinateBar = new JPanel();
        coordinateBar.setPreferredSize(new Dimension(this.getWidth(), 40));
        coordinateBar.setLayout(new FlowLayout());
        this.add(coordinateBar, BorderLayout.SOUTH);

        xLabel = new JLabel("X-as: " + viewPanel.getxPos());
        yLabel = new JLabel("y-as: " + viewPanel.getxPos());

        coordinateBar.add(xLabel);
        coordinateBar.add(yLabel);
    }

    public void refreshPanel() {
        pickOrderList.setListData(pickOrders.toArray());
        pickOrderList.revalidate();
        pickOrderList.repaint();
    }

    public void updatePos(int x, int y) {
        viewPanel.updatePos(x,y);
        xLabel.setText("X-as: " + viewPanel.getxPos());
        yLabel.setText("Y-as: " + viewPanel.getyPos());
        revalidate();
        repaint();
    }

    public static void removeOrderFromQueue(int orderNummer) {
        pickOrders.removeAll(pickOrders.stream().filter(p -> p.getOrderNummer() == orderNummer).collect(Collectors.toList()));
        pickedOrderNummers.remove(pickedOrderNummers.indexOf(orderNummer));
    }
}
