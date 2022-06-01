public class Game {

    public static void Main (String[] args) {

        //Initialize 2D Array
        int[][] board;

        //Set the health and speed of the enemies
        GameEntity.health = 1;
        GameEntity.speed = 100;

        //Instantiate the object
        GameEntity enemy = new GameEntity(1, 100);

    }

}
