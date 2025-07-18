import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    /**
     * Amount of pixels that will be used to represent a tile.
     * A tile will be represented as 16 x 16 pixels.
     */
    final int tileSize = 16;

    /**
     * Used to scale the tile size.
     */
    final int scale = 3;

    /**
     * Scales the tile size according to the scale.
     * Creates a 48 x 48 pixel tile
     */
    final int scaledTileSize = tileSize * scale;

    // Forms a 16 x 12 grid making a 4:3 ratio

    /**
     * Amount of columns used to form the window.
     */
    final int windowColumnSize = 16;

    /**
     * Amount of rows used to form the window.
     */
    final int windowRowSize = 12;

    // Forms a window of 786 x 576 pixels (4:3) ratio

    final int screenWidth = scaledTileSize * windowColumnSize; // 768 pixels
    final int screenHeight = scaledTileSize * windowRowSize; // 576 pixels

    Thread gameThread;
    InputHandler inputHandler = new InputHandler();

    // player position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    // fps
    private final int FPS = 60;

    public GamePanel() {
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

        while (gameThread != null) {
            currentRepaintTime = System.nanoTime(); // get current time in nanoseconds for high precision

            double elapsedTime = currentRepaintTime - previousRepaintTime;
            delta += elapsedTime / repaintInterval;

            // previous repaint time now becomes the repaint time that just occurred
            previousRepaintTime = currentRepaintTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    // Game Loop Logic
    // This game loop has 2 main steps:
    // 1. Get updates
    // 2. Repaint screen

    public void update() {
        if (inputHandler.up) {
            playerY -= playerSpeed;
        } else if (inputHandler.down) {
            playerY += playerSpeed;
        } else if (inputHandler.left) {
            playerX -= playerSpeed;
        } else if (inputHandler.right) {
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setPaint(Color.WHITE);
        graphics2D.fillRect(playerX, playerY, scaledTileSize, scaledTileSize);

        // garbage collect all graphic objects - used for performance
        graphics2D.dispose();
    }

}
