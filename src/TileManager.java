import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    private final GamePanel gamePanel;
    private final Tile[] tiles;

    private final int[][] tileMapNumbers;

    /**
     * Constructs a TileManager object that is used to control the behavior of tiles for the world map
     *
     * @param gamePanel main game panel
     */

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.tiles = new Tile[10];
        this.tileMapNumbers = new int[gamePanel.MAX_WINDOW_COLUMNS][gamePanel.MAX_WINDOW_ROWS];

        initializeTileImages();
        initializeTileMapNumbers("tiles/tiles.txt");
    }

    /**
     *
     * @param graphics2D
     */

    public void draw(Graphics2D graphics2D) {
        int columnNumber = 0;
        int rowNumber = 0;
        int xCoordinate = 0;
        int yCoordinate = 0;

        while (columnNumber < gamePanel.MAX_WINDOW_COLUMNS && rowNumber < gamePanel.MAX_WINDOW_ROWS) {
            int tileNumber = tileMapNumbers[columnNumber][rowNumber]; // fetch tile number for given column and row
            BufferedImage tileImage = tiles[tileNumber].getImage();

            // draw image on screen
            graphics2D.drawImage(tileImage, xCoordinate, yCoordinate, gamePanel.scaledTileSize, gamePanel.scaledTileSize, null);

            columnNumber++; // increment column pointer to the next column in matrix

            xCoordinate += gamePanel.scaledTileSize; // increment current X coordinate to the start of the next tile column

            // once you've hit the final column for the row, go to the first column of the next row
            if (columnNumber == gamePanel.MAX_WINDOW_COLUMNS) {
                // rest column and current x coordinate pointer
                columnNumber = 0;
                xCoordinate = 0;

                rowNumber++; // increment row pointer to the next row in matrix
                yCoordinate += gamePanel.scaledTileSize; // increment current Y coordinate to the start of the next tile row
            }
        }
    }


    /**
     * Loads tiles array by creating new tile instances and assigning a tile sprite image to the tile instance
     */

    private void initializeTileImages() {
        try {
            // Grass Tile
            tiles[0] = new Tile();
            tiles[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/grass.png"))));
        } catch (IOException e) {
            e.printStackTrace();
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

            while (columnNumber < gamePanel.MAX_WINDOW_COLUMNS && rowNumber < gamePanel.MAX_WINDOW_ROWS) {
                String currentLine = bufferedReader.readLine();
                String[] rawTileNumbers = currentLine.split(" ");

                while (columnNumber < gamePanel.MAX_WINDOW_COLUMNS) {
                    int parsedTileNumber = Integer.parseInt(rawTileNumbers[columnNumber]);
                    tileMapNumbers[columnNumber][rowNumber] = parsedTileNumber;
                    columnNumber++;
                }

                if (columnNumber == gamePanel.MAX_WINDOW_COLUMNS) {
                    columnNumber = 0;
                    rowNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
