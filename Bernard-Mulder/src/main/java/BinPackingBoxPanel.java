//
//import javax.swing.*;
//import java.awt.*;
//import java.util.ArrayList;
//
//public class BinPackingBoxPanel extends JPanel {
//
//    static ArrayList<PickOrder> pickOrders = new ArrayList<>();
//
//    public BinPackingBoxPanel(){
//        this.setLayout(new GridLayout(2,1));
//
//        JScrollPane jScrollPane1 = new JScrollPane();
//        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//        JPanel jPanel = new JPanel();
//        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
//        jPanel.add(new JLabel("Doos 1"));
//        jPanel.add(jScrollPane1);
//
//        JScrollPane jScrollPane2 = new JScrollPane();
//        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//        JPanel jPanel2 = new JPanel();
//        jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.Y_AXIS));
//        jPanel2.add(new JLabel("Doos 2"));
//        jPanel2.add(jScrollPane2);
//
//        this.add(jPanel);
//        this.add(jPanel2);
//    }
//
//}