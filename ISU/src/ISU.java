import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class ISU {

    JFrame frame;
    JPanel myPanel;
    JLabel label;
    JButton button;

    // Constructor
    public ISU() {
        frame = new JFrame("Basic JFrame Example");
        frame.setPreferredSize(new Dimension(200, 200));
        frame.setLocation(200, 200);

        myPanel = new JPanel();
        label = new JLabel("Hello World!");
        button = new JButton("Hide");

        myPanel.add(label);
        myPanel.add(button);

        frame.add(myPanel);
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {

    }


    public static void main(String[] args) {
        new ISU();
    }
}



