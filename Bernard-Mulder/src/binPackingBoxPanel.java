import javax.swing.*;
import java.awt.*;

public class binPackingBoxPanel extends JPanel {

    public binPackingBoxPanel(){
        this.setLayout(new GridLayout(2,1));

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(new JLabel("Doosie"));
        jPanel.add(jScrollPane);

        this.add(jPanel);
    }

}
