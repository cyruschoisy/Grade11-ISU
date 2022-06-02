public class Game {

    //Initialize variables
    public static int map;
    public static int wave;

    //Initialize 2D Array
    //20 rows, 15 columns
    //Each box should be 40 x 40 pixels large
    public static int[][] board = new int[15][20];

    //Main method
    public static void Main () {

        //Set the health and speed of the enemies
        GameEntity.health = 1;
        GameEntity.speed = 100;

        //Instantiate the object
        GameEntity enemy = new GameEntity();

    }

}
