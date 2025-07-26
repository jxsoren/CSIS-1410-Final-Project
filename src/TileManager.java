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
        this.tileMapNumbers = new int[gamePanel.MAX_WORLD_COLUMNS][gamePanel.MAX_WORLD_ROWS];

        initializeTileImages();
        initializeTileMapNumbers("tiles/tiles.txt");
    }

    /**
     * @param graphics2D
     */

    public void draw(Graphics2D graphics2D) {
        int worldColumn = 0;
        int worldRow = 0;

        while (worldColumn < gamePanel.MAX_WORLD_COLUMNS && worldRow < gamePanel.MAX_WORLD_ROWS) {
            int tileNumber = tileMapNumbers[worldColumn][worldRow]; // fetch tile number for given column and row
            BufferedImage tileImage = tiles[tileNumber].getImage();

            // absolute position of tiles on map
            int absoluteWorldX = worldColumn * gamePanel.getScaledTileSize();
            int absoluteWorldY = worldRow * gamePanel.getScaledTileSize();

            // player position on the world map
            int playerWorldX = gamePanel.getPlayer().getWorldX();
            int playerWorldY = gamePanel.getPlayer().getWorldY();

            // player position on the screen
            int playerScreenX = gamePanel.getPlayer().getScreenX();
            int playerScreenY = gamePanel.getPlayer().getScreenY();

            int screenX = absoluteWorldX - playerWorldX + playerScreenX;
            int screenY = absoluteWorldY - playerWorldY + playerScreenY;

            // draw image on screen

            if (absoluteWorldX + gamePanel.getScaledTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() &&
                    absoluteWorldX - gamePanel.getScaledTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() &&
                    absoluteWorldY + gamePanel.getScaledTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() &&
                    absoluteWorldY - gamePanel.getScaledTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()) {
                graphics2D.drawImage(tileImage, screenX, screenY, gamePanel.getScaledTileSize(), gamePanel.getScaledTileSize(), null);
            }

            worldColumn++; // increment column pointer to the next column in matrix

            // once you've hit the final column for the row, go to the first column of the next row
            if (worldColumn == gamePanel.MAX_WORLD_ROWS) {
                worldColumn = 0; // reset column and current x coordinate pointer
                worldRow++; // increment row pointer to the next row in matrix
            }
        }
    }


    /**
     * Loads tiles array by creating new tile instances and assigning a tile sprite image to the tile instance
     */

    private void initializeTileImages() {
        try {
            // TILE 0: Autumn Grass (walkable)
            tiles[0] = new Tile();
            tiles[0].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/autumn_grass.png"))));
            tiles[0].setHasCollision(false); // Players can walk on grass

            // TILE 1: Suburban Street (walkable)
            tiles[1] = new Tile();
            tiles[1].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/street.png"))));
            tiles[1].setHasCollision(false); // Players can walk on roads

            // TILE 2: House Floor (walkable)
            tiles[2] = new Tile();
            tiles[2].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/house_floor.png"))));
            tiles[2].setHasCollision(false); // Players can walk inside houses

            // TILE 3: Autumn Tree (blocks movement)
            tiles[3] = new Tile();
            tiles[3].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/autumn_tree.png"))));
            tiles[3].setHasCollision(true); // Trees block movement

            // TILE 4: Jack-o'-Lantern Decoration (walkable)
            tiles[4] = new Tile();
            tiles[4].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/jack_o_lantern.png"))));
            tiles[4].setHasCollision(false); // Decorative, players can walk over

            // TILE 5: House Wall (blocks movement)
            tiles[5] = new Tile();
            tiles[5].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/house_wall.png"))));
            tiles[5].setHasCollision(true); // Walls block movement

            // TILE 6: Front Door (interactable - no collision for now)
            tiles[6] = new Tile();
            tiles[6].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/front_door.png"))));
            tiles[6].setHasCollision(false); // Players need to reach doors to interact (trick-or-treat)

            // TILE 7: Sidewalk (walkable)
            tiles[7] = new Tile();
            tiles[7].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/sidewalk.png"))));
            tiles[7].setHasCollision(false); // Players can walk on sidewalks

            // TILE 8: Park Grass (walkable)
            tiles[8] = new Tile();
            tiles[8].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/park_grass.png"))));
            tiles[8].setHasCollision(false); // Players can walk on park grass

            // TILE 9: Park Bench (blocks movement)
            tiles[9] = new Tile();
            tiles[9].setImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("tiles/sprites/park_bench.png"))));
            tiles[9].setHasCollision(true); // Benches block movement

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading tile sprites! Make sure all PNG files are in tiles/sprites/ folder");
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

    public Tile[] getTiles() {
        return tiles;
    }
}
