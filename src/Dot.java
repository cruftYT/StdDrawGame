// Name: Anas
import javax.sound.sampled.*;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Random;
import java.awt.event.KeyEvent;

public class Dot {
    static DecimalFormat difficultyFormat = new DecimalFormat("0%");
    static float difficulty = 0.5f;
    static Button startButton = new     Button(400, 200, 120, 40, "Start",new Color(0x3DB614), new Color(0x88D16E));
    static Button settingsButton = new Button(400, 115, 120, 40, "Settings",new Color(0x139DFF), new Color(0x5FB2FA));
    static Button exitSettings = new Button(770,770,20,20,"X",new Color(0xF41A1A), new Color(0xD16E6E));
    static Button mainMenuButton = new Button(150, 200, 120, 40, "Main Menu",new Color(0x139DFF), new Color(0x5FB2FA));
    static Button restartButton = new Button(650, 200, 120, 40, "Restart",new Color(0x3DB614), new Color(0x88D16E));
    static Button replayButton = new Button(400, 200, 120, 40, "Respawn",new Color(0x80B614), new Color(0xA1D16E));
    static Button testSound = new Button(400, 600, 200, 40, "Boop! (test sound)",new Color(0xFFF313), new Color(0xFFF0B4));

    static Slider musicVolume = new Slider(100,0,50,600,400,10,100, 20,10,"Music Volume", new Color(0xBDBDBD), new Color(0xB1CFED));
    static Slider sfxVolume = new Slider(0,0,50,200,400,10,100, 20,10,"SFX Volume", new Color(0xBDBDBD), new Color(0xB1CFED));

    static boolean gameOver = false;
    static boolean settingsScreen = false;



    public static void reset(BlockBarrier b1, BlockBarrier b2, BlockBarrier b3, BlockBarrier b4, Player player) {
        b1.setBlockX(400);
        b2.setBlockX(400);
        b3.setBlockX(700);
        b4.setBlockX(100);
        b1.setBlockY(700);
        b2.setBlockY(100);
        b3.setBlockY(400);
        b4.setBlockY(400);
        player.setPlayerPos(400,400);

        StdDraw.clear(StdDraw.WHITE);
        StdDraw.text(400,400,"You Died! Lives: " + player.getLives());
        StdDraw.show();
        StdDraw.clear(StdDraw.WHITE);
        if(player.getLives() > 0) {
            do {
                StdDraw.text(400,400,"You Died! Lives: " + player.getLives());
                replayButton.draw(StdDraw.mouseX(), StdDraw.mouseY());
                StdDraw.show();
                StdDraw.clear();
            } while (!replayButton.isPressed(StdDraw.mouseX(), StdDraw.mouseY(),sfxVolume.getCurrentValue()));
        }
        else{
            gameOver = true;
        }
    }

    public static void titleScreen(FloatControl volume){
        // --------------------------TITLE SCREEN LOOP--------------------------
        while(true){
            difficulty = 0.5f;
            gameOver = false;
            StdDraw.picture(400,544,"title.png",800,512);
            startButton.draw(StdDraw.mouseX(), StdDraw.mouseY());
            settingsButton.draw(StdDraw.mouseX(), StdDraw.mouseY());
            if(startButton.isPressed(StdDraw.mouseX(), StdDraw.mouseY(),sfxVolume.getCurrentValue())){
                break;
            }
            if(settingsButton.isPressed(StdDraw.mouseX(), StdDraw.mouseY(),sfxVolume.getCurrentValue())){
                settingsScreen = true;
            }
            StdDraw.show();
            StdDraw.clear();
            while (settingsScreen){
                if (musicVolume.calculatePercentage() < 1.0f ){
                    volume.setValue(-30 * musicVolume.calculatePercentage());
                }
                else{
                    volume.setValue(-80.0f);
                }

                if (sfxVolume.calculatePercentage() < 1.0f ){
                    Button.setVolume((-30 * sfxVolume.calculatePercentage()));
                }
                else{
                    Button.setVolume(-80f);
                }
                StdDraw.textLeft(5,10,"song: purrfect day :3 by koteon");
                StdDraw.show();
                StdDraw.clear();
                exitSettings.draw(StdDraw.mouseX(), StdDraw.mouseY());
                testSound.draw(StdDraw.mouseX(), StdDraw.mouseY());
                testSound.isPressed(StdDraw.mouseX(), StdDraw.mouseY(),sfxVolume.getCurrentValue());
                musicVolume.draw(StdDraw.mouseX(), StdDraw.mouseY());
                musicVolume.update(StdDraw.mouseX(), StdDraw.mouseY());
                sfxVolume.draw(StdDraw.mouseX(), StdDraw.mouseY());
                sfxVolume.update(StdDraw.mouseX(), StdDraw.mouseY());
                if(exitSettings.isPressed(StdDraw.mouseX(), StdDraw.mouseY(),sfxVolume.getCurrentValue())){
                    settingsScreen = false;
                }
                if(testSound.isPressed(StdDraw.mouseX(), StdDraw.mouseY(),sfxVolume.getCurrentValue()) && testSound.getButtonY() == 600){
                    testSound.setButtonY(200);
                }
                else if (testSound.isPressed(StdDraw.mouseX(), StdDraw.mouseY(),sfxVolume.getCurrentValue()) && testSound.getButtonY() != 700){
                    testSound.setButtonY(600);
                }

            }
        }
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        //Music
        FloatControl volume;
        InputStream audioSrc = Dot.class.getResourceAsStream("/music.wav");
        if (audioSrc == null) throw new IOException("music.wav not found in JAR root");
        try (BufferedInputStream bis = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bis)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            // set volume
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-0f);
            clip.start();
        }
        //Variables
        Random random = new Random();
        final int TARGET_FPS = 1000 / 180;

        //Canvas
        StdDraw.setCanvasSize(500, 500);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 800);
        StdDraw.setPenRadius(0.01);
        StdDraw.enableDoubleBuffering(); // enable buffered drawing

        StdDraw.setPenColor(StdDraw.BLACK);

        //Game Objects
        Player playerOne = new Player(400,400,5,5);
        BlockBarrier westBarrier = new BlockBarrier(100, 400, 50, 50, "Erratic barrier");
        BlockBarrier eastBarrier = new BlockBarrier(700, 400, 50, 50, "Corner Moving barrier");
        BlockBarrier northBarrier = new BlockBarrier(400, 700, 50, 50, "Sliding barrier");
        BlockBarrier southBarrier = new BlockBarrier(400, 100, 50, 50, "Tracking barrier");

        //More Variables
        long lastTime = System.nanoTime();
        int frames = 0;
        double fps = 0;
        int score = 0;
        int SlidingBarrierX = (random.nextInt(70) + 1) * 10;
        int SlidingBarrierY = (random.nextInt(70) + 1) * 10;

        //Initial title screen loop
        titleScreen(volume);


        // --------------------------MAIN GAME LOOP--------------------------
        //noinspection InfiniteLoopStatement
        while(true){
            StdDraw.clear();
            long lastScoreTime = System.currentTimeMillis();

            while(gameOver) {
                difficulty = 0.5f;
                StdDraw.clear();
                mainMenuButton.draw(StdDraw.mouseX(), StdDraw.mouseY());
                restartButton.draw(StdDraw.mouseX(), StdDraw.mouseY());
                StdDraw.picture(400,544,"lose.png",800,512);
                StdDraw.text(400, 200, "Score: " + score);
                StdDraw.show();

                if (mainMenuButton.isPressed(StdDraw.mouseX(), StdDraw.mouseY(),sfxVolume.getCurrentValue())) {
                    reset(northBarrier,southBarrier,westBarrier,eastBarrier,playerOne);
                    playerOne.setPlayerLives(5);
                    titleScreen(volume);
                    score = 0;
                }
                if (restartButton.isPressed(StdDraw.mouseX(), StdDraw.mouseY(),sfxVolume.getCurrentValue())) {
                    reset(northBarrier,southBarrier,westBarrier,eastBarrier,playerOne);
                    gameOver = false;
                    playerOne.setPlayerLives(5);
                    score = 0;
                }
            }

            while (!gameOver) {// loop forever
                StdDraw.show();
                StdDraw.pause(TARGET_FPS); // regulate fps (affects game speed)
                StdDraw.clear();

                long now = System.nanoTime();
                frames++;

                if (now - lastTime >= 1_000_000_000L) {
                    fps = frames;
                    frames = 0;
                    lastTime = now;
                }

                // Movement
                if (StdDraw.isKeyPressed(KeyEvent.VK_W) || StdDraw.isKeyPressed(KeyEvent.VK_UP)){
                    playerOne.incrementPos(0,playerOne.getSpeed());
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_S)|| StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
                    playerOne.incrementPos(0,-playerOne.getSpeed());
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_A)|| StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                    playerOne.incrementPos(-playerOne.getSpeed(), 0);
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_D)|| StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                    playerOne.incrementPos(playerOne.getSpeed(),0);
                }

                //Collision Detection
                if (westBarrier.checkForCollision(playerOne)) {
                    playerOne.incrementLives(-1);
                    reset(northBarrier,southBarrier,westBarrier,eastBarrier,playerOne);
                    SlidingBarrierX = (random.nextInt(70) + 1) * 10;
                    SlidingBarrierY = (random.nextInt(70) + 1) * 10;
                } else if (eastBarrier.checkForCollision(playerOne)) {
                    playerOne.incrementLives(-1);
                    reset(northBarrier,southBarrier,westBarrier,eastBarrier,playerOne);
                    SlidingBarrierX = (random.nextInt(70) + 1) * 10;
                    SlidingBarrierY = (random.nextInt(70) + 1) * 10;
                } else if (northBarrier.checkForCollision(playerOne)) {
                    playerOne.incrementLives(-1);
                    reset(northBarrier,southBarrier,westBarrier,eastBarrier,playerOne);
                    SlidingBarrierX = (random.nextInt(70) + 1) * 10;
                    SlidingBarrierY = (random.nextInt(70) + 1) * 10;
                } else if (southBarrier.checkForCollision(playerOne)) {
                    playerOne.incrementLives(-1);
                    reset(northBarrier,southBarrier,westBarrier,eastBarrier,playerOne);
                    SlidingBarrierX = (random.nextInt(70) + 1) * 10;
                    SlidingBarrierY = (random.nextInt(70) + 1) * 10;
                }

                //Teleportation
                if (playerOne.getPosX() > 800) {
                    playerOne.setPlayerPos(0, playerOne.getPosY());
                }
                if (playerOne.getPosY() > 800) {
                    playerOne.setPlayerPos(playerOne.getPosX(),0);
                }
                if (playerOne.getPosX() < 0) {
                    playerOne.setPlayerPos(800, playerOne.getPosY());
                }
                if (playerOne.getPosY() < 0) {
                    playerOne.setPlayerPos(playerOne.getPosX(),800);
                }

                //West Barrier logic
                westBarrier.incrementBlockX(-(random.nextInt(40) - 20) * difficulty);
                westBarrier.incrementBlockY(-(random.nextInt(40) - 20));
                if (westBarrier.getBlockX() > 900) {
                    westBarrier.setBlockX(-100);
                }
                if (westBarrier.getBlockX() < -100) {
                    westBarrier.setBlockX(900);
                }
                if (westBarrier.getBlockY() > 900) {
                    westBarrier.setBlockY(-100);
                }
                if (westBarrier.getBlockY() < -100) {
                    westBarrier.setBlockY(900);
                }

                //East Barrier logic
                eastBarrier.incrementBlockY(-5 * difficulty);
                eastBarrier.incrementBlockX(-1 * difficulty);
                if (eastBarrier.getBlockX() > 900) {
                    eastBarrier.setBlockX(-100);
                }
                if (eastBarrier.getBlockX() < -100) {
                    eastBarrier.setBlockX(900);
                }

                if (eastBarrier.getBlockY() > 900) {
                    eastBarrier.setBlockY(-100);
                }
                if (eastBarrier.getBlockY() < -100) {
                    eastBarrier.setBlockY(900);
                }
                //Sliding Barrier

                if (Math.abs(northBarrier.getBlockX() - SlidingBarrierX) < 10 && Math.abs(northBarrier.getBlockY() - SlidingBarrierY) < 10) {
                    SlidingBarrierX = (random.nextInt(70) + 1) * 10;
                    SlidingBarrierY = (random.nextInt(70) + 1) * 10;
                }
                if (northBarrier.getBlockX() > SlidingBarrierX) {
                    northBarrier.incrementBlockX((int) ((-2) * difficulty));
                } else if (northBarrier.getBlockX() < SlidingBarrierX) {
                    northBarrier.incrementBlockX((int)((2) * difficulty));
                }
                if (northBarrier.getBlockY() > SlidingBarrierY) {
                    northBarrier.incrementBlockY((int)((-2) * difficulty));
                } else if (northBarrier.getBlockY() < SlidingBarrierY) {
                    northBarrier.incrementBlockY((int)((2) * difficulty));
                }

                StdDraw.setPenColor(new Color(220, 220, 220));
                StdDraw.filledCircle(SlidingBarrierX, SlidingBarrierY, 10);
                StdDraw.line(SlidingBarrierX, SlidingBarrierY, northBarrier.getBlockX(), northBarrier.getBlockY());
                StdDraw.setPenColor(Color.black);

                //Tracking barrier
                if (southBarrier.getBlockX() == playerOne.getPosX() && southBarrier.getBlockY() == playerOne.getPosY()) {
                    SlidingBarrierX = playerOne.getPosX();
                    SlidingBarrierY = playerOne.getPosY();
                }
                if (southBarrier.getBlockX() > playerOne.getPosX()) {
                    southBarrier.incrementBlockX((-2) * difficulty);
                } else if (southBarrier.getBlockX() < playerOne.getPosX()) {
                    southBarrier.incrementBlockX((2)* difficulty);
                }
                if (southBarrier.getBlockY() > playerOne.getPosY()) {
                    southBarrier.incrementBlockY((-2)* difficulty);
                } else if (southBarrier.getBlockY() < playerOne.getPosY()) {
                    southBarrier.incrementBlockY((2)* difficulty);
                }

                long currentTime = System.currentTimeMillis();
                if (currentTime - lastScoreTime >= 10) {
                    score ++;
                    lastScoreTime = currentTime; // reset for next second
                }
                if(difficulty < 1.15f) {
                    difficulty += score * 0.00000005f;// 7 zeros 1 two
                }
                else{
                    difficulty = 1.15f;
                }



                StdDraw.text(100, 780, "X:" + playerOne.getPosX() + "  Y:" + playerOne.getPosY());
                StdDraw.text(700, 780, "FPS: " + (int) fps);
                StdDraw.text(500,780,"Score: " + score);
                StdDraw.text(300, 780, "Lives: " + playerOne.getLives());
                if(difficulty != 1.15f){
                    StdDraw.text(400, 740, "Difficulty: " + difficultyFormat.format(difficulty));
                }
                else{
                    StdDraw.text(400, 740, "Difficulty: 115% (MAX)");
                }



                westBarrier.draw();
                eastBarrier.draw();
                northBarrier.draw();
                southBarrier.draw();
                playerOne.draw(5);

            }


        }
    }
}


