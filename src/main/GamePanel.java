package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    /****************GAME COMPONENTS (EDITABLE)******************************/

    // SCREEN SETTINGS
    private final int originalTileSize = 16;     // 16x16 tile
    private final int SCALE = 3;

    public final int tileSize = originalTileSize * SCALE;     // 48x48 tile
    public final int maxScreenColumn = 16;  // width of screen
    public final int maxScreenRow = 12;     // height of screen
    public final int screenWidth = tileSize * maxScreenColumn;    // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;      // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldColumn = 50;   // width of world map
    public final int maxWorldRow = 50;     // height of world map
    public final int worldWidth = tileSize * maxScreenColumn;   // in pixels
    public final int worldHeight = tileSize * maxScreenRow;     // in pixels

    // FPS (Frames per second)
    int FPS = 60;

    // Objects used for game design
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionDetector cDetector = new CollisionDetector(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public Player player = new Player(this, keyH);

    public SuperObject[] obj = new SuperObject[10];  // this is where game objects are stored

    /********************************************************************/


    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setUpGame() {

        assetSetter.setObject();

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;  // 0.016666... seconds with 60 FPS
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            // 1 UPDATE: update info such as character position
            update();

            // 2 DRAW: refresh screen with new info displayed (drawn)
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void update() {

        player.update();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g_2d = (Graphics2D) g;

        // TILE
        tileM.draw(g_2d);

        // OBJECT
        for (SuperObject x: obj) {
            if (x != null) {
                x.draw(g_2d, this);
            }
        }

        // PLAYER
        player.draw(g_2d);

        g_2d.dispose();

    }
}
