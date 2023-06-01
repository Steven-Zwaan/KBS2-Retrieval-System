import Models.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PackingScreen extends JPanel implements ActionListener {

    JPanel viewPanel;
   // JList gepickteOrderList;  ?
    JList pickOrderList;  //bevat de orders die gepicked worden
    JList doos1List;
    JList doos2List;

  //  JList boxList; ?

    JScrollPane orderLineScrollPane;   //ScrollPane voor de Jlist pickOrderList
    JScrollPane doos1ScrollPane;
    JScrollPane doos2ScrollPane;
   // JScrollPane boxScrollPane;  ??

    public PackingScreen() {
        this.setLayout(new BorderLayout());

        viewPanel = new InpakkenDrawScreen();
        this.add(viewPanel, BorderLayout.CENTER);

        //print de order in de Linker Panel
        pickOrderList = new JList(getSelectedOrderLines().toArray());
        orderLineScrollPane = new JScrollPane(pickOrderList);
        this.add(orderLineScrollPane, BorderLayout.WEST);

        //Panel voor de visuele weergave van de 2 dozen aan de rechter kant
        JPanel dozenWeergave = new JPanel();
        dozenWeergave.setLayout(new BoxLayout(dozenWeergave, BoxLayout.Y_AXIS));
        dozenWeergave.setPreferredSize(new Dimension(400, this.getHeight()));
        this.add(dozenWeergave, BorderLayout.EAST);

        //panel voor doos 1
        dozenWeergave.add(new JLabel("Doos 1"));
        doos1List = new JList(getSelectedOrderLines().stream().filter(p -> p.getDoos() == 1).collect(Collectors.toList()).toArray());
        doos1ScrollPane = new JScrollPane(doos1List);
        dozenWeergave.add(doos1ScrollPane);

        //panel voor doos 2
        dozenWeergave.add(new JLabel("Doos 2"));
        doos2List = new JList(getSelectedOrderLines().stream().filter(p -> p.getDoos() == 2).collect(Collectors.toList()).toArray());
        doos2ScrollPane = new JScrollPane(doos2List);
        dozenWeergave.add(doos2ScrollPane);

    }

    public void refreshPanel(){
        pickOrderList.setListData(getSelectedOrderLines().toArray());
        doos1List.setListData(getSelectedOrderLines().stream().filter(p -> p.getDoos() == 1).collect(Collectors.toList()).toArray());
        doos2List.setListData(getSelectedOrderLines().stream().filter(p -> p.getDoos() == 2).collect(Collectors.toList()).toArray());
        pickOrderList.revalidate();
        pickOrderList.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public ArrayList<PickOrder> getSelectedOrderLines() {
        return new ArrayList(WeergavePanel.pickOrders.stream().filter(p->p.getOrderNummer() == WeergavePanel.pickedOrderNummers.get(0)).collect(Collectors.toList()));
    }


//    static void addOrderLineDoos(Models.PickOrder ) {
//        .add(pickOrder);
//    }
}

