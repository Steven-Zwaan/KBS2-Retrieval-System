import Entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WeergavePanel extends JPanel implements ActionListener {

    JPanel viewPanel;
    JList gepickteOrderList;
    ArrayList<OrderLine> orderLines;
    JScrollPane orderLineScrollPane;
    JPanel coordinateBar;
    static Order gepickteOrder = null;

    public WeergavePanel() {
        this.setLayout(new BorderLayout());

        viewPanel = new JPanel();
        viewPanel.setBackground(new Color(255, 0,0));
        this.add(viewPanel, BorderLayout.CENTER);
        gepickteOrderList = new JList();
        orderLineScrollPane = new JScrollPane(gepickteOrderList);
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
            gepickteOrderList.setListData(gepickteOrder.getOrderLines().toArray());
        }
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
