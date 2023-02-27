package main;

import entity.Entity;
import object.SuperObject;

public class CollisionDetector {

    GamePanel gp;

    public CollisionDetector(GamePanel gp) {

        this.gp = gp;

    }

    public void checkTile(Entity entity) {

        int entityLeft_WorldX = entity.worldX + entity.solidArea.x;
        int entityRight_WorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTop_WorldY = entity.worldY + entity.solidArea.y;
        int entityBottom_WorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftColumn = entityLeft_WorldX / gp.tileSize;
        int entityRightColumn = entityRight_WorldX / gp.tileSize;
        int entityTopRow = entityTop_WorldY / gp.tileSize;
        int entityBottomRow = entityBottom_WorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTop_WorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftColumn][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightColumn][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottom_WorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftColumn][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightColumn][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftColumn = (entityLeft_WorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftColumn][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityLeftColumn][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightColumn = (entityRight_WorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityRightColumn][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightColumn][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }
                break;
        }

    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {

            if (gp.obj[i] != null) {

                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            // Checks if object is solid
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            // Checks if the entity is a player
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;  // Restores original x coordinate
                entity.solidArea.y = entity.solidAreaDefaultY;  // Restores original y coordinate
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;  //  "
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;  //  "

            }
        }

        return index;
    }

}
