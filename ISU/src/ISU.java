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

    JLabel bgd;
    ImageIcon bgdImage;
    String background = "firstMap.png";

    // Constructor
    public ISU () {
        frame = new JFrame("Basic JFrame Example");
        frame.setPreferredSize(new Dimension(800, 600));
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

        JPanel bgd = new JPanel ();
//        bgd. (background);

//        myPanel.add (bgdImage);
        myPanel.add (buttons);

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



