import Models.Order;
import Models.OrderLine;
import Models.OrderList;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PackingScreen extends JPanel implements ActionListener {

    JPanel viewPanel;
    JList gepickteOrderList;
    JScrollPane orderLineScrollPane;
    JScrollPane boxScrollPane;
    BinPackingBoxPanel binPackingBoxPanel;

    public PackingScreen() {
        this.setLayout(new BorderLayout());

        viewPanel = new InpakkenDrawScreen();
        this.add(viewPanel, BorderLayout.CENTER);
        viewPanel.setPreferredSize(new Dimension(300, 500));

        gepickteOrderList = new JList();
        orderLineScrollPane = new JScrollPane(gepickteOrderList);
        this.add(orderLineScrollPane, BorderLayout.WEST);
        gepickteOrderList.setPreferredSize(new Dimension(400,780));

        BinPackingBoxPanel binPackingBoxPanel1 = new BinPackingBoxPanel();

        boxScrollPane = new JScrollPane(binPackingBoxPanel1);
        binPackingBoxPanel1.setPreferredSize(new Dimension(400, 800));
        this.add(boxScrollPane, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
