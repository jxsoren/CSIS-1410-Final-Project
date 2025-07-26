import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public abstract class Entity {
    private int worldX, worldY;
    private int speed;
    private Direction currentDirection;
    private HashMap<String, BufferedImage> spriteImages;
    private int currentSpriteVariant = 1;
    private Rectangle hitBox;
    private boolean collision;

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

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}
