import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Start {
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

        bgd = new JLabel ("/media/towerDefence.png");


        buttons.add(startButton);
        buttons.add(aboutUs);
        buttons.add(howTo);

        myPanel.add (bgd);
        myPanel.add (buttons);

        frame.setUndecorated(true);
        frame.add (myPanel);
        frame.pack ();
        frame.setVisible (true);
    }

    public void actionPerformed(ActionEvent event) {

    }

    public static void main(String[] args) {
        new Start ();
        Game.Main();
    }
}

