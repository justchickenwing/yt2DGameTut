package gui;

import main.KeyHandler;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{   //// implements Runnable braucht es für den Thread

    // SCREEN SETTINGS
    final int originalTileSize = 16;     //// Tile Size 16x16 px
    final int scale = 2;  //// To make the character look bigger, I have to scale it (2x) -> 32x32 px

    final int tilesize = originalTileSize * scale;    //// 32 x 32 px
    final int maxScreenCol = 20;    //// Columns 20
    final int maxScreenRow = 20;    //// Rows 20

    final int screenWidth = tilesize * maxScreenCol;    //// 640 px
    final int screenHeight = tilesize * maxScreenRow;    //// 640 px

    // FPS
    int fps = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;    //// erzeugt einen weiteren (aktiven) Thread - neben dem (passiven) Mainthread
                          //// ruft automatisch die run() Methode auf

    // Set player default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);    //// better rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {      //// Methode run() für das Runnable  -> Core of the Game (Game Loop)

        double drawInterval = 1000000000d/fps;   //// 0.01667 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){    //// As long as this gameThread exists, it repeats the code in the Brackets

            // 1 UPDATE: update information such as character position
            update();

            // 2 DRAW: draw the screen with the updates information
            repaint();    //// calls the paintComponent Method

            // 3 SLEEP: fps minus the passed time equals the remaining time -> sleep

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;     //// sleep accepts only mili seconds, no nanoseconds

                if (remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long)remainingTime);     //// this pauses the Gametime

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void update() {

        if (keyH.upPressed){
            playerY = playerY-playerSpeed;
        }
        else if (keyH.downPressed){
            playerY = playerY+playerSpeed;
        }
        else if (keyH.leftPressed){
            playerX = playerX-playerSpeed;
        }
        else if (keyH.rightPressed){
            playerX = playerX+playerSpeed;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //// CHARACTER DRAW
        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, tilesize, tilesize);
        g2.dispose();

    }
}
