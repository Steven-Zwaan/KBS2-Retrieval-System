import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PackingScreen extends JPanel implements ActionListener {

    JPanel viewPanel;
   // JList gepickteOrderList;  ?
    JList pickOrderList;  //bevat de orders die gepicked worden
    JList doos1List;
  //  ArrayList<PickOrder> doos1Array;
    JList doos2List;
  //  ArrayList<PickOrder> doos2Array;

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
        pickOrderList = new JList(WeergavePanel.pickOrders.toArray());
        orderLineScrollPane = new JScrollPane(pickOrderList);
        this.add(orderLineScrollPane, BorderLayout.WEST);

        //Panel voor de visuele weergave van de 2 dozen aan de rechter kant
        JPanel dozenWeergave = new JPanel();
        dozenWeergave.setLayout(new BoxLayout(dozenWeergave, BoxLayout.Y_AXIS));
        dozenWeergave.setPreferredSize(new Dimension(400, this.getHeight()));
        this.add(dozenWeergave, BorderLayout.EAST);

        //panel voor doos 1
        dozenWeergave.add(new JLabel("Doos 1"));
      //  doos1List = new JList();
        doos1ScrollPane = new JScrollPane();
        dozenWeergave.add(doos1ScrollPane);

        //panel voor doos 2
        dozenWeergave.add(new JLabel("Doos 2"));
        //doos2List = new JList();
        doos2ScrollPane = new JScrollPane();
        dozenWeergave.add(doos2ScrollPane);

    }

    public void refreshPanel(){
        pickOrderList.setListData(WeergavePanel.pickOrders.toArray());
        pickOrderList.revalidate();
        pickOrderList.repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

//    static void addOrderLineDoos(PickOrder ) {
//        .add(pickOrder);
//    }
}

