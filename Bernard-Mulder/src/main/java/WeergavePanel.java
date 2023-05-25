import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class WeergavePanel extends JPanel {

    JPanel viewPanel;
    JList pickOrderList;
    private static ArrayList<OrderLine> pickOrders = new ArrayList<>();
    JScrollPane orderLineScrollPane;
    JPanel coordinateBar;
    static Order gepickteOrder = null;

    public WeergavePanel() {
        this.setLayout(new BorderLayout());
        viewPanel = new WeergaveDrawPanel();
//        viewPanel.setBackground(new Color(255, 0,0));
        this.add(viewPanel, BorderLayout.CENTER);
        pickOrderList = new JList(pickOrders.toArray());
        orderLineScrollPane = new JScrollPane(pickOrderList);
        this.add(orderLineScrollPane, BorderLayout.EAST);

        coordinateBar = new JPanel();
        coordinateBar.setPreferredSize(new Dimension(this.getWidth(), 70));
        coordinateBar.setLayout(new GridLayout(1, 3));
        this.add(coordinateBar, BorderLayout.SOUTH);

        JLabel xLabel = new JLabel("X-as: ");
        JLabel yLabel = new JLabel("y-as: ");
        JLabel zLabel = new JLabel("Z-as: ");

        coordinateBar.add(xLabel);
        coordinateBar.add(yLabel);
        coordinateBar.add(zLabel);
    }

    public void refreshPanel() {
        if (gepickteOrder != null) {
            pickOrderList.setListData(gepickteOrder.getOrderLines().toArray());
        }
        revalidate();
        repaint();
    }

    public void updatePos(int x, int y) {
        ((WeergaveDrawPanel) viewPanel).updatePos(x,y);
    }

    static void addOrderLine(OrderLine orderLine) {

    }
}
