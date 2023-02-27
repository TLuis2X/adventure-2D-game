package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    // Position of player relative to game screen
    public final int screenX;
    public final int screenY;

    int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        // Player position at the middle of the tile
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Rectangle inside player character tile or HITBOX (EDITABLE)
        // Coordinates relative to this tile, where they point to the top left edge of the rectangle
        solidArea = new Rectangle();
        solidArea.x = 12;             // numbers of pixels to the right
        solidArea.y = 22;            // number of pixels down
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 21;         // in pixels
        solidArea.height = 26;        // in pixels

        setDefaultValues();
        getPlayerImage();

    }

    // Starting position of player in world map (x=19, y=23) (EDITABLE)
    public void setDefaultValues() {

        // in pixels
        worldX = gp.tileSize * 19;
        worldY = gp.tileSize * 23;
        speed = 4;
        direction = "down";

    }

    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/man_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/man_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/man_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/man_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/man_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/man_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/man_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/man_right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {

            if (keyH.upPressed) {
                direction = "up";
            }
            else if (keyH.downPressed) {
                direction = "down";
            }
            else if (keyH.leftPressed) {
                direction = "left";
            }
            else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.cDetector.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cDetector.checkObject(this, true);
            pickUpObject(objIndex);

            // If collision is FALSE, player can move
            if (collisionOn == false) {

                switch (direction) {
                    case "up":
                        worldY -= speed;  // Moves player up
                        break;
                    case "down":
                        worldY += speed;  // Moves player down
                        break;
                    case "left":
                        worldX -= speed;  // Moves player left
                        break;
                    case "right":
                        worldX += speed;  // Moves player right
                        break;
                }

            }

            spriteCounter++;
            // RATE is the rate at which player is walking (10 is fast, 20 is slow)
            final int RATE = 12; //EDITABLE
            if (spriteCounter > RATE) {
                if (spriteNumber == 1)
                    spriteNumber = 2;
                else if (spriteNumber == 2)
                    spriteNumber = 1;

                spriteCounter = 0;

            }
        }

    }

    public void pickUpObject(int i) {

        if (i != 999) {

            String objectName = gp.obj[i].name;

            switch (objectName) {
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.obj[i] = null;
                        hasKey--;
                    }
                    break;

            }

        }

    }

    public void draw(Graphics2D g_2d) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNumber == 1)
                    image = up1;
                if (spriteNumber == 2)
                    image = up2;
                break;
            case "down":
                if (spriteNumber == 1)
                    image = down1;
                if (spriteNumber == 2)
                    image = down2;
                break;
            case "left":
                if (spriteNumber == 1)
                    image = left1;
                if (spriteNumber == 2)
                    image = left2;
                break;
            case "right":
                if (spriteNumber == 1)
                    image = right1;
                if (spriteNumber == 2)
                    image = right2;
                break;
        }
        g_2d.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
