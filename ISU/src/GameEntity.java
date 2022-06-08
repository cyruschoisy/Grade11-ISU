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

    Path currentRelativePath = Paths.get("");
    String root = currentRelativePath.toAbsolutePath().toString();

    public Image enemyOne;

    public GameEntity () {

        System.out.println("LOADING THE ENEMY");
        loadImage();

//        //Alter animation path for different maps
//        if (Game.map == 1) {
//            animationPathOne(GameEntity.speed);
//        } else if (Game.map == 2) {
//            animationPathTwo(GameEntity.speed);
//        } else if (Game.map == 3) {
//            animationPathThree(GameEntity.speed);
//        }
    }

    public void loadImage() {
        String enemyPath = root + "\\ISU\\media\\enemyOne.png";
        System.out.println(enemyPath);
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
