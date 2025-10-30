import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SoundEffect {
    private Clip clip;
    private FloatControl volume;


    // load from classpath resource (e.g. "/click.wav")
    public SoundEffect(String resourcePath) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        InputStream audioSrc = Dot.class.getResourceAsStream(resourcePath);
        if (audioSrc == null) throw new IOException(resourcePath + " not found in JAR root");
        try (BufferedInputStream bis = new BufferedInputStream(audioSrc);
             AudioInputStream audioStream = AudioSystem.getAudioInputStream(bis)) {
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-0f);
        }
    }

    public void play(float givenVolume) {
        if (clip == null) return;
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        volume.setValue(givenVolume);
        clip.start();
    }

    public void close() {
        if (clip != null) {
            clip.close();
            clip = null;
        }
    }
}