import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class GameEntity {

    //Initialize variables
    public int health;
    public int speed;
    private int h;
    private int w;

    public Image enemyOne;

    public GameEntity () {
        //Add the image onto the screen ("spawn it in")

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
        ImageIcon entity = new ImageIcon("/media/enemyOne.png");
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
