import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
    public static BufferedImage towerBaseImage;
    public static BufferedImage towerSwivelImage;

    public final int FPS = 30;
    public int FPSCOUNT = 0;

    public int enemyTrack = 0;

    int posX = 0;
    int posY = 175;
    int width = 100;
    int height = 100;

    JPanel myPanel;
    JPanel buttons;

    GameEntity enemy;
    
    String picture = "towerDefence";
    int x, y;
    boolean startScreen = true;
    boolean aboutUs = false;
    boolean inGame = false;
    boolean [] clickedTowers = new boolean [48];
    int enemyCount = 0;
    Rectangle [] enemiesList = new Rectangle [10];
    Rectangle [] towers = new Rectangle [48];
    ArrayList <Rectangle> [] towerBullets = new ArrayList [40];
    int [] startShot = new int [40];

    // Constructor
    public Start () {
        enemy = new GameEntity();
        setLayout (new BorderLayout ());
        addMouseListener (this);

        Thread thread = new Thread(this);
        thread.start();

        try{
            enemyImage = ImageIO.read(new File("enemyOne.png"));
            towerBaseImage = ImageIO.read(new File("towerBase.png"));
            towerSwivelImage = ImageIO.read(new File("towerSwivelLarge2.png"));
        }
        catch (Exception e){
            System.out.println(e);
        }

        // All the available spots for towers
        towers [0] = new Rectangle (0, 0, 95, 90);
        towers [1] = new Rectangle (100, 0, 95, 90);
        towers [2] = new Rectangle (200, 0, 95, 90);
        towers [3] = new Rectangle (300, 0, 95, 90);
        towers [4] = new Rectangle (400, 0, 95, 90);
        towers [5] = new Rectangle (500, 0, 95, 90);
        towers [6] = new Rectangle (600, 0, 95, 90);
        towers [7] = new Rectangle (700, 0, 95, 90);

        towers [8] = new Rectangle (0, 95, 95, 90);
        towers [9] = new Rectangle (100, 95, 95, 90);

        towers [10] = new Rectangle (0, 190, 95, 90);
        towers [11] = new Rectangle (100, 190, 95, 90);
        towers [12] = new Rectangle (300, 190, 95, 90);
        towers [13] = new Rectangle (400, 190, 95, 90);
        towers [14] = new Rectangle (500, 190, 95, 90);
        towers [15] = new Rectangle (600, 190, 95, 90);
        towers [16] = new Rectangle (700, 190, 95, 90);

        towers [17] = new Rectangle (0, 285, 95, 90);
        towers [18] = new Rectangle (100, 285, 95, 90);
        towers [19] = new Rectangle (200, 285, 95, 90);
        towers [20] = new Rectangle (300, 285, 95, 90);
        towers [21] = new Rectangle (400, 285, 95, 90);
        towers [22] = new Rectangle (500, 285, 95, 90);
        towers [23] = new Rectangle (600, 285, 95, 90);
        towers [24] = new Rectangle (700, 285, 95, 90);

        towers [25] = new Rectangle (0, 380, 95, 90);
        towers [26] = new Rectangle (100, 380, 95, 90);
        towers [27] = new Rectangle (200, 380, 95, 90);
        towers [28] = new Rectangle (300, 380, 95, 90);
        towers [29] = new Rectangle (400, 380, 95, 90);
        towers [30] = new Rectangle (500, 380, 95, 90);
        towers [31] = new Rectangle (600, 380, 95, 90);
        towers [32] = new Rectangle (700, 380, 95, 90);

        towers [33] = new Rectangle (0, 475, 95, 90);
        towers [34] = new Rectangle (100, 475, 95, 90);
        towers [35] = new Rectangle (200, 475, 95, 90);
        towers [36] = new Rectangle (300, 475, 95, 90);
        towers [37] = new Rectangle (400, 475, 95, 90);
        towers [38] = new Rectangle (500, 475, 95, 90);
        towers [39] = new Rectangle (600, 475, 95, 90);
        towers [40] = new Rectangle (700, 475, 95, 90);
    }

    // FPS count
    @Override
    public void run() {
        while(true) {
            update();
            this.repaint();
            FPSCOUNT++;
//            System.out.println(FPSCOUNT);
            try {
                Thread.sleep(800/FPS);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Moving the enemy and moving the bullets
    public void update() {
        moveEnemy();
        updateBullets ();
    }

    // Goes through each bullets and moves them, if its off the page, it will undraw and remove the bullet
    public void updateBullets () {
        //Check to see if it is time to add a new bullet to each tower
        for (int i = 0; i < towerBullets.length; i++) {
            if (towerBullets [i] != null) {
                if ((FPSCOUNT - startShot [i]) % 20 == 0) {
                    towerBullets [i].add (new Rectangle (towers[i]));
                }
            }
        }

        for (int i = 0; i < towerBullets.length; i++) {
            if (towerBullets [i] != null) {
                for (int j = 0; j < towerBullets[i].size(); j++) {
                    towerBullets[i].get(j).x++;
                    if (towerBullets [i].get(j).x >= 800 || towerBullets [i].get(j).y >= 800 || towerBullets [i].get(j).x <= 0 || towerBullets [i].get(j).y <= 0) {
                        towerBullets [i].remove (j);
                        j--;
                    }
                }
            }
        }
    }

    // Moves the enemy
    public void moveEnemy () {
        //spawn a new enemy every certain frame counts
        if (inGame && FPSCOUNT % 45 == 0 && enemyCount < 5) {
            enemiesList[enemyCount++] = new Rectangle (-100,250,100,100);
        }
        // Loop through all the enemies and move them
        // Moving the ghost along the track

        for (int i = 0; i < enemyCount; i++) {
            if (enemiesList[i] != null) {
                if (enemiesList[i].x <= 200 || enemiesList[i].y <= 75) {
                    enemiesList[i].x += 2;
                } else {
                    enemiesList[i].y -= 2;
                }
            }
        }
    }

    // Paint Component
    public void paintComponent (Graphics g) {
        super.paintComponent (g);

        Path currentRelativePath = Paths.get("");
        String root = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current absolute path is: " + root);

        String splashImagePath = root + "/ISU/media/" + picture + ".png";
        File splashImage = new File(splashImagePath);
//        System.out.println("Entire path: " + splashImagePath);

        Image background;

        if (splashImage.exists() && !splashImage.isDirectory()) {
            background = Toolkit.getDefaultToolkit().getImage(splashImagePath);
            g.drawImage(background, 0, 0, 800, 561, this);
        }
        else {
            System.out.println("IMAGE CANNOT BE FOUND");
        }

        if (inGame == true) {
            for (int i = 0; i < enemyCount; i++) {
//                enemiesList [x] =
//                System.out.println ("Enemy moving");
                g.drawImage (enemyImage, enemiesList[i].x, enemiesList[i].y, 100, 100, this);
            }
        }

        for (int i = 0; i < towers.length; i++) {
            if (clickedTowers[i] == true) {
                g.drawImage (towerBaseImage, towers[i].x + 7, towers[i].y + 5, 80, 80, this);
                if (FPSCOUNT < 50) {
                    g.drawImage(rotateImage(270), towers[i].x + 7, towers[i].y + 5, 80, 80, this);
                } else {
                        if (enemiesList[enemyTrack].x > 775) {
                            enemyTrack++;
                        }
                    g.drawImage (rotateImage (getTheta (enemiesList [enemyTrack].x, enemiesList [enemyTrack].y, i)), towers [i].x + 7, towers[i].y + 5, 80, 80, this);
                }
            }
        }

        for (int i = 0; i < towerBullets.length; i++) {
            if (towerBullets [i] != null) {
                for (int j = 0; j < towerBullets[i].size(); j++) {
                    g.drawRect (towerBullets [i].get(j).x, towerBullets [i].get (j).y, 20, 20);
                }
            }
        }
    }

    // Gets theta for the angle of the turret nozzle
    public double getTheta (int x2, int y2, int i) {

        int x1 = towers[i].x;
        int y1 = towers[i].y;

        double hypotenuse;

        hypotenuse = Math.sqrt(((y2 - y1) * (y2 - y1)) + ((x2 - x1) * (x2 - x1)));

        double vertical = y1 - y2;
        double theta = (Math.asin(vertical / hypotenuse));
        theta = Math.toDegrees(theta);

         if (x2 > x1) {
            theta = 180 - theta;
        }

        return theta;
    }

    // Rotates the nozzle image to fit theta
    public BufferedImage rotateImage (double theta) {
        int width = (int) Math.round(towerSwivelImage.getWidth() / 2.7 + towerSwivelImage.getHeight() / 2.7);
        int height = (int) Math.round(towerSwivelImage.getWidth() / 2.7 + towerSwivelImage.getHeight() / 2.7);

        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        AffineTransform at = new AffineTransform();

        at.translate(width / 2, height / 2);

        at.rotate (Math.toRadians(theta),0, 0);
        at.translate (-towerSwivelImage.getWidth() / 2, -towerSwivelImage.getHeight() / 2);
        AffineTransformOp rotateOp = new AffineTransformOp (at, AffineTransformOp.TYPE_BILINEAR);

        rotateOp.filter (towerSwivelImage, rotatedImage);

        return rotatedImage;
    }

    public void actionPerformed (ActionEvent event) {
        String click = event.getActionCommand ();
    }

    // Gets mouse clicked
    public void mouseClicked (MouseEvent e) {
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

    // When mouse is clicked, declares what will happen
    public void handleAction (int x, int y) {
        // When on about us pane
        if (aboutUs == true)  {
            if (x >= 642 && x <= 721 && y >= 12 && y <= 38) { // Exit button
                picture = "towerDefence";
                startScreen = true;
                inGame = false;
                repaint();
            }
        }

        // When in start screen
    	if (startScreen = true) {
	    	if (x >= 644 && x <= 719 && y >= 507 && y <= 531) { // Exit button
	    		System.exit (0);
	    	}
	    	
	    	else if (x >= 331 && x <= 467 && y >= 194 && y <= 233) { // Start button
	    		System.out.println ("START");
	    		picture = "firstMapGrid";
                map = 1;
	    		startScreen = false;
                inGame = true;
                FPSCOUNT = 1;
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

        // When in game
        if (inGame == true) {
            // Checks if a tower has been clicked yet, if yes - returns pos, if no - returns -1
            int clickedPos = -1;
            for (int i = 0; i < towers.length; i++) {
                if (x > towers [i].x && x < towers[i].x + 100 && y > towers [i].y && y < towers [i].y + 100) {
                    if (clickedTowers [i] == false) {
                        clickedPos = i;
                        clickedTowers [i] = true;
                    }
                    System.out.print (clickedTowers [i]);
                    break;
                }
            }

            // Adds clicked pos for towers that have been clicked, also records FPS
            if (clickedPos != -1) {
                towerBullets [clickedPos] = new ArrayList <Rectangle> ();
                towerBullets [clickedPos].add (new Rectangle (towers[clickedPos]));
                startShot [clickedPos] = FPSCOUNT;
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

