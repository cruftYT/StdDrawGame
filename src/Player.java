public class Player {
    private int playerX;
    private int playerY;
    private int playerSpeed;
    private int playerLives;

    public Player(int X, int Y, int speed, int lives){
        this.playerX = X;
        this.playerY = Y;
        this.playerSpeed = speed;
        this.playerLives = lives;
    }

    public int getPosX(){
        return playerX;
    }
    public int getPosY(){
        return playerY;
    }
    public int getSpeed(){
        return playerSpeed;
    }
    public int getLives(){
        return playerLives;
    }

    public void setPlayerSpeed(int speed){
        playerSpeed = speed;
    }
    public void setPlayerPos(int X, int Y){
        playerX = X;
        playerY = Y;

    }
    public void setPlayerLives(int lives){
        playerLives = lives;
    }

    public void incrementPos(int X, int Y){
        playerX += X;
        playerY += Y;
    }
    public void incrementLives(int amount){
        playerLives += amount;
    }

    public void draw(int radius){
        StdDraw.filledCircle(playerX,playerY,radius);
    }


}
