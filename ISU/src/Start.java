import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Start extends JPanel {
    public static JFrame frame;
    public static Graphics g;

    JPanel myPanel;
    JPanel buttons;

    JButton startButton;
    JButton aboutUs;
    JButton howTo;

    JLabel bgd;
    ImageIcon bgdImage;
    String background = "/media/firstMap.png";

    GameEntity enemy;

    // Constructor
    public Start() {
        setLayout (new BorderLayout());
        buttons = new JPanel();
        buttons.setLayout (new BoxLayout(buttons, BoxLayout.PAGE_AXIS));

        startButton = new JButton("Start");
        aboutUs = new JButton("About us");
        howTo = new JButton("How to play");

        bgd = new JLabel ("media/towerDefence.png");

        buttons.add (startButton);
        buttons.add (aboutUs);
        buttons.add (howTo);

        //  add (BorderLayout.NORTH, bgd);
        add(BorderLayout.SOUTH, buttons);

        //Instantiate the object
        enemy = new GameEntity();
    }

    public void paintComponent (Graphics g) {
        super.paintComponent (g);
        Image blah = Toolkit.getDefaultToolkit().getImage("media/enemyOne.png");
        g.drawImage(blah, 100, 100, this);

        System.out.print ("HI");
    }

    public void actionPerformed(ActionEvent event) {

    }

    public static void main (String[] args) {
        frame = new JFrame ("Basic JFrame Example");
        frame.setPreferredSize (new Dimension (800, 600));
        frame.setLocation(0, 0);

        Start myPanel = new Start ();
        frame.add (myPanel);
        frame.pack ();
        frame.setVisible (true);

        // Game.Main();
    }
}

