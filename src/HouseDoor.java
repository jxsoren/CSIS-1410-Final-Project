import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class HouseDoor extends GameObject implements Speakable {
    private Dialog dialog;

    public HouseDoor() {
        setCollision(true);
        setInteractable(true);
        setConsumable(false);
        setHitBox(new HitBox(0, 0, 48, 48));
        this.dialog = new Dialog();

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/mapTiles/tile_41.png")));
            setImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void interact() {
        speak();
    }

    @Override
    public void speak() {

    }

    @Override
    public Dialog getDialog() {
        return dialog;
    }
}
