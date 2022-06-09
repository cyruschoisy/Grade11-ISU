import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import javax.swing.*;

public class Start extends JPanel implements Runnable, MouseListener{
    public static JFrame frame;
    public static Graphics g;
    public static int map = 0;
    public static int wave;
    public static int[][] board = new int[15][20];
    public int count = 0;
    public String root;

    public static BufferedImage enemyImage;

    public final int FPS = 30;

    int posX = 200;
    int posY = 200;
    int width = 100;
    int height = 100;

    JPanel myPanel;
    JPanel buttons;

//    ImageIcon bgdImage;
//    String background = "media/firstMap.png";

    GameEntity enemy;
    
    String picture = "towerDefence";

    boolean startScreen = true;
    boolean aboutUs = true;
    
    // Constructor
    public Start () {
        enemy = new GameEntity();
        setLayout (new BorderLayout ());
        addMouseListener (this);

        Thread thread = new Thread(this);
        thread.start();

        try{
            enemyImage = ImageIO.read(new File("enemyOne.png"));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        System.out.println("Init. Framerate");
        while(true) {
            update();
            this.repaint();
            try {
                Thread.sleep(1000/FPS);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        //Create our movement for the enemies
        posX += 4;
    }

    public void paintComponent (Graphics g) {
        super.paintComponent (g);

        Path currentRelativePath = Paths.get("");
        String root = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current absolute path is: " + root);

        String splashImagePath = root + "/ISU/media/" + picture + ".png";
        File splashImage = new File(splashImagePath);
//        System.out.println("Entire path: " + splashImagePath);

        Image blah;
        if (splashImage.exists() && !splashImage.isDirectory()) {
            blah = Toolkit.getDefaultToolkit().getImage(splashImagePath);
            g.drawImage(blah, 0, 0, 800, 561, this);
        } else {
            System.out.println("IMAGE CANNOT BE FOUND");
        }

        for (int i = 0; i < GameEntity.MapEnemyCount.length; i++) {
            for (int j = 0; j < GameEntity.MapEnemyCount[i]; j++) {
                g.drawImage(enemyImage, getXPos(count), getYPos(count), 100, 100, null);
                if (count != 49) {
                    count++;
                }
            }
        }
    }

    public int getXPos(int count) {
        posX += GameEntity.Map1EnemyMovementX[count];

        return posX;
    }

    public int getYPos(int count) {
        posY += GameEntity.Map1EnemyMovementY[count];

        return posY;
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
	    		picture = "firstMapFinal";
                map = 1;
	    		startScreen = false;
	    		repaint(); 
	    	}
	    	
	    	else if (x >= 289 && x <= 510 && y >= 274 && y <= 313) { // About us button
	    		System.out.println ("ABOUT US");
                picture = "aboutUs";
                aboutUs = false;
                startScreen = false;
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


    }
}

