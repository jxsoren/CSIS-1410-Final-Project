import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TileManager {
    private final GamePanel gamePanel;
    private final List<Tile> tiles;
    private final int[][] tileMapNumbers;

    /**
     * Constructs a TileManager object that is used to control the behavior of tiles for the world map
     *
     * @param gamePanel main game panel
     */
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tiles = new ArrayList<>();
        this.tileMapNumbers = new int[gamePanel.MAX_WORLD_COLUMNS][gamePanel.MAX_WORLD_ROWS];

        initializeTileImages();
        initializeTileMapNumbers("tiles/tiles.txt");
    }

    /**
     * @param graphics2D Graphics2D Object
     */
    public void draw(Graphics2D graphics2D) {
        int worldColumn = 0;
        int worldRow = 0;

        while (worldColumn < gamePanel.MAX_WORLD_COLUMNS && worldRow < gamePanel.MAX_WORLD_ROWS) {
            int tileNumber = tileMapNumbers[worldColumn][worldRow]; // fetch tile number for given column and row
            BufferedImage tileImage = tiles.get(tileNumber).getImage();

            // absolute position of tiles on map
            int absoluteWorldX = worldColumn * gamePanel.getTileSize();
            int absoluteWorldY = worldRow * gamePanel.getTileSize();

            // player position on the world map
            int playerWorldX = gamePanel.getPlayer().getWorldX();
            int playerWorldY = gamePanel.getPlayer().getWorldY();

            // player position on the screen
            int playerScreenX = gamePanel.getPlayer().getScreenX();
            int playerScreenY = gamePanel.getPlayer().getScreenY();

            int screenX = absoluteWorldX - playerWorldX + playerScreenX;
            int screenY = absoluteWorldY - playerWorldY + playerScreenY;

            int tileInLength = gamePanel.getTileSize();

            // draw tiles on screen only if the player is close enough to the world tile
            if (absoluteWorldX + tileInLength > playerWorldX - playerScreenX &&
                    absoluteWorldX - tileInLength < playerWorldX + playerScreenX
                    && absoluteWorldY + tileInLength > playerWorldY - playerScreenY
                    && absoluteWorldY - tileInLength < playerWorldY + playerScreenY
            ) {
                graphics2D.drawImage(tileImage, screenX, screenY, tileInLength, tileInLength, null);
            }

            worldColumn++; // increment column pointer to the next column in matrix

            // once you've hit the final column for the row, go to the first column of the next row
            if (worldColumn == gamePanel.MAX_WORLD_COLUMNS) {
                worldColumn = 0; // reset column and current x coordinate pointer
                worldRow++; // increment row pointer to the next row in matrix
            }
        }
    }


    /**
     * Loads tiles array by creating new tile instances and assigning a tile sprite image to the tile instance
     */

    private void initializeTileImages() {
        for (int i = 0; i <= 281; i++) {
            try {
                String imagePath = String.format("tiles/mapTiles/tile_%s.png", i);
                BufferedImage tileImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));

                Tile tile = new Tile(tileImage);

                // Purplish Grey House
                if (i >= 16 && i <= 53) {
                    tile.setHasCollision(true);
                }

                if (i >= 75 && i <= 105) {
                    tile.setHasCollision(true);
                }

                // Dark Turquoise House
                if (i >= 222 && i <= 239) {
                    tile.setHasCollision(true);
                }

                // Blue House
                if (i >= 240 && i <= 257) {
                    tile.setHasCollision(true);
                }

                // Stone Walls
                if (i >= 256 && i <= 280) {
                    tile.setHasCollision(true);
                }

                tiles.add(tile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Reads in a text file containing map of tile numbers and loads it into an array of
     * tile map numbers, mimicking the same format of the map text file
     * <p>
     * The tile map text files are expected to form a matrix in a similar format to the following:
     * <p>
     * 0 0 0<br>
     * 0 0 0<br>
     * 0 0 0<br>
     *
     * @param mapFilePath filepath to map file
     */

    private void initializeTileMapNumbers(String mapFilePath) {
        try {
            // read file
            InputStream inputStream = getClass().getResourceAsStream(mapFilePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int columnNumber = 0;
            int rowNumber = 0;

            while (columnNumber < gamePanel.MAX_WORLD_COLUMNS && rowNumber < gamePanel.MAX_WORLD_ROWS) {
                String currentLine = bufferedReader.readLine();
                String[] rawTileNumbers = currentLine.split(" ");

                while (columnNumber < gamePanel.MAX_WORLD_COLUMNS) {
                    int parsedTileNumber = Integer.parseInt(rawTileNumbers[columnNumber]);
                    tileMapNumbers[columnNumber][rowNumber] = parsedTileNumber;
                    columnNumber++;
                }

                if (columnNumber == gamePanel.MAX_WORLD_COLUMNS) {
                    columnNumber = 0;
                    rowNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] getTileMapNumbers() {
        return tileMapNumbers;
    }

    public List<Tile> getTiles() {
        return tiles;
    }
}
