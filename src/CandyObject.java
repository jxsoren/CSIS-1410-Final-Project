import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CandyObject extends GameObject {
    public CandyObject() {
        setName("Candy");

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/candy.png")));
            setImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
