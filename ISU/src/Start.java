import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

public class Start extends JPanel implements MouseListener{
    public static JFrame frame;
    public static Graphics g;
    public static int map;
    public static int wave;
    public static int[][] board = new int[15][20];
    public String root;

    int posX = 100;
    int posY = 100;
    int width = 100;
    int height = 100;

    JPanel myPanel;
    JPanel buttons;

    ImageIcon bgdImage;
    String background = "media/firstMap.png";

    GameEntity enemy;
    
    String picture = "towerDefence";

    boolean startScreen = true;
    boolean aboutUs = true;
    
    // Constructor
    public Start () {
        setLayout (new BorderLayout ());
        addMouseListener (this);
        //Call the constructor for the object
        for (int i = 1; i < 10; i++) {
        	width = (int)(Math.random () * 99) + 1;
            height = (int)(Math.random () * 99) + 1;
            posX = (int)(Math.random () * 99) + 1;
            posY = (int)(Math.random () * 99) + 1;
            
//        	enemy.speed = (int)(Math.random() * 99) + 1;
//        	enemy.health = (int)(Math.random() * 9) + 1;
        	enemy = new GameEntity();
        	this.paint (g);
        }
    }

    public void paintComponent (Graphics g) {
        super.paintComponent (g);

        Path currentRelativePath = Paths.get("");
        String root = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + root);

        String splashImagePath = root + "/ISU/media/" + picture + ".png";
        File splashImage = new File(splashImagePath);
        System.out.println("Entire path: " + splashImagePath);

        Image blah;
        if (splashImage.exists() && !splashImage.isDirectory()) {
            blah = Toolkit.getDefaultToolkit().getImage(splashImagePath);
            g.drawImage(blah, 0, 0, 800, 561, this);
        } else {
            System.out.println("IMAGE CANNOT BE FOUND");
        }

       drawEntity (g, posX, posY, width, height);
    }

    public void drawEntity (Graphics g, int posX, int posY, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.drawImage(enemy.getImage(), posX, posY, width, height, this);
    }

    public void actionPerformed (ActionEvent event) {
    	String click = event.getActionCommand ();
    }
    
    
    public void mouseClicked (MouseEvent e) {
    	int x, y;
		x = e.getX ();
		y = e.getY ();

		System.out.println ("x: " + x + " y: " + y);
		
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
        if (aboutUs == false)  {
            if (x >= 642 && x <= 721 && y >= 12 && y <= 38) { // Exit button
                picture = "towerDefence";
                startScreen = true;
                repaint();
            }
        }

    	if (startScreen = true) {
	    	if (x >= 644 && x <= 719 && y >= 507 && y <= 531) { // Exit button
	    		System.exit (0);
	    	}
	    	
	    	else if (x >= 331 && x <= 467 && y >= 194 && y <= 233) { // Start button
	    		System.out.println ("START");
	    		picture = "firstMap";
	    		startScreen = false;
	    		repaint(); 
	    	}
	    	
	    	else if (x >= 289 && x <= 510 && y >= 274 && y <= 313) { // About us button
	    		System.out.println ("ABOUT US");
                picture = "aboutUs";
                aboutUs = false;
                repaint();
            }
	    	
	    	else if (x >= 244 && x <= 552 && y >= 353 && y <= 391) { // How to Play button
	    		System.out.println ("HOW TO PLAY");
	    	}
    	}
    }

    public static void main (String[] args) {
        frame = new JFrame ("Tower Defence");
        frame.setPreferredSize (new Dimension (800, 600));
        frame.setLocation(0, 0);

        Start myPanel = new Start ();
        frame.add (myPanel);
        frame.pack ();
        frame.setVisible (true);

        // Game.Main();

    }


}

