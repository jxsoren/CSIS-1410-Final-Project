import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HouseDoor extends Entity implements Drawable, Speakable {
    public HouseDoor(GamePanel gamePanel) {
        super(gamePanel);
        setCollision(true);
        setHitBox(new HitBox(0, 0, gamePanel.getTileSize(), gamePanel.getTileSize()));
    }


}
