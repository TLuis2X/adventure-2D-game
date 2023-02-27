package main;

import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    /**********************************
     Instantiates objects unto the game
     Current objects:
     */
    public void setObject() {

        // - 2 Keys
        gp.obj[0] = new OBJ_Key();
        gp.obj[0].worldX = gp.tileSize * 46;        // x-coordinate
        gp.obj[0].worldY = gp.tileSize * 46;        // y-coordinate

        gp.obj[1] = new OBJ_Key();
        gp.obj[1].worldX = gp.tileSize * 6;        // x-coordinate
        gp.obj[1].worldY = gp.tileSize * 15;        // y-coordinate

        // - 2 Doors
        gp.obj[2] = new OBJ_Door();
        gp.obj[2].worldX = gp.tileSize * 5;        // x-coordinate
        gp.obj[2].worldY = gp.tileSize * 32;        // y-coordinate

        gp.obj[3] = new OBJ_Door();
        gp.obj[3].worldX = gp.tileSize * 2;        // x-coordinate
        gp.obj[3].worldY = gp.tileSize * 2;        // y-coordinate

        // - 1 Chest
        gp.obj[4] = new OBJ_Chest();
        gp.obj[4].worldX = gp.tileSize * 6;        // x-coordinate
        gp.obj[4].worldY = gp.tileSize * 34;        // y-coordinate

    }

}