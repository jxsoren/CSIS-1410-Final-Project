import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player extends Entity {
    private GamePanel gamePanel;
    private InputHandler inputHandler;

    public Player(GamePanel gamePanel, InputHandler inputHandler) {
        this.gamePanel = gamePanel;
        this.inputHandler = inputHandler;

        setX(100);
        setY(100);
        setSpeed(4);
        setCurrentDirection(Direction.RIGHT);
        setPlayerImages();
    }

    public void update(int currentFrameNumber) {
        if (playerIsInMotion()) {
            // every 10 frames change the sprite image
            if (currentFrameNumber % 10 == 0) {
                int currentSpriteVariationNumber = getSpriteVariantNumber();
                setSpriteVariantNumber(currentSpriteVariationNumber + 1);
            }

            if (inputHandler.up) {
                setY(getY() - getSpeed());
                setCurrentDirection(Direction.UP);
            } else if (inputHandler.down) {
                setY(getY() + getSpeed());
                setCurrentDirection(Direction.DOWN);
            } else if (inputHandler.left) {
                setX(getX() - getSpeed());
                setCurrentDirection(Direction.LEFT);
            } else if (inputHandler.right) {
                setX(getX() + getSpeed());
                setCurrentDirection(Direction.RIGHT);
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage playerImage = null;

        int currentSpriteVariationNumber = getSpriteVariantNumber();

        switch (getCurrentDirection()) {
            case LEFT -> playerImage = getSpriteMapping().get("left" + currentSpriteVariationNumber);
            case RIGHT -> playerImage = getSpriteMapping().get("right" + currentSpriteVariationNumber);
            case UP -> playerImage = getSpriteMapping().get("up" + currentSpriteVariationNumber);
            case DOWN -> playerImage = getSpriteMapping().get("down" + currentSpriteVariationNumber);
        }

        graphics2D.drawImage(playerImage, getX(), getY(), gamePanel.scaledTileSize, gamePanel.scaledTileSize, null);
    }

    private void setPlayerImages() {
        HashMap<String, BufferedImage> images = new HashMap<>();
        images.put("left1", getBufferedImage("player/sprites/left1.png"));
        images.put("left2", getBufferedImage("player/sprites/left2.png"));
        images.put("right1", getBufferedImage("player/sprites/right1.png"));
        images.put("right2", getBufferedImage("player/sprites/right2.png"));
        images.put("up1", getBufferedImage("player/sprites/up1.png"));
        images.put("up2", getBufferedImage("player/sprites/up2.png"));
        images.put("down1", getBufferedImage("player/sprites/down1.png"));
        images.put("down2", getBufferedImage("player/sprites/down2.png"));

        setSpriteMapping(images);
    }

    private boolean playerIsInMotion() {
        return inputHandler.up || inputHandler.down || inputHandler.left || inputHandler.right;
    }

}
