import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GameEntity {

    //Initialize variables
    public int health;
    public int speed;
    private int h;
    private int w;

    public static int[] Map1EnemyMovementX = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
    public static int[] Map1EnemyMovementY = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static int[] MapEnemyCount = {2, 3, 4, 6, 8, 10, 12, 14, 15, 20};

    public static int enemyOneDistance = 5;

    Path currentRelativePath = Paths.get("");
    String root = currentRelativePath.toAbsolutePath().toString();

    public Image enemyOne;

    public GameEntity () {
        System.out.println("LOADING THE ENEMY");
        loadImage();
    }

    public void loadImage() {
        String enemyPath = root + "\\ISU\\media\\enemyOne.png";
        ImageIcon entity = new ImageIcon(enemyPath);
        enemyOne = entity.getImage();
        w = enemyOne.getWidth (null);
        h = enemyOne.getHeight (null);
    }

    public Image getImage() {
        return enemyOne;
    }

    public static void animationPathOne(int speed) {
        //Describe the path of the entity
    }

    public static void animationPathTwo(int speed) {
        //Describe the path of the entity
    }

    public static void animationPathThree(int speed) {
        //Describe the path of the entity
    }

}
