import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

import javax.swing.*;

public class Start extends JPanel implements Runnable, MouseListener {
    public static JFrame frame;
    public static Graphics g;
    public static int map = 0;
    public static int wave = 0;
    public static double slope;
    // Initializing all our images in the program
    public static BufferedImage enemyImage;
    public static BufferedImage towerBaseImage;
    public static BufferedImage towerSwivelImage;
    public static BufferedImage bullet;
    public static BufferedImage rotatedBullet;
    public static BufferedImage wave1Image;
    public static BufferedImage wave2Image;
    public static BufferedImage wave3Image;
    public static BufferedImage wave4Image;
    public static BufferedImage wave5Image;
    // FPS / How many times the screen updates
    public final int FPS = 30;
    public int FPSCOUNT = 0;
    // How many enemies are on the screen at once
    public int enemyTrack = 0;
    // Background picture of track
    String picture = "towerDefence";
    int x, y;
    boolean startScreen = true;
    boolean aboutUs = false;
    boolean inGame = false;
    boolean waveStart = false;
    boolean waveComplete = false;
    boolean[] clickedTowers = new boolean[48];
    int enemyCount = 0;
    Rectangle[] enemiesList = new Rectangle[50];
    Rectangle[] towers = new Rectangle[48];
    ArrayList<Rectangle>[] towerBullets = new ArrayList[500];
    BufferedImage bullets[] = new BufferedImage[500];
    ArrayList <Double> bulletSlope[] = new ArrayList[500];
    Boolean setupBullets[] = new Boolean[500];
    int [] startShot = new int[500];
    Clip bgdMusic, click;
    int [] enemiesPerWave = {3, 3, 5, 7, 9};
    boolean howToPlay = false;
    int [][] enemyHP = {{2}, {2, 2, 2}, {2, 2, 2, 2, 2}, {2, 2, 2, 2, 2, 2, 2}, {2, 2, 2, 2, 2, 2, 2, 2, 2}};
    int interval = 100;
    int money = 0;


    // Constructor
    public Start() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        setLayout(new BorderLayout());
        addMouseListener(this);

        Thread thread = new Thread(this);
        thread.start();

        // Set up the icon image (Tracker not needed for the icon image)
        Image iconImage = Toolkit.getDefaultToolkit().getImage("enemyOne.png");
        frame.setIconImage(iconImage);

        for (int i = 0; i < setupBullets.length; i++) {
            setupBullets[i] = false;
        }

//        moneyText.setText (String.valueOf(money));
//        moneyText.add (frame);

        try {
            enemyImage = ImageIO.read(new File("enemyOne.png"));
            towerBaseImage = ImageIO.read(new File("towerBase.png"));
            towerSwivelImage = ImageIO.read(new File("towerSwivelLarge2.png"));
            bullet = ImageIO.read(new File("bullet.png"));
            wave1Image = ImageIO.read(new File("wave1.png"));
            wave2Image = ImageIO.read(new File("wave1.png"));
            wave3Image = ImageIO.read(new File("wave1.png"));
            wave4Image = ImageIO.read(new File("wave1.png"));
            wave5Image = ImageIO.read(new File("wave1.png"));

            AudioInputStream sound = AudioSystem.getAudioInputStream(new File("music.wav"));
            bgdMusic = AudioSystem.getClip();
            bgdMusic.open(sound);

            sound = AudioSystem.getAudioInputStream(new File("click.wav"));
            click = AudioSystem.getClip();
            click.open(sound);

        } catch (Exception e) {
            System.out.println(e);
        }

        // All the available spots for towers
        towers[0] = new Rectangle(0, 0, 95, 90);
        towers[1] = new Rectangle(100, 0, 95, 90);
        towers[2] = new Rectangle(200, 0, 95, 90);
        towers[3] = new Rectangle(300, 0, 95, 90);
        towers[4] = new Rectangle(400, 0, 95, 90);
        towers[5] = new Rectangle(500, 0, 95, 90);
        towers[6] = new Rectangle(600, 0, 95, 90);
        towers[7] = new Rectangle(700, 0, 95, 90);

        towers[8] = new Rectangle(0, 95, 95, 90);
        towers[9] = new Rectangle(100, 95, 95, 90);

        towers[10] = new Rectangle(0, 190, 95, 90);
        towers[11] = new Rectangle(100, 190, 95, 90);
        towers[12] = new Rectangle(300, 190, 95, 90);
        towers[13] = new Rectangle(400, 190, 95, 90);
        towers[14] = new Rectangle(500, 190, 95, 90);
        towers[15] = new Rectangle(600, 190, 95, 90);
        towers[16] = new Rectangle(700, 190, 95, 90);

        towers[17] = new Rectangle(0, 285, 95, 90);
        towers[18] = new Rectangle(100, 285, 95, 90);
        towers[19] = new Rectangle(200, 285, 95, 90);
        towers[20] = new Rectangle(300, 285, 95, 90);
        towers[21] = new Rectangle(400, 285, 95, 90);
        towers[22] = new Rectangle(500, 285, 95, 90);
        towers[23] = new Rectangle(600, 285, 95, 90);
        towers[24] = new Rectangle(700, 285, 95, 90);

        towers[25] = new Rectangle(0, 380, 95, 90);
        towers[26] = new Rectangle(100, 380, 95, 90);
        towers[27] = new Rectangle(200, 380, 95, 90);
        towers[28] = new Rectangle(300, 380, 95, 90);
        towers[29] = new Rectangle(400, 380, 95, 90);
        towers[30] = new Rectangle(500, 380, 95, 90);
        towers[31] = new Rectangle(600, 380, 95, 90);
        towers[32] = new Rectangle(700, 380, 95, 90);

        towers[33] = new Rectangle(0, 475, 95, 90);
        towers[34] = new Rectangle(100, 475, 95, 90);
        towers[35] = new Rectangle(200, 475, 95, 90);
        towers[36] = new Rectangle(300, 475, 95, 90);
        towers[37] = new Rectangle(400, 475, 95, 90);
        towers[38] = new Rectangle(500, 475, 95, 90);
        towers[39] = new Rectangle(600, 475, 95, 90);
        towers[40] = new Rectangle(700, 475, 95, 90);
    }

    // FPS count
    @Override
    public void run() {
        while (true) {
            update();
            this.repaint();
            FPSCOUNT++;
//            System.out.println(FPSCOUNT);
            try {
                Thread.sleep(800 / FPS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Moving the enemy and moving the bullets and checking for collision
    public void update() {
        moveEnemy();
        updateBullets();
    }

    // Returns the correct waveImage to display
    public BufferedImage waveCount(int[] enemiesPerWave, int wave) {
        BufferedImage[] images = {wave1Image, wave2Image, wave3Image, wave4Image, wave5Image};

        BufferedImage waveImage = images[wave];

        return waveImage;
    }

    // Checks if a bullet has collided with any ghost
    public void checkCollision(Rectangle bullet) {
        for (int j = 0; j < enemiesList.length; j++) {
//            System.out.println("BEFORE != null");
//            System.out.println("ENEMY COUNT: " + enemyCount);
            if (enemiesList[enemyCount - 1] != null) {
//                System.out.println("GOING INTO LOOP");
                if (enemiesList[j] != null && enemiesList[j].intersects(bullet)) {
                    money = money + 50;
                    System.out.println ("Balance: " + money);

                    enemiesList[j] = null;
                    System.out.println("ENEMY DEATH");
                    for (int i = 0; i < enemiesList.length; i++) {
                        if (enemiesList[i] != null) {
                            enemyTrack = i;
                            break;
                        }
                    }
                }
            }
        }
    }


    // Goes through each bullet and moves them, if it's off the page, it will undraw and remove the bullet
    public void updateBullets() {
        //Check to see if it is time to add a new bullet to each tower
        for (int i = 0; i < towerBullets.length; i++) {
            if (towerBullets[i] != null && enemiesList [enemyTrack] != null) {
                if ((FPSCOUNT - startShot[i]) % 100 == 0) {

                    towerBullets[i].add(new Rectangle(towers[i]));

                    double angle = getTheta(enemiesList[enemyTrack].x, enemiesList[enemyTrack].y, i);

                    if (towers[i].x > enemiesList[enemyTrack].x) {
                        angle = angle - 180;
                    }

                    rotatedBullet = rotateImage(bullet, angle);
                    bullets[i] = rotatedBullet;

                    slope = getSlope(towers[i].x + 45, towers[i].y + 40, enemiesList[enemyTrack].x, enemiesList[enemyTrack].y);

                    bulletSlope[i].add(slope);
                    setupBullets[i] = true;
//                    System.out.println (slope);

                    // Add image rotation and slope and save it
                    // Create two more array lists for slope and rotation of image
                }
            }
        }

        for (int i = 0; i < towerBullets.length; i++) {
            if (towerBullets[i] != null) {
                for (int j = 0; j < towerBullets[i].size(); j++) {
                    // Straight up
                    if (towers [i].x == enemiesList [enemyTrack].x && towers [i].y > enemiesList [enemyTrack].y) {
                        towerBullets[i].get(j).y -= bulletSlope[i].get(j) * 15;
                    }
                    // Right
                    else if (towers [i].x < enemiesList [enemyTrack].x && towers [i].y == enemiesList [enemyTrack].y) {
                        towerBullets[i].get(j).x += bulletSlope[i].get(j) * 15;
                    }
                    // Left
                    else if (towers [i].x > enemiesList [enemyTrack].x && towers [i].y == enemiesList [enemyTrack].y) {
                        towerBullets[i].get(j).x -= bulletSlope[i].get(j) * 15;
                    }
                    // Top left
                    if (towers [i].x > enemiesList [enemyTrack].x && towers [i].y > enemiesList [enemyTrack].y) {
                        towerBullets[i].get(j).x -= bulletSlope[i].get(j) * 15;
                        towerBullets[i].get(j).y -= bulletSlope[i].get(j) * 15;
                    }
                    // Top right
                    else if (towers [i].x < enemiesList [enemyTrack].x && towers [i].y > enemiesList [enemyTrack].y) {
                        towerBullets[i].get(j).x += bulletSlope[i].get(j) * 15;
                        towerBullets[i].get(j).y -= bulletSlope[i].get(j) * 15;
                    }
                    // Bottom left
                    if (towers [i].x > enemiesList [enemyTrack].x && towers [i].y < enemiesList [enemyTrack].y) {
                        towerBullets[i].get(j).x -= bulletSlope[i].get(j) * 15;
                        towerBullets[i].get(j).y += bulletSlope[i].get(j) * 15;
                    }
                    // Bottom right
                    else if (towers [i].x < enemiesList [enemyTrack].x && towers [i].y < enemiesList [enemyTrack].y) {
                        towerBullets[i].get(j).x += bulletSlope[i].get(j) * 15;
                        towerBullets[i].get(j).y += bulletSlope[i].get(j) * 15;
                    }
                    checkCollision(towerBullets[i].get(j));
                    if (towerBullets[i].get(j).x >= 800 || towerBullets[i].get(j).y >= 800 || towerBullets[i].get(j).x <= 0 || towerBullets[i].get(j).y <= 0) {
                        towerBullets[i].remove(j);
                        bulletSlope [i].remove (j);
                        j--;
                    }
                }
            }
        }
    }

    // Moves the enemy
    public void moveEnemy() {
        //spawn a new enemy every certain frame counts
        if (inGame == true && FPSCOUNT % interval == 0 && enemyCount < 50) {
            enemiesList[enemyCount++] = new Rectangle(-100, 250, 100, 100);
        }

        else if (enemyCount <= 9) {
            interval = 100;
        }

        else if (enemyCount >= 10 && enemyCount <= 19) {
            interval = 75;
        }

        else if (enemyCount >= 20 && enemyCount <= 29) {
            interval = 50;
        }

        else if (enemyCount >= 30 && enemyCount <= 39) {
            interval = 25;
        }

        else if (enemyCount >= 40 && enemyCount <= 49) {
            interval = 15;
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

                if (enemiesList[i].x > 800) {
                    enemiesList[i] = null;
                    for (int p = 0; p < enemiesList.length; p++) {
                        if (enemiesList[p] != null) {
                            enemyTrack = p;
                            break;
                        }
                    }
                }
            }
        }
//        System.out.println("ENEMY TRACK: " + enemyTrack);
        if (enemyCount == 0 && waveStart == true) {
//            System.out.println(wave);
            wave++;
            waveStart = false;
        }
    }

    // Paint Component
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

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
        } else {
            System.out.println("IMAGE CANNOT BE FOUND");
        }

        if (inGame == true) {
            for (int i = 0; i < enemyCount; i++) {
                if (enemiesList[i] != null) {
                    g.drawRect(enemiesList[i].x, enemiesList[i].y, 100, 100);
                    g.drawImage(enemyImage, enemiesList[i].x, enemiesList[i].y, 100, 100, this);
                }

                // Changes wave image every 300 frames
                if (FPSCOUNT % 300 == 0) {
                    BufferedImage waveImage = waveCount(enemiesPerWave, wave);
                    g.drawImage(waveImage, 800, 800, 100, 100, this);
                }
            }
        }

        for (int i = 0; i < towers.length; i++) {
            if (clickedTowers[i] == true) {
                g.drawImage(towerBaseImage, towers[i].x + 7, towers[i].y + 5, 80, 80, this);

                if (FPSCOUNT > 0) {
                    g.drawImage(rotateImage(towerSwivelImage, (getTheta(enemiesList[enemyTrack].x, enemiesList[enemyTrack].y, i))), towers[i].x + 7, towers[i].y + 5, 80, 80, this);
                }
            }
        }

        for (int i = 0; i < towerBullets.length; i++) {
            if (towerBullets[i] != null) {
                for (int j = 0; j < towerBullets[i].size(); j++) {

                    if (enemiesList[enemyTrack] != null) {
                        if (setupBullets[i] == true) {
                            //  System.out.println ("i: " + i);
//                            System.out.println("sjdflskdjf " + (bulletSlope[i].size() + "-" + towerBullets[i].size()));

//                            System.out.println(towerBullets[i].get(j).x + ", " + towerBullets[i].get(j).y);
                            g.drawRect(towerBullets[i].get(j).x + 45, towerBullets[i].get(j).y + 40, 20, 20);
                            g.drawImage(bullets[i], towerBullets[i].get(j).x + 45, towerBullets[i].get(j).y + 40, 20, 20, this);
                        }
                    }
                }
            }
        }
    }

    // Gets theta for the angle of the turret nozzle
    public double getTheta(int x2, int y2, int i) {
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

    // Finds the slope of two objects
    // NOTE: A slope of 1 means changing y by - 1 for every + 1 change in x
    // A slope of -1 means changing y by + 1 for every - 1 change in x
    public double getSlope(int x1, int y1, int x2, int y2) {
        double vertical = y1 - y2;
        double horizontal = x2 - x1;

        double slope = Math.abs(vertical / horizontal);

        return slope;
    }

    // Rotates the nozzle image to fit theta
    public BufferedImage rotateImage(BufferedImage image, double theta) {
        int width = (int) Math.round(image.getWidth() / 2.7 + image.getHeight() / 2.7);
        int height = (int) Math.round(image.getWidth() / 2.7 + image.getHeight() / 2.7);

        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        AffineTransform at = new AffineTransform();

        at.translate(width / 2, height / 2);

        at.rotate(Math.toRadians(theta), 0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        rotateOp.filter(image, rotatedImage);

        return rotatedImage;
    }

    public void actionPerformed(ActionEvent event) {
        String click = event.getActionCommand();
    }

    // Gets mouse clicked
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();

//        System.out.println("x: " + x + " y: " + y);

        handleAction(x, y);
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
    public void handleAction(int x, int y) {
        // When on about us pane
        if (aboutUs == true) {
            if (x >= 642 && x <= 721 && y >= 12 && y <= 38) { // Exit button
                picture = "towerDefence";
                startScreen = true;
                inGame = false;
                repaint();
            }
        }

        if (howToPlay == true) {
            if (x >= 642 && x <= 721 && y >= 12 && y <= 38) { // Exit button
                picture = "towerDefence";
                startScreen = true;
                inGame = false;
                repaint();
            }
        }

        // When in start screen
        if (startScreen == true) {
            bgdMusic.start();
            if (x >= 644 && x <= 719 && y >= 507 && y <= 531) { // Exit button
                System.exit(0);

            } else if (x >= 331 && x <= 467 && y >= 194 && y <= 233) { // Start button
//                System.out.println("START");
                picture = "firstMapGrid";
                map = 1;
                startScreen = false;
                inGame = true;
                FPSCOUNT = 1;
                howToPlay = false;
                repaint();

            } else if (x >= 289 && x <= 510 && y >= 274 && y <= 313) { // About us button
//                System.out.println("ABOUT US");
                picture = "aboutUs";
                aboutUs = true;
                startScreen = false;
                inGame = false;
                howToPlay = false;
                repaint();

            } else if (x >= 245 && x <= 563 && y >= 353 && y <= 391) { // How to Play button
//                System.out.println("HOW TO PLAY");
                picture = "howtoplay";
                howToPlay = true;
                aboutUs = false;
                startScreen = false;
                inGame = false;
                repaint();
            }
        }

        // When in game
        if (inGame == true && FPSCOUNT > 30) {
            // Checks if a tower has been clicked yet, if yes - returns pos, if no - returns -1
            int clickedPos = -1;
            for (int i = 0; i < towers.length; i++) {
                if (x > towers[i].x && x < towers[i].x + 100 && y > towers[i].y && y < towers[i].y + 100) {
                    if (clickedTowers[i] == false) {
                        clickedPos = i;
                        clickedTowers[i] = true;
                    }
//                    System.out.print(clickedTowers[i]);
                    break;
                }
            }

            // Adds clicked pos for towers that have been clicked, also records FPS
            if (clickedPos != -1) {
                click.start();
                towerBullets[clickedPos] = new ArrayList<Rectangle>();
                bulletSlope[clickedPos] = new ArrayList<Double>();
                startShot[clickedPos] = FPSCOUNT;
            }
        }
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        frame = new JFrame("Tower Defence");
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setLocation(0, 0);

        Start myPanel = new Start();
        frame.add(myPanel);
        frame.pack();
        frame.setVisible(true);
    }
}

