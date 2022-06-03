import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Start extends JPanel {
    public static JFrame frame;
    public static Graphics g;

    JPanel myPanel;
    JPanel buttons;

    ImageIcon bgdImage;
    String background = "/media/firstMap.png";

    GameEntity enemy;

    // Constructor
    public Start() {
        setLayout (new BorderLayout ());
        
        //Instantiate the object
        enemy = new GameEntity();
        
    }

    public void paintComponent (Graphics g) {
        super.paintComponent (g);
        Image blah = Toolkit.getDefaultToolkit().getImage("media/towerDefence.png");
        g.drawImage(blah, 0, 0, 800, 561, this);
        

    }

    public void actionPerformed (ActionEvent event) {
    	String click = event.getActionCommand (); 
    	
    }
    
    public void mouseClicked (MouseEvent e) {
    	int x, y;
		x = e.getX ();
		y = e.getY ();

		System.out.print ("x: " + x + "y: " + y);
		
		handleAction (x, y);
    }
    
    public void handleAction (int x, int y) {
    	
//    	if (x) { // Exit
//    		System.exit (0);
//    	}
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

