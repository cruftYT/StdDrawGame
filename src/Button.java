import javax.sound.sampled.*;
import java.awt.Color;
import java.io.IOException;

public class Button {
    static SoundEffect click;
    private static float volume = 0;


    public static float getVolume() {
        return volume;
    }

    public static void setVolume(float volume) {
        Button.volume = volume;
    }

    static {
        try {
            click = new SoundEffect("/click.wav");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    private int buttonX, buttonY, buttonLengthX, buttonLengthY;
    private Color defaultColor, hoveringColor;
    public String buttonText = "Button";


    public Button(int givenButtonX, int givenButtonY, int lengthX, int lengthY, String givenButtonText, Color givenDefaultColor, Color givenHoveringColor) {
        this.buttonX = givenButtonX;
        this.buttonY = givenButtonY;
        this.buttonLengthX = lengthX;
        this.buttonLengthY = lengthY;
        this.buttonText = givenButtonText;
        this.defaultColor = givenDefaultColor;
        this.hoveringColor = givenHoveringColor;
    }

    public boolean mouseHovering(double mouseX, double mouseY) {
        int left = buttonX - buttonLengthX;
        int right = buttonX + buttonLengthX;
        int bottom = buttonY - buttonLengthY;
        int top = buttonY + buttonLengthY;

        return (mouseX >= left && mouseX <= right && mouseY >= bottom && mouseY <= top);
    }
    
    public boolean isPressed(double mouseX, double mouseY, float soundVolume){
        if((mouseHovering(mouseX , mouseY) && StdDraw.isMousePressed())){
            click.play(volume);
        }
        return (mouseHovering(mouseX , mouseY) && StdDraw.isMousePressed());
    }

    public void draw(double mouseX, double mouseY){
        if (mouseHovering(mouseX, mouseY)){
            StdDraw.setPenColor(hoveringColor);
        }
        else{
            StdDraw.setPenColor(defaultColor);
        }
        StdDraw.filledRectangle(buttonX,buttonY,buttonLengthX,buttonLengthY);
        StdDraw.setPenColor(Color.black);
        StdDraw.text(buttonX,buttonY,buttonText);
    }

    public int getButtonX() {
        return buttonX;
    }

    public void setButtonX(int buttonX) {
        this.buttonX = buttonX;
    }

    public int getButtonY() {
        return buttonY;
    }

    public void setButtonY(int buttonY) {
        this.buttonY = buttonY;
    }

    public int getButtonLengthX() {
        return buttonLengthX;
    }

    public void setButtonLengthX(int buttonLengthX) {
        this.buttonLengthX = buttonLengthX;
    }

    public int getButtonLengthY() {
        return buttonLengthY;
    }

    public void setButtonLengthY(int buttonLengthY) {
        this.buttonLengthY = buttonLengthY;
    }
}
