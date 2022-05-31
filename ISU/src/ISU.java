import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class ISU {
    JFrame frame;
    JPanel myPanel;
    JPanel buttons;
    JButton startButton;
    JButton aboutUs;
    JButton howTo;

    // Constructor
    public ISU () {
        frame = new JFrame("Basic JFrame Example");
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.setLocation(0, 0);

        myPanel = new JPanel();
        myPanel.setLayout (new BorderLayout());

        buttons = new JPanel ();
        buttons.setLayout (new BoxLayout (buttons, BoxLayout.PAGE_AXIS));

        startButton = new JButton ("Start");
        aboutUs = new JButton ("About us");
        howTo = new JButton ("How to play");

        buttons.add (startButton);
        buttons.add (aboutUs);
        buttons.add (howTo);

        myPanel.add (buttons, BorderLayout.CENTER);

        frame.add (myPanel);
        frame.pack ();
        frame.setVisible (true);
    }

    public void actionPerformed(ActionEvent event) {

    }

    public static void main(String[] args) {
        new ISU();
    }
}



