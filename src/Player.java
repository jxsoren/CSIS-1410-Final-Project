import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player extends Entity {
    private final GamePanel gamePanel;
    private final InputHandler inputHandler;

    private int screenX, screenY;

    public Player(GamePanel gamePanel, InputHandler inputHandler) {
        this.gamePanel = gamePanel;
        this.inputHandler = inputHandler;

        initializePlayerInScreen(gamePanel);
        initializePlayerImages();

        setWorldX(700);
        setWorldY(700);
        setSpeed(4);
        setCollision(false);
        setCurrentDirection(Direction.DOWN);
        setHitBox(new Rectangle(8, 16, gamePanel.getScaledTileSize() / 2, gamePanel.getScaledTileSize() / 2));
    }

    public void update(int currentFrameNumber) {
        if (playerIsInMotion()) {
            // every 10 frames change the sprite image
            if (currentFrameNumber % 10 == 0) {
                int currentSpriteVariationNumber = getCurrentSpriteVariant();
                setCurrentSpriteVariant(currentSpriteVariationNumber + 1);
            }

            if (inputHandler.up) {
                setCurrentDirection(Direction.UP);
            } else if (inputHandler.down) {
                setCurrentDirection(Direction.DOWN);
            } else if (inputHandler.left) {
                setCurrentDirection(Direction.LEFT);
            } else if (inputHandler.right) {
                setCurrentDirection(Direction.RIGHT);
            }

            setCollision(false);
            gamePanel.getCollisionController().checkTileForCollision(this);

            if (!isCollision()) {
                switch (getCurrentDirection()) {
                    case UP -> setWorldY(getWorldY() - getSpeed());
                    case DOWN -> setWorldY(getWorldY() + getSpeed());
                    case LEFT -> setWorldX(getWorldX() - getSpeed());
                    case RIGHT -> setWorldX(getWorldX() + getSpeed());
                }
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage playerImage = null;

        int currentSpriteVariationNumber = getCurrentSpriteVariant();

        switch (getCurrentDirection()) {
            case UP -> playerImage = getSpriteImages().get("up" + currentSpriteVariationNumber);
            case DOWN -> playerImage = getSpriteImages().get("down" + currentSpriteVariationNumber);
            case LEFT -> playerImage = getSpriteImages().get("left" + currentSpriteVariationNumber);
            case RIGHT -> playerImage = getSpriteImages().get("right" + currentSpriteVariationNumber);
        }

        graphics2D.drawImage(playerImage, getScreenX(), getScreenY(), gamePanel.getScaledTileSize(), gamePanel.getScaledTileSize(), null);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    /**
     * Initializes the player in the center of the game window
     *
     * @param gamePanel main game panel
     */

    private void initializePlayerInScreen(GamePanel gamePanel) {
        int halfTileLength = gamePanel.getScaledTileSize() / 2;
        this.screenX = gamePanel.getScreenWidth() / 2 - halfTileLength;
        this.screenY = gamePanel.getScreenHeight() / 2 - halfTileLength;
    }

    private void initializePlayerImages() {
        HashMap<String, BufferedImage> images = new HashMap<>();
        images.put("left1", getBufferedImage("player/sprites/left1.png"));
        images.put("left2", getBufferedImage("player/sprites/left2.png"));
        images.put("right1", getBufferedImage("player/sprites/right1.png"));
        images.put("right2", getBufferedImage("player/sprites/right2.png"));
        images.put("up1", getBufferedImage("player/sprites/up1.png"));
        images.put("up2", getBufferedImage("player/sprites/up2.png"));
        images.put("down1", getBufferedImage("player/sprites/down1.png"));
        images.put("down2", getBufferedImage("player/sprites/down2.png"));

        setSpriteImages(images);
    }

    private boolean playerIsInMotion() {
        return inputHandler.up || inputHandler.down || inputHandler.left || inputHandler.right;
    }

}
