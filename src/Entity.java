import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public abstract class Entity {
    private final GamePanel gamePanel;
    private Direction currentDirection;
    private HashMap<String, BufferedImage> spriteImages;
    private HitBox hitBox;
    private final Dialog dialog;
    private int worldX;
    private int worldY;
    private int speed;
    private int currentSpriteVariant = 1;
    private boolean collision;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.hitBox = new HitBox(0, 0, gamePanel.getTileSize(), gamePanel.getTileSize());
        this.dialog = new Dialog();
    }

    public void setAction() {
    }

    public void update(int currentFrameNumber) {
        setAction();
        setCollision(false);
        gamePanel.getCollisionController().checkTileForCollision(this);
        gamePanel.getCollisionController().checkObjectForCollision(this);
        gamePanel.getCollisionController().checkPlayerForCollision(this);

        // every 10 frames change the sprite image
        if (currentFrameNumber % 10 == 0) {
            int currentSpriteVariationNumber = getCurrentSpriteVariant();
            setCurrentSpriteVariant(currentSpriteVariationNumber + 1);
        }

        move();
    }

    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX();
        int screenY = worldY - gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY();

        if (worldX + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldX() - gamePanel.getPlayer().getScreenX() && worldX - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldX() + gamePanel.getPlayer().getScreenX() && worldY + gamePanel.getTileSize() > gamePanel.getPlayer().getWorldY() - gamePanel.getPlayer().getScreenY() && worldY - gamePanel.getTileSize() < gamePanel.getPlayer().getWorldY() + gamePanel.getPlayer().getScreenY()) {
            BufferedImage image = null;

            int currentSpriteVariationNumber = getCurrentSpriteVariant();

            switch (getCurrentDirection()) {
                case UP -> image = getSpriteImages().get("up" + currentSpriteVariationNumber);
                case DOWN -> image = getSpriteImages().get("down" + currentSpriteVariationNumber);
                case LEFT -> image = getSpriteImages().get("left" + currentSpriteVariationNumber);
                case RIGHT -> image = getSpriteImages().get("right" + currentSpriteVariationNumber);
            }

            graphics2D.drawImage(image, screenX, screenY, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
            graphics2D.setColor(Color.RED);
            graphics2D.drawRect(screenX + getHitBox().x, screenY + getHitBox().y, getHitBox().width, getHitBox().height);
        }
    }

    public void speak() {
        //        if (!dialog.linesCompleted()) {}

        // Face Player When Communicating
        switch (gamePanel.getPlayer().getCurrentDirection()) {
            case Direction.UP -> setCurrentDirection(Direction.DOWN);
            case Direction.DOWN -> setCurrentDirection(Direction.UP);
            case Direction.LEFT -> setCurrentDirection(Direction.RIGHT);
            case Direction.RIGHT -> setCurrentDirection(Direction.LEFT);
        }
    }

    protected void move() {
        if (!hasCollision()) {
            switch (getCurrentDirection()) {
                case UP -> setWorldY(getWorldY() - getSpeed());
                case DOWN -> setWorldY(getWorldY() + getSpeed());
                case LEFT -> setWorldX(getWorldX() - getSpeed());
                case RIGHT -> setWorldX(getWorldX() + getSpeed());
            }
        }
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public HashMap<String, BufferedImage> getSpriteImages() {
        return spriteImages;
    }

    public void setSpriteImages(HashMap<String, BufferedImage> spriteImages) {
        this.spriteImages = spriteImages;
    }

    public BufferedImage getBufferedImage(String name) {
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(name)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

    public int getCurrentSpriteVariant() {
        return currentSpriteVariant;
    }

    public void setCurrentSpriteVariant(int currentSpriteVariant) {
        int maxSpriteVariants = 2;

        if (currentSpriteVariant > maxSpriteVariants) {
            currentSpriteVariant = 1;
        }

        this.currentSpriteVariant = currentSpriteVariant;
    }

    public HitBox getHitBox() {
        return hitBox;
    }

    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
    }

    public boolean hasCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public Dialog getDialog() {
        return dialog;
    }
}
