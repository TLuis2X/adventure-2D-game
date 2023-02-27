package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNumber;

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10];   // Number of tiles used (EDITABLE)
        mapTileNumber = new int[gp.maxWorldColumn][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/worldmap_v2.txt");  //Map file matrix goes here
    }

    //All tile images used
    public void getTileImage() {

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_1.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water_1.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree_on_grass.png"));
            tile[5].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Stores the map matrix for use later
    public void loadMap(String filePath) {

        try {

            InputStream input_S = getClass().getResourceAsStream(filePath);
            BufferedReader bufferedR = new BufferedReader(new InputStreamReader(input_S));

            int column = 0;
            int row = 0;

            while (column < gp.maxWorldColumn && row < gp.maxWorldRow) {

                String line = bufferedR.readLine();

                while (column < gp.maxWorldColumn) {

                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[column]);

                    mapTileNumber[column][row] = num;
                    column++;
                }
                if (column == gp.maxWorldColumn) {
                    column = 0;
                    row++;
                }
            }
            bufferedR.close();

        } catch (Exception e) {

        }

    }

    //Draws the whole map using the matrix positions
    public void draw(Graphics2D g_2d) {

        int worldColumn = 0;
        int worldRow = 0;

        while (worldColumn < gp.maxWorldColumn && worldRow < gp.maxWorldRow) {

            int tileNumber = mapTileNumber[worldColumn][worldRow];

            int worldX = worldColumn * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //Only renders the map section where the camera is showing, which improves render performance
            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

                g_2d.drawImage(tile[tileNumber].image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            }

            worldColumn++;

            if (worldColumn == gp.maxWorldColumn) {
                worldColumn = 0;
                worldRow++;
            }
        }

    }

}
