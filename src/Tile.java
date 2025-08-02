import java.awt.image.BufferedImage;

public class Tile {
    private final BufferedImage image;
    private boolean hasCollision = false;

    public Tile(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean hasCollision() {
        return hasCollision;
    }

    public void setHasCollision(boolean hasCollision) {
        this.hasCollision = hasCollision;
    }
}
