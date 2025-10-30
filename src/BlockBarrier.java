
public class BlockBarrier{
    private float blockX, blockY, blockLengthX, blockLengthY;
    public String blockName = "Barrier";


    public BlockBarrier(int givenBlockX, int givenBlockY, int lengthX, int lengthY, String givenBlockName){
        this.blockX = givenBlockX;
        this.blockY = givenBlockY;
        this.blockLengthX = lengthX;
        this.blockLengthY = lengthY;
        this.blockName = givenBlockName;
    }
    
    public boolean checkForCollision(Player collidingPlayer){
        float left = blockX - blockLengthX;
        float right = blockX + blockLengthX;
        float bottom = blockY - blockLengthY;
        float top = blockY + blockLengthY;

        return (collidingPlayer.getPosX() >= left && collidingPlayer.getPosX() <= right && collidingPlayer.getPosY() >= bottom && collidingPlayer.getPosY() <= top);
    }

    public void draw(){
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledRectangle(this.blockX, this.blockY, this.blockLengthX, this.blockLengthY);
        StdDraw.setPenColor(StdDraw.BLACK);
    }

    public float getBlockX(){
        return blockX;
    }

    public float getBlockY(){
        return blockY;
    }

    public float getBlockLengthX(){
        return blockLengthX;
    }

    public float getBlockLengthY(){
        return blockLengthY;
    }

    public void setBlockX(int value){
        this.blockX = value;
    }
    public void setBlockY(int value){
        this.blockY = value;
    }
    public void setBlockLengthX(int value){
        this.blockLengthX = value;
    }
    public void setBlockLengthY(int value){
        this.blockLengthY = value;
    }
    public void incrementBlockX(float value){
        this.blockX += value;
    }
    public void incrementBlockY(float value){
        this.blockY += value;
    }


}

//double x, double y, double halfWidth, double halfHeight