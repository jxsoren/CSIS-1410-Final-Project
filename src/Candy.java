import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Candy extends GameObject {
    CandyType candyType;

    public Candy(CandyType candyType, String imagePath) {
        this.candyType = candyType;
        setConsumable(true);
        setHitBox(new HitBox(0, 0, 48, 48));

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            setImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CandyType getCandyType() {
        return candyType;
    }
}
