import javax.swing.*;
import java.awt.*;

public class binPackingBoxPanel extends JPanel {

    public binPackingBoxPanel(){
        this.setLayout(new GridLayout(2,1));

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JScrollPane jScrollPane2 = new JScrollPane();
        jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(16);

        JPanel jPanelDoos1 = new JPanel();
        jPanelDoos1.setLayout(new BoxLayout(jPanelDoos1, BoxLayout.Y_AXIS));
        jPanelDoos1.add(new JLabel("Doosje 1"));
        jPanelDoos1.add(jScrollPane);

        this.add(jPanelDoos1);

        JPanel jPanelDoos2 = new JPanel();
        jPanelDoos2.setLayout(new BoxLayout(jPanelDoos2, BoxLayout.Y_AXIS));
        jPanelDoos2.add(new JLabel("Doosje 2"));
        jPanelDoos2.add(jScrollPane2);

        this.add(jPanelDoos2);
    }

}