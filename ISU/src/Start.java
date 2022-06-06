import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Start extends JPanel implements MouseListener{
    public static JFrame frame;
    public static Graphics g;

    JPanel myPanel;
    JPanel buttons;

    ImageIcon bgdImage;
    String background = "media/firstMap.png";

    GameEntity enemy;

    // Constructor
    public Start () {
        setLayout (new BorderLayout ());
        addMouseListener (this);
        //Call the constructor for the object
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

		//System.out.println ("x: " + x + " y: " + y);
		
		handleAction (x, y);
    }
   
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
    public void handleAction (int x, int y) {
    	
    	if (x >= 644 && x <= 719 && y >= 507 && y <= 531) { // Exit button
    		System.exit (0);
    	}
    	
    	else if (x >= 331 && x <= 467 && y >= 194 && y <= 233) { // Start button
    		System.out.println ("START");
    	}
    	
    	else if (x >= 289 && x <= 510 && y >= 274 && y <= 313) { // About us button
    		System.out.println ("ABOUT US");
    	}
    	
    	else if (x >= 244 && x <= 552 && y >= 353 && y <= 391) { // How to Play button
    		System.out.println ("HOW TO PLAY");
    	}
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

