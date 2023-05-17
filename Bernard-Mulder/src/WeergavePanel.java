import Entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WeergavePanel extends JPanel implements ActionListener {

    JPanel viewPanel;
    JList gepickteOrder;
    ArrayList<OrderLine> orderLines;
    JScrollPane orderLineScrollPane;
    JPanel coordinateBar;

    public WeergavePanel() {
        this.setLayout(new BorderLayout());

        viewPanel = new JPanel();
        viewPanel.setBackground(new Color(255, 0,0));
        this.add(viewPanel, BorderLayout.CENTER);

        gepickteOrder = new JList();
        orderLineScrollPane = new JScrollPane(gepickteOrder);
        this.add(orderLineScrollPane, BorderLayout.EAST);

        coordinateBar = new JPanel();
        coordinateBar.setPreferredSize(new Dimension(this.getWidth(), 100));
        this.add(coordinateBar, BorderLayout.SOUTH);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
