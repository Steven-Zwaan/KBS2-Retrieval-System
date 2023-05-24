import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class WeergavePanel extends JPanel implements ActionListener {

    JPanel viewPanel;
    JList gepickteOrderList;
    ArrayList<OrderLine> orderLines;
    JScrollPane orderLineScrollPane;
    JPanel coordinateBar;
    static Order gepickteOrder = null;

    int x = 0;
    int y = 0;
    int z = 0;

    public WeergavePanel() {
        this.setLayout(new BorderLayout());

        viewPanel = new WeergaveDrawPanel();
//        viewPanel.setBackground(new Color(255, 0,0));
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

        JButton testbutton = new JButton("test");
        testbutton.setActionCommand("test");
        testbutton.addActionListener(this);

        coordinateBar.add(xLabel);
        coordinateBar.add(yLabel);
        coordinateBar.add(zLabel);
        coordinateBar.add(testbutton);


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
        if (e.getActionCommand().equals("test")){
            while (!(x == 150 &&  y == 200 && z == 300)){
                if (x < 150) {
                    x++;
                } else if (x > 150){
                    x--;
                }

                if (y < 200) {
                    y++;
                } else if (y > 200){
                    y--;
                }

                if (z < 300) {
                    z++;
                } else if (z > 300){
                    z--;
                }

                ((WeergaveDrawPanel) viewPanel).updatePos(x, y, z);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        viewPanel.repaint();
                    }
                });

                System.out.print(x + " ");
                System.out.print(y + " ");
                System.out.println(y);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
