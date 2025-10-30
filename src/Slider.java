import java.awt.*;

public class Slider {
    private float min, max;
    private float currentValue;
    private int hitboxX, hitboxY, hitboxLengthX, hitboxLengthY;
    private int handleX, handleY, handleLengthX, handleLengthY;
    private String sliderName;
    private Color defaultColor, hoveringColor;
    private boolean clicked = false;

    public Slider(float value, float minimum, float maximum, int x, int y, int lengthX, int lengthY, int givenHandleLengthX, int givenHandleLengthY, String name, Color resting, Color hovering){
        this.min = minimum;
        this.max = maximum;
        this.currentValue = value;
        this.hitboxX = x;
        this.hitboxY = y;
        this.hitboxLengthX = lengthX;
        this.hitboxLengthY = lengthY;
        this.handleX = hitboxX;
        this.handleY = hitboxY + hitboxLengthY;
        this.handleLengthX = givenHandleLengthX;
        this.handleLengthY = givenHandleLengthY;
        this.sliderName = name;
        this.defaultColor = resting;
        this.hoveringColor = hovering;

    }

    public boolean mouseHoveringOverHandle(double mouseX, double mouseY) {
        int left = handleX - handleLengthX;
        int right = handleX + handleLengthX;
        int bottom = handleY - handleLengthY;
        int top = handleY + handleLengthY;

        return (mouseX >= left && mouseX <= right && mouseY >= bottom && mouseY <= top);
    }

    public boolean mouseHovering(double mouseX, double mouseY) {
        int left = hitboxX - hitboxLengthX;
        int right = hitboxX + hitboxLengthX;
        int bottom = hitboxY - hitboxLengthY;
        int top = hitboxY + hitboxLengthY;

        return (mouseX >= left && mouseX <= right && mouseY >= bottom && mouseY <= top);
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getCurrentValue(){
        return currentValue;
    }

    public void setCurrentValue(float value){
        this.currentValue = value;
    }

    public int getHitboxX() {
        return hitboxX;
    }

    public void setHitboxX(int hitboxX) {
        this.hitboxX = hitboxX;
    }

    public int getHitboxY() {
        return hitboxY;
    }

    public void setHitboxY(int hitboxY) {
        this.hitboxY = hitboxY;
    }

    public int getHitboxLengthX() {
        return hitboxLengthX;
    }

    public void setHitboxLengthX(int hitboxLengthX) {
        this.hitboxLengthX = hitboxLengthX;
    }

    public int getHitboxLengthY() {
        return hitboxLengthY;
    }

    public void setHitboxLengthY(int hitboxLengthY) {
        this.hitboxLengthY = hitboxLengthY;
    }

    public int getHandleX() {
        return handleX;
    }

    public void setHandleX(int handleX) {
        this.handleX = handleX;
    }

    public int getHandleY() {
        return handleY;
    }

    public void setHandleY(int handleY) {
        this.handleY = handleY;
    }

    public int getHandleLengthX() {
        return handleLengthX;
    }

    public void setHandleLengthX(int handleLengthX) {
        this.handleLengthX = handleLengthX;
    }

    public int getHandleLengthY() {
        return handleLengthY;
    }

    public void setHandleLengthY(int handleLengthY) {
        this.handleLengthY = handleLengthY;
    }

    public String getSliderName() {
        return sliderName;
    }

    public void setSliderName(String sliderName) {
        this.sliderName = sliderName;
    }

    public void draw(double mouseX, double mouseY){
        StdDraw.setPenColor(new Color(0xC1C1C1));
        StdDraw.filledRectangle(hitboxX,hitboxY,hitboxLengthX,hitboxLengthY);
        StdDraw.setPenColor(Color.black);
        StdDraw.setPenRadius(0.005);
        StdDraw.line(hitboxX, (hitboxY+hitboxLengthY)-5, hitboxX, (hitboxY-hitboxLengthY)+5);
        if(mouseHoveringOverHandle(mouseX,mouseY)){
            StdDraw.setPenColor(hoveringColor);
        }
        else{
            StdDraw.setPenColor(defaultColor);
        }
        StdDraw.filledRectangle(handleX,handleY,handleLengthX,handleLengthY);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(hitboxX,(hitboxY-hitboxLengthY-25), sliderName);
        StdDraw.text(hitboxX,(hitboxY+hitboxLengthY+20), "%" + (int) (100 - (calculatePercentage() * 100)));

    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public float calculatePercentage(){
        int top = hitboxY + hitboxLengthY;
        int bottom = hitboxY - hitboxLengthY;
        currentValue = (float)(top - getHandleY()) / (top - bottom);

        return currentValue;
    }
    
    public void update(double mouseX, double mouseY){
        if(this.mouseHovering(mouseX, mouseY) && StdDraw.isMousePressed()){
            this.setClicked(true);
        }
        if(this.isClicked() && StdDraw.isMousePressed()){
            if(mouseY >= ((this.getHitboxY() - this.getHitboxLengthY())-2) && mouseY <= ((this.getHitboxY() + this.getHitboxLengthY()))+2) {
                this.setHandleY((int) mouseY);
            } else if (mouseY > ((this.getHitboxY() + this.getHitboxLengthY()))+2) {
                this.setHandleY(this.getHitboxY() + this.getHitboxLengthY());

            }else{
                this.setHandleY(this.getHitboxY() - this.getHitboxLengthY());
            }
        }else{
            this.setClicked(false);
        }
    }
}
