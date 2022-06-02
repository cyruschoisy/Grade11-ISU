import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Start extends Component {
        public static JFrame frame;
        public static Graphics g;

        JPanel myPanel;
        JPanel buttons;

        JButton startButton;
        JButton aboutUs;
        JButton howTo;
        JButton exit;

        JLabel bgd;
        ImageIcon bgdImage;
        String background = "/media/firstMap.png";
        Image startbgd;

        // Constructor
    public Start () {
        frame = new JFrame("Basic JFrame Example");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLocation(0, 0);

        myPanel = new JPanel();
        myPanel.setLayout(new BorderLayout());

        buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));

        startButton = new JButton("Start");
        aboutUs = new JButton("About us");
        howTo = new JButton("How to play");
        exit = new JButton ("Exit");

        MediaTracker tracker = new MediaTracker (this);
        startbgd = Toolkit.getDefaultToolkit ().getImage ("/media/towerDefence.png");
        tracker.addImage (startbgd, 0);

        //  Wait until all of the images are loaded
        try
        {
            tracker.waitForAll ();
        }
        catch (InterruptedException e)
        {
        }

        buttons.add(startButton);
        buttons.add(aboutUs);
        buttons.add(howTo);
        buttons.add(exit);

        myPanel.add (bgd);
        myPanel.add (buttons);

        frame.setUndecorated(false);
        frame.add (myPanel);
        frame.pack ();
        frame.setVisible (true);

        setFocusable (true); // Need this to set the focus to the panel in order to add the keyListener
//        addKeyListener (this);
//        addMouseListener (this);
    }

    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand ();
        if (eventName.equals ("New"))
        {
//            newGame ();
        }

        else if (eventName.equals ("Exit"))
        {
            System.exit (0);
        }
    }

    public static void main(String[] args) {
        new Start ();
        Game.Main();
    }
}

