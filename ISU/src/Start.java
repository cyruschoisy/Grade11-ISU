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

//    ImageIcon bgdImage;
//    String background = "media/firstMap.png";

    GameEntity enemy;
    
    String picture = "towerDefence";
    int x, y;
    boolean startScreen = true;
    boolean aboutUs = false;
    boolean inGame = false;
    boolean [] clickedTowers = new boolean [12];
    int enemyCount = 0;
    Rectangle [] enemiesList = new Rectangle [10];
    Rectangle [] towers = new Rectangle [12];

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
            towerSwivelImage = ImageIO.read(new File("towerSwivelLarge.png"));
        }
        catch (Exception e){
            System.out.println(e);
        }

        towers [0] = new Rectangle (5, 132, 100, 100);
        towers [1] = new Rectangle (110, 132, 100, 100);
        towers [2] = new Rectangle (220, 132, 100, 100);

        towers [3] = new Rectangle (5, 332, 100, 100);
        towers [4] = new Rectangle (110, 332, 100, 100);
        towers [5] = new Rectangle (220, 332, 100, 100);

        towers [6] = new Rectangle (477, 0, 100, 100);
        towers [7] = new Rectangle (587, 0, 100, 100);
        towers [8] = new Rectangle (693, 0, 100, 100);

        towers [9] = new Rectangle (477, 200, 100, 100);
        towers [10] = new Rectangle (587, 200, 100, 100);
        towers [11] = new Rectangle (693, 200, 100, 100);
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
        moveEnemy();
    }

    public void moveEnemy () {
        //spawn a new enemy every certain frame counts
        if (inGame && FPSCOUNT % 275 == 0 && enemyCount < 5) {
            enemiesList[enemyCount++] = new Rectangle (0,200,100,100);
        }
        //Loop through all the enemies and move them
        // Moving the ghost along the track

        for (int i = 0; i < enemyCount; i++) {
            if (enemiesList[i] != null) {
                enemiesList[i].x += 2;
                if (enemiesList[i].x > 215 && enemiesList[i].x < 500) {
                    enemiesList[i].y -= 1;
                }
            }
        }
    }
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
//                System.out.println("DRAWING TOWER");
                g.drawImage (towerBaseImage, towers[i].x, towers[i].y, 100, 100, this);
                if (FPSCOUNT < 276) {
                    g.drawImage(rotateImage(270), towers[i].x, towers[i].y, 100, 100, this);
                } else {
                        if (enemiesList[enemyTrack].x > 775) {
                            enemyTrack++;
                        }
                    g.drawImage (rotateImage(getTheta(enemiesList[enemyTrack].x, enemiesList[enemyTrack].y, i)), towers [i].x, towers[i].y, 100, 100, this);
                }
            }
        }
    }

    public double getTheta(int x2, int y2, int i) {

        int x1 = towers[i].x;
        int y1 = towers[i].y;

        double hypotenuse = Math.sqrt(((y2 - y1) * (y2 - y1)) + ((x2 - x1) * (x2 - x1)));
        double vertical = y1 - y2;
        double theta = (Math.asin(vertical / hypotenuse));
        theta = Math.toDegrees(theta);
        theta = 180 - theta;

        return theta;
    }

    public BufferedImage rotateImage(double theta) {

        double sin = Math.abs(Math.sin(Math.toRadians(theta)));
        double cos = Math.abs(Math.cos(Math.toRadians(theta)));

        int width = (int) Math.round(towerSwivelImage.getWidth() * cos + towerSwivelImage.getHeight() * sin);
        int height = (int) Math.round(towerSwivelImage.getWidth() * sin + towerSwivelImage.getHeight() * cos);

        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        AffineTransform at = new AffineTransform();

        at.translate(width / 2, height / 2);

        at.rotate(Math.toRadians(theta),0, 0);
        at.translate(-towerSwivelImage.getWidth() / 2, -towerBaseImage.getHeight() / 2);
        AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        rotateOp.filter(towerSwivelImage, rotatedImage);

        return rotatedImage;
    }

    public void actionPerformed (ActionEvent event) {
        String click = event.getActionCommand ();
    }
    
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

        if (inGame == true) {
            FPSCOUNT = 1;
            for (int i = 0; i < towers.length; i++) {
                if (x > towers [i].x && x < towers[i].x + 100 && y > towers [i].y && y < towers [i].y + 100) {
                    clickedTowers [i] = true;
                    System.out.print (clickedTowers [i]);
                    break;
                }
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

