import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Candy extends GameObject {
    public Candy() {
        setName("Candy");
        setHitBox(new HitBox(5, 0, 48, 48));

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/candy.png")));
            setImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
