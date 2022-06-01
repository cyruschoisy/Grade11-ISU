public class GameEntity {

    //Initialize variables
    public static int health;
    public static int speed;

    public void start() {
        //Add the image onto the screen ("spawn it in")
        //Some code

        //Alter animation path for different maps
        if (Game.map == 1) {
            animationPathOne(GameEntity.speed);
        } else if (Game.map == 2) {
            animationPathTwo(GameEntity.speed);
        } else if (Game.map == 3) {
            animationPathThree(GameEntity.speed);
        }
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
    public GameEntity() {
        start();
    }
}
