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
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import javax.swing.*;

public class Start extends JPanel implements Runnable, MouseListener {
    public static JFrame frame;
    public static Graphics g;
    public static int map = 0;
    public static int wave;
    public static int[][] board = new int [8][6];
    public int count = 0;
    public String root;

    public static BufferedImage enemyImage;
    public static BufferedImage towerImage;

    public final int FPS = 30;
    public int FPSCOUNT = 0;

    int posX = 0;
    int posY = 175;
    int width = 100;
    int height = 100;

    JPanel myPanel;
    JPanel buttons;

//    ImageIcon bgdImage;
//    String background = "media/firstMap.png";

    GameEntity enemy;
    
    String picture = "towerDefence";

    boolean startScreen = true;
    boolean aboutUs = false;
    boolean inGame = false;

    Rectangle [] enemies = new Rectangle [10];

    // Constructor
    public Start () {
        enemy = new GameEntity();
        setLayout (new BorderLayout ());
        addMouseListener (this);

        Thread thread = new Thread(this);
        thread.start();

        try{
            enemyImage = ImageIO.read(new File("enemyOne.png"));
            towerImage = ImageIO.read(new File("tower1.png"));
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public void run() {
        while(true) {
            update();
            this.repaint();
            FPSCOUNT++;
//            System.out.println(FPSCOUNT);
            try {
                Thread.sleep(1000/FPS);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
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
        }
        else {
            System.out.println("IMAGE CANNOT BE FOUND");
        }
        if (inGame == true) {
            for (int i = 0; i < 10; i++) {
                System.out.println ("Enemy moving");
                g.drawImage (enemyImage, posX, posY, 100, 100, this);

                // Moving the ghost along the track
                posX += 2;
                if (posX > 215 && posX < 500) {
                    posY -= 1;
                }
                else if (posX > 850) {
                    posX += 0;
                    System.out.println("Stopped moving");
                }
                // slowing down the movement of the ghost
                long slowdown = 1;
                try {
                    Thread.sleep(slowdown * 20);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

//    public int getXPos(int count) {
//        posX += GameEntity.Map1EnemyMovementX[count];
//
//        return posX;
//    }
//
//    public int getYPos(int count) {
//        posY += GameEntity.Map1EnemyMovementY[count];
//
//        return posY;
//    }

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
        if (aboutUs == true)  {
            if (x >= 642 && x <= 721 && y >= 12 && y <= 38) { // Exit button
                picture = "towerDefence";
                startScreen = true;
                inGame = false;
                repaint();
            }
        }

    	if (startScreen = true) {
	    	if (x >= 644 && x <= 719 && y >= 507 && y <= 531) { // Exit button
	    		System.exit (0);
	    	}
	    	
	    	else if (x >= 331 && x <= 467 && y >= 194 && y <= 233) { // Start button
	    		System.out.println ("START");
	    		picture = "secondmap";
                map = 1;
	    		startScreen = false;
                inGame = true;
                FPSCOUNT = 0;
                repaint();
	    	}
	    	
	    	else if (x >= 289 && x <= 510 && y >= 274 && y <= 313) { // About us button
	    		System.out.println ("ABOUT US");
                picture = "aboutUs";
                aboutUs = true;
                startScreen = false;
                inGame = false;
                repaint();
            }
	    	
	    	else if (x >= 244 && x <= 552 && y >= 353 && y <= 391) { // How to Play button
	    		System.out.println ("HOW TO PLAY");

                aboutUs = false;
                startScreen = false;
                inGame = false;
	    	}
    	}

        if (inGame == true) {
            if (x >= 1 && x <= 105 && y >= 132 && y <= 220) { // (0, 0)
                System.out.println ("0, 0");
            }

            else if (x >= 108 && x <= 214 && y >= 131 && y <= 224) { // (1, 0)
                System.out.println ("1, 0");
            }

            else if (x >= 216 && x <= 317 && y >= 128 && y <= 223) { // (2, 0)
                System.out.println("2, 0");
            }

            else if (x >= 2 && x <= 109 && y >= 331 && y <= 426) { // (0, 2)
                System.out.println ("0, 2");
            }

            else if (x >= 110 && x <= 215 && y >= 330 && y <= 423) { // (1, 2)
                System.out.println ("1, 2");
            }

            else if (x >= 219 && x <= 320 && y >= 330 && y <= 423) { // (2, 2)
                System.out.println ("2, 2");
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

