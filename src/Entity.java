import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public abstract class Entity {
    private int x, y;
    private int speed;
    private Direction currentDirection;
    private HashMap<String, BufferedImage> spriteMapping;
    private int spriteVariantNumber = 1;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public HashMap<String, BufferedImage> getSpriteMapping() {
        return spriteMapping;
    }

    public void setSpriteMapping(HashMap<String, BufferedImage> spriteMapping) {
        this.spriteMapping = spriteMapping;
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

    public int getSpriteVariantNumber() {
        return spriteVariantNumber;
    }

    public void setSpriteVariantNumber(int spriteVariantNumber) {
        int maxSpriteVariants = 2;

        if (spriteVariantNumber > maxSpriteVariants) {
            spriteVariantNumber = 1;
        }

        this.spriteVariantNumber = spriteVariantNumber;
    }
}
