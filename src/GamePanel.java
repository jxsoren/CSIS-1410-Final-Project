import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements Runnable {
    /**
     * Amount of pixels that will be used to represent a tile.
     * A tile will be represented as 16 x 16 pixels.
     */
    private final int tileSize = 16;

    /**
     * Used to scale the tile size.
     */
    private final int scale = 3;

    /**
     * Scales the tile size according to the scale.
     * Creates a 48 x 48 pixel tile
     */
    private final int scaledTileSize = tileSize * scale;

    // Forms a 16 x 12 grid making a 4:3 ratio

    /**
     * Amount of columns used to form the window.
     */
    public final int MAX_WINDOW_COLUMNS = 16;

    /**
     * Amount of rows used to form the window.
     */
    public final int MAX_WINDOW_ROWS = 12;

    // Forms a window of 786 x 576 pixels (4:3) ratio

    // World Settings
    public final int MAX_WORLD_COLUMNS = 50;
    public final int MAX_WORLD_ROWS = 50;

    public final int WORLD_WIDTH = scaledTileSize * MAX_WORLD_COLUMNS;
    public final int WORLD_HEIGHT = scaledTileSize * MAX_WORLD_ROWS;

    private final int screenWidth = scaledTileSize * MAX_WINDOW_COLUMNS; // 768 pixels
    private final int screenHeight = scaledTileSize * MAX_WINDOW_ROWS; // 576 pixels

    private Thread gameThread;
    private final Player player;
    private final TileManager tileManager;
    private final CollisionController collisionController;
    private final ArrayList<GameObject> gameObjects;
    private final AssetSetter assetSetter;

    // fps
    private final int FPS = 60;

    public GamePanel() {
        InputHandler inputHandler = new InputHandler();

        this.player = new Player(this, inputHandler);
        this.tileManager = new TileManager(this);
        this.collisionController = new CollisionController(this);
        this.gameObjects = new ArrayList<>();
        this.assetSetter = new AssetSetter(this);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.setFocusable(true); // ensure window is focused to receive input
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setupGame() {
        assetSetter.initializeObjects();
    }

    /**
     * This method gets called after the thread has started
     */

    @Override
    public void run() {
        double oneSecondInNanoSeconds = 1_000_000_000d;
        double repaintInterval = oneSecondInNanoSeconds / FPS; // repaint screen 60 times per second (every ~16ms)
        double delta = 0;
        long previousRepaintTime = System.nanoTime();
        long currentRepaintTime;
        int currentFrameNumber = 1;

        while (gameThread != null) {
            currentRepaintTime = System.nanoTime(); // get current time in nanoseconds for high precision

            double elapsedTime = currentRepaintTime - previousRepaintTime;
            delta += elapsedTime / repaintInterval;

            // previous repaint time now becomes the repaint time that just occurred
            previousRepaintTime = currentRepaintTime;

            if (delta >= 1) {
                currentFrameNumber++;

                if (currentFrameNumber > FPS) {
                    currentFrameNumber = 1;
                }

                update(currentFrameNumber);
                repaint();
                delta--;
            }
        }
    }

    // Game Loop Logic
    // This game loop has 2 main steps:
    // 1. Get updates
    // 2. Repaint screen

    public void update(int currentFrameNumber) {
        player.update(currentFrameNumber);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        // render map
        tileManager.draw(graphics2D);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(graphics2D, this);
        }

        // render player
        player.draw(graphics2D);

        // garbage collect all unused graphic objects (helps performance)
        graphics2D.dispose();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScaledTileSize() {
        return scaledTileSize;
    }

    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public CollisionController getCollisionController() {
        return collisionController;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }
}
