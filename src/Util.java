import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Util {
    public static BufferedImage getBufferedImage(String name) {
        BufferedImage bufferedImage = null;

        try {
            bufferedImage = ImageIO.read(Objects.requireNonNull(Util.class.getResourceAsStream(name)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }
}
