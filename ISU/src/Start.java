/*
Cyrus Choi, Christian Fisla
June 21th, 2022

Tower Defense! This is a game where the player will be given a certain amount of money to defend the incoming ghosts. However,
the player must choose the tower locations wisely, as they are not cheap and some spots on the grid are better than others.
As the game progresses, the incoming ghosts will be harder to defend and reward you with less money. In addition, towers will
become more expensive. See how long you can last, you will win the game when 250 ghosts are defeated. Good luck!

 */

// Import statements
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

import javax.swing.*;

// Main class
public class Start extends JPanel implements Runnable, MouseListener {
    //Initialize
    public static JFrame frame;
    public static double slope;
    // Initializing all our images in the program
    public static BufferedImage enemyImage;
    public static BufferedImage towerBaseImage;
    public static BufferedImage towerSwivelImage;
    public static BufferedImage bullet;
    public static BufferedImage rotatedBullet;

    // FPS / How many times the screen updates
    public final int FPS = 30;
    public int FPSCOUNT = 0;
    // Index of the current enemy for the towers to track
    public int enemyTrack = 0;
    // Current background picture
    String picture = "towerDefence";
    // X, Y variables to handle mouse actions
    int x, y;
    //Variable to keep track of how many enemies have been defeated so far
    int enemiesKilled = 0;

    // Boolean variables to toggle different screens in the menu
    boolean startScreen = true;
    boolean aboutUs = false;
    boolean howToPlay = false;
    boolean inGame = false;
    boolean lost = false;
    boolean won = false;

    // Variable to increase of decrease total thread.sleep() time. Using a variable to hold this value is useful
    // for dynamically changing the computing time when more enemies are on the screen
    int computeTime = 500;

    // A boolean array to determine which grid spot has been clicked, if any
    boolean[] clickedTowers = new boolean [48];

    // Keeps track of all enemies that have been spawned
    int enemyCount = 0;

    Rectangle[] enemiesList = new Rectangle [250]; // Number of enemies
    Rectangle[] towers = new Rectangle [48]; // Initializing rectangle array for towers
    ArrayList <Rectangle> [] towerBullets = new ArrayList [500]; // Writes array in arrayList
    ArrayList <Double> bulletSlope [] = new ArrayList[500]; // ArrayList for slope of each bullet
    BufferedImage bullets [] = new BufferedImage [500]; // Bullets buffered image
    Boolean setupBullets [] = new Boolean[500]; // Saves the state of all bullets

    int interval = 100; // Frame gap between enemy spawns
    int money = 750; // Amount of money the user has
    int costOfTower = 500; // The amount of money a new tower will cost
    double tempTheta = 270; // A variable to store the temporary angle of the tower's turret. This way, they can stay in the same spot when there are no enemies currently on the screen to look at
    Clip bgdMusic, click; // Initialization of background music
    Image cursorImage; // Initialize cursor variable

    // Constructor
    public Start() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // Initialize the layout of our frame
        setLayout(new BorderLayout());
        addMouseListener(this);

        // Start thread
        Thread thread = new Thread(this);
        thread.start();

        // Set up the icon image (Tracker not needed for the icon image)
        Image iconImage = Toolkit.getDefaultToolkit().getImage("enemyOne.png");
        frame.setIconImage(iconImage);

        // Change all bullet setups to false -> they have not been setup yet
        for (int i = 0; i < setupBullets.length; i++) {
            setupBullets[i] = false;
        }

        // Set up the cursor
        cursorImage = Toolkit.getDefaultToolkit().getImage("cursor.png");

        Point hotspot = new Point (0, 0);
        Toolkit toolkit = Toolkit.getDefaultToolkit ();
        Cursor cursor = toolkit.createCustomCursor (cursorImage, hotspot, "cursor");
        frame.setCursor (cursor);

        try {
            // Pull our "sprite" images
            enemyImage = ImageIO.read(new File("enemyOne.png"));
            towerBaseImage = ImageIO.read(new File("towerBase.png"));
            towerSwivelImage = ImageIO.read(new File("towerSwivelLarge2.png"));
            bullet = ImageIO.read(new File("bullet.png"));

            // Initialization of background music
            AudioInputStream sound = AudioSystem.getAudioInputStream(new File("music.wav"));
            bgdMusic = AudioSystem.getClip();
            bgdMusic.open(sound);

            // Initialization of click music
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
            // Call the update function in an infinite loop to constantly update animations, etc...
            update();
            this.repaint();
            FPSCOUNT++;
            try {
                // Pause the thread using the computeTime variable to increase or decrease the time spend processing
                Thread.sleep(computeTime / FPS);
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

    // Checks if a bullet has collided with any ghost
    public boolean checkCollision(Rectangle bullet) {
        // Loop through all enemies
        for (int j = 0; j < enemiesList.length; j++) {
            // Only continue if said enemy is NOT null
            if (enemiesList[enemyCount - 1] != null) {
                // If the enemy has intersected with a bullet
                if (enemiesList[j] != null && enemiesList[j].intersects(bullet)) {
                    // then award the player money, depending on how early or far they are into the game
                    if (enemiesKilled <= 50) {
                        money += 40;
                    } else if (enemiesKilled >= 51 && enemiesKilled <= 100) {
                        money += 35;
                    } else if (enemiesKilled >= 101 && enemiesKilled <= 175) {
                        money += 30;
                    } else {
                        money += 25;
                    }

                    // Take out the ghost by making its position null
                    // The array is passed by reference, so we don't need to return it!
                    enemiesList[j] = null;
                    enemiesKilled++; // Increase the enemy death toll

                    // Re-configure which enemy is supposed to be tracked
                    // If the leading ghost has died -> follow to the next leading ghost
                    // If any other ghost has died -> reconfigure to the next ghost that isn't null -> leading ghost
                    for (int i = 0; i < enemiesList.length; i++) {
                        if (enemiesList[i] != null) {
                            enemyTrack = i;
                            break; // Once we've found the next target, exit the loop!
                        }
                    }
                    // Return true if any bullet has collided with any ghost in the given frame
                    return true;
                }
            }
        }
        // If not, return false
        return false;
    }


    // Goes through each bullet and moves them, if it's off the page, it will remove the bullet
    public void updateBullets() {
        //Check to see if it is time to add a new bullet to each tower
        for (int i = 0; i < towerBullets.length; i++) {
            // Checks to make sure another bullet should be added and drawn in if there are currently enemies on the screen and a tower has been successfully placed
            if (towerBullets[i] != null && enemiesList [enemyTrack] != null && clickedTowers[i] == true) {
                // Every 115 frames, run the code to "shoot" the bullet
                if ((FPSCOUNT) % 115 == 0) {

                    // Append a new rectangle to the arraylist
                    towerBullets[i].add(new Rectangle(towers[i]));

                    // Call getTheta() with the position of the currently tracked enemies and save the angle to a variable
                    double angle = getTheta(enemiesList[enemyTrack].x, enemiesList[enemyTrack].y, i);

                    // Change the angle depending on where the enemy stands in relation to the tower
                    if (towers[i].x > enemiesList[enemyTrack].x) {
                        angle = angle - 180;
                    }

                    // Rotate the image of the bullet, in accordance with the angle and save it to a variable
                    rotatedBullet = rotateImage(bullet, angle);

                    // Use the bullets array to save the rotated image of the bullet
                    bullets[i] = rotatedBullet;

                    // Get the slope of the bullet's trajectory and save it to a variable
                    slope = getSlope(towers[i].x + 40, towers[i].y + 35, enemiesList[enemyTrack].x + 40, enemiesList[enemyTrack].y + 35);

                    // Save the slope to an array list
                    bulletSlope[i].add(slope);

                    // Change the setup state to true -> we do not need to calculate the rotated image and slope for this particular bullet anymore
                    setupBullets[i] = true;
                }
            }
        }

        // Loop through all the tower bullets
        for (int i = 0; i < towerBullets.length; i++) {
            // Only proceed if not null
            if (towerBullets[i] != null) {
                // Loops through all the bullets and adds the slope to them appropriately
                for (int j = 0; j < towerBullets[i].size(); j++) {
                    if (towerBullets[i] != null && enemiesList[enemyTrack] != null) {
                        // Straight up
                        if (towers[i].x == enemiesList[enemyTrack].x && towers[i].y > enemiesList[enemyTrack].y) {
                            towerBullets[i].get(j).y -= bulletSlope[i].get(j) * 15;
                        }
                        // Right
                        else if (towers[i].x < enemiesList[enemyTrack].x && towers[i].y == enemiesList[enemyTrack].y) {
                            towerBullets[i].get(j).x += 15;
                        }
                        // Left
                        else if (towers[i].x > enemiesList[enemyTrack].x && towers[i].y == enemiesList[enemyTrack].y) {
                            towerBullets[i].get(j).x -= 15;
                        }
                        // Top left
                        else if (towers[i].x > enemiesList[enemyTrack].x && towers[i].y > enemiesList[enemyTrack].y) {
                            towerBullets[i].get(j).x -= 15;
                            towerBullets[i].get(j).y -= bulletSlope[i].get(j) * 15;
                        }
                        // Top right
                        else if (towers[i].x < enemiesList[enemyTrack].x && towers[i].y > enemiesList[enemyTrack].y) {
                            towerBullets[i].get(j).x += 15;
                            towerBullets[i].get(j).y -= bulletSlope[i].get(j) * 15;
                        }
                        // Bottom left
                        else if (towers[i].x > enemiesList[enemyTrack].x && towers[i].y < enemiesList[enemyTrack].y) {
                            towerBullets[i].get(j).x -= 15;
                            towerBullets[i].get(j).y += bulletSlope[i].get(j) * 15;
                        }
                        // Bottom right
                        else if (towers[i].x < enemiesList[enemyTrack].x && towers[i].y < enemiesList[enemyTrack].y) {
                            towerBullets[i].get(j).x += 15;
                            towerBullets[i].get(j).y += bulletSlope[i].get(j) * 15;
                        }
                        // Run check collision. Only if it returns true, proceed with removing the bullet
                        // this will ensure that it only collides with a single ghost
                        if (checkCollision(towerBullets[i].get(j)) || towerBullets[i].get(j).x >= 800 || towerBullets[i].get(j).y >= 800 || towerBullets[i].get(j).x <= 0 || towerBullets[i].get(j).y <= 0) {
                            towerBullets[i].remove(j);
                            bulletSlope[i].remove(j);
                            j--;
                        }
                    }
                }
            }
        }
    }

    // Moves the enemy
    public void moveEnemy() {
        // Spawn a new enemy every certain frame counts
        if (inGame == true && FPSCOUNT % interval == 0 && enemyCount < 250) {
            enemiesList[enemyCount++] = new Rectangle(-100, 250, 100, 100);
        }

        // Makes the enemies draw in quicker
        if (enemyCount <= 9) {
            interval = 100;
        }

        else if (enemyCount >= 10 && enemyCount <= 19) {
            interval = 85;
            computeTime = 350;
        }

        else if (enemyCount >= 20 && enemyCount <= 29) {
            interval = 75;
            computeTime = 335;
        }

        else if (enemyCount >= 30 && enemyCount <= 39) {
            interval = 50;
            computeTime = 320;
        }

        else if (enemyCount >= 40 && enemyCount <= 99) {
            interval = 35;
            computeTime = 305;
        }

        else if (enemyCount >= 100 && enemyCount < 175) {
            interval = 30;
            computeTime = 200;
        }

        else if (enemyCount >= 176 && enemyCount < 249) {
            interval = 25;
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

                // If past 800 pixels, user will lose
                if (enemiesList[i].x > 800) {
                    enemiesList[i] = null;
                    for (int p = 0; p < enemiesList.length; p++) {
                        if (enemiesList[p] != null) {
                            enemyTrack = p;
                            lost = true;
                        }
                    }
                }
            }
        }
    }

    // Paint Component
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Render different ending screens if the user has one or lost
        if (lost) {
            startScreen = false;
            inGame = false;
            picture = "lost";
        }

        if (enemiesKilled == 249) {
            startScreen = false;
            inGame = false;
            won = true;
            picture = "winner";
        }

        // Get the current path
        Path currentRelativePath = Paths.get("");
        String root = currentRelativePath.toAbsolutePath().toString();

        // Append the path to the directory and current picture
        String splashImagePath = root + "/ISU/media/" + picture + ".png";
        File splashImage = new File(splashImagePath);

        Image background;

        // Check if the image is available. If not, notify in console
        if (splashImage.exists() && !splashImage.isDirectory()) {
            background = Toolkit.getDefaultToolkit().getImage(splashImagePath);
            g.drawImage(background, 0, 0, 800, 561, this);
        } else {
            System.out.println("IMAGE CANNOT BE FOUND");
        }

        // If "start" has been pressed -> in game
        if (inGame == true) {
            // Draw the labels for balance, costOfTower, and Enemies Killed
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString("Balance: " + Integer.toString (money), 550, 525);
            g.drawString("Cost of Tower: " + Integer.toString (costOfTower), 35, 525);
            g.drawString("Enemies Killed: " + Integer.toString (enemiesKilled), 35, 45);

            // Draws each enemy image
            for (int i = 0; i < enemyCount; i++) {
                if (enemiesList[i] != null) {
                    g.drawImage(enemyImage, enemiesList[i].x, enemiesList[i].y, 100, 100, this);
                }
            }
        }

        // Drawing tower image
        for (int i = 0; i < towers.length; i++) {
            if (clickedTowers[i] == true && inGame == true) {

                // Always draw the base of the tower
                g.drawImage(towerBaseImage, towers[i].x + 7, towers[i].y + 5, 80, 80, this);

                // Once the game has started check if any enemies are on the screen yet
                if (FPSCOUNT > 0) {
                    if (enemiesList[enemyTrack] != null) {
                        // If they've spawned yet, assign the angle at which to rotate to tempTheta, then...
                        tempTheta = getTheta(enemiesList[enemyTrack].x, enemiesList[enemyTrack].y, i);
                        // ...draw the "turret" image seperately in its correct rotation
                        g.drawImage(rotateImage(towerSwivelImage, (getTheta(enemiesList[enemyTrack].x, enemiesList[enemyTrack].y, i))), towers[i].x + 7, towers[i].y + 5, 80, 80, this);
                    } else {
                        // If there are no enemies on the screen...
                        if (enemiesKilled != 249) {
                            if (enemiesList[enemyTrack + 1] != null) {
                                enemyTrack++;
                            }
                        }
                        // Draw the "turret" in the default position -> "tempTheta" default value is 270
                        // Otherwise, use tempTheta to draw the turret in the latest position
                        g.drawImage(rotateImage(towerSwivelImage, tempTheta), towers[i].x + 7, towers[i].y + 5, 80, 80, this);
                    }
                }
            }
        }
        // Draws each tower bullet
        for (int i = 0; i < towerBullets.length; i++) {
            if (towerBullets[i] != null) {
                for (int j = 0; j < towerBullets[i].size(); j++) {
                    // Sets up each bullet, draws the bullet image
                    if (enemiesList[enemyTrack] != null && !lost && !won) {
                        if (setupBullets[i] == true) {
                            g.drawImage(bullets[i], towerBullets[i].get(j).x + 40, towerBullets[i].get(j).y + 35, 20, 20, this);
                        }
                    }
                }
            }
        }
    }

    // Gets theta for the angle of the turret nozzle
    public double getTheta(int x2, int y2, int i) {

        // Assigns the rower position to a local variable for ease of use
        int x1 = towers[i].x;
        int y1 = towers[i].y;

        double hypotenuse;

        // Calculate the hypotenuse of our imaginary triangle between the tower and the ghost
        // We can do this using the distance formula, inputting both points
        hypotenuse = Math.sqrt(((y2 - y1) * (y2 - y1)) + ((x2 - x1) * (x2 - x1)));

        // Find out the vertical length of the triangle, or the "opposite" side
        double vertical = y1 - y2;

        // Now that we know to sides, isolate for theta
        double theta = (Math.asin(vertical / hypotenuse));

        // Trigonometric functions return radians, so we need to convert it to degrees
        theta = Math.toDegrees(theta);

        // Depending on the position of te ghost, add 180 degrees to negative theta
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
        click.setMicrosecondPosition(0);
        click.start();

        x = e.getX();
        y = e.getY();

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
        // If exit button on how to play screen is clicked
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
            if (x >= 644 && x <= 719 && y >= 507 && y <= 531) { // Exit button
                System.exit(0);

            } else if (x >= 331 && x <= 467 && y >= 194 && y <= 233) { // Start button
                picture = "firstMapGrid";
                startScreen = false;
                inGame = true;
                FPSCOUNT = 1;
                howToPlay = false;
                bgdMusic.loop(Clip.LOOP_CONTINUOUSLY);
                bgdMusic.start();
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
                if (towers[i] != null) {
                    if (x > towers[i].x && x < towers[i].x + 100 && y > towers[i].y && y < towers[i].y + 100) {
                        if (clickedTowers[i] == false) {
                            clickedPos = i;
                            if (money >= costOfTower) { // Only allow a purchase to be made if the user has enough money
                                clickedTowers[i] = true;
                                money -= costOfTower;

                                // Increase the cost of the tower for every purchase to make it harder
                                if (costOfTower <= 1250) {
                                    costOfTower += 250;
                                } else if (costOfTower <= 2500) {
                                    costOfTower += 500;
                                } else {
                                    costOfTower += 1000;
                                }
                            }
                        }
                        break;
                    }
                }
            }

            // Adds clicked pos for towers that have been clicked, also records FPS
            if (clickedPos != -1) {

                towerBullets[clickedPos] = new ArrayList<Rectangle>();
                bulletSlope[clickedPos] = new ArrayList<Double>();
            }
        }
    }

    // Main method
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