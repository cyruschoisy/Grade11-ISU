import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class GameEntity {

    //Initialize variables
    public int health;
    public int speed;

    public  Image enemyOne;

    public GameEntity () {
        //Add the image onto the screen ("spawn it in")
        enemyOne = Toolkit.getDefaultToolkit().getImage("/media/enemyOne.png");
        health = 100;
        speed = 10;
//        //Alter animation path for different maps
//        if (Game.map == 1) {
//            animationPathOne(GameEntity.speed);
//        } else if (Game.map == 2) {
//            animationPathTwo(GameEntity.speed);
//        } else if (Game.map == 3) {
//            animationPathThree(GameEntity.speed);
//        }
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

    //Main method
//    public void Main() {
//        start();
//    }
}
