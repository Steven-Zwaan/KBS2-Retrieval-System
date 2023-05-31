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
import java.util.stream.Collectors;

public class PackingScreen extends JPanel implements ActionListener {

    JPanel viewpanel;
    JList gepickteOrderList;
    JList pickOrderList;
    JList boxList;


    JScrollPane orderLineScrollPane;
    JScrollPane boxScorllPane;
    BinPackingBoxPanel BinPackingBoxPanel;

    public PackingScreen() {
        this.setLayout(new BorderLayout());

        viewpanel = new InpakkenDrawScreen();
        this.add(viewpanel, BorderLayout.CENTER);

        //print de order in de Linker Panel
        pickOrderList = new JList(WeergavePanel.pickOrders.toArray());
        orderLineScrollPane = new JScrollPane(pickOrderList);
        this.add(orderLineScrollPane, BorderLayout.WEST);

        BinPackingBoxPanel BinPackingBoxPanel1 = new BinPackingBoxPanel();

        boxScorllPane = new JScrollPane(BinPackingBoxPanel1);
        BinPackingBoxPanel1.setPreferredSize(new Dimension(400, 800));
        this.add(boxScorllPane, BorderLayout.EAST);
    }

    public void refreshPanel(){
        pickOrderList.setListData(WeergavePanel.pickOrders.toArray());
        pickOrderList.revalidate();
        pickOrderList.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

