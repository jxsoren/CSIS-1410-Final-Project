import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class BullyKid extends NPC {
    private int actionLockCounter = 0;

    public BullyKid(GamePanel gamePanel) {
        super(gamePanel);
        setHitBox(new HitBox(8, 16, gamePanel.getTileSize() / 2, gamePanel.getTileSize() / 2));
        setCurrentDirection(Direction.DOWN);
        setSpeed(1);
        initializeSpriteImages();
        setDialog();
    }

    public void setDialog() {
        getDialog().setDialogLines(List.of(
                "Hey, Buddy!",
                "What's up???",
                "How are you??"
        ));
    }

    public void speak() {
        super.speak();
    }

    public void setAction() {
        actionLockCounter++;
        Random random = new Random();

        if (actionLockCounter == 120) {
            int i = random.nextInt(1, 101);

            if (i <= 25) {
                setCurrentDirection(Direction.UP);
            } else if (i > 25 && i <= 50) {
                setCurrentDirection(Direction.DOWN);
            } else if (i > 50 && i <= 75) {
                setCurrentDirection(Direction.LEFT);
            } else if (i > 75 && i <= 100) {
                setCurrentDirection(Direction.RIGHT);
            }

            actionLockCounter = 0;
        }
    }

    private void initializeSpriteImages() {
        HashMap<String, BufferedImage> images = new HashMap<>();
        images.put("left1", getBufferedImage("player/sprites/left1.png"));
        images.put("left2", getBufferedImage("player/sprites/left2.png"));
        images.put("right1", getBufferedImage("player/sprites/right1.png"));
        images.put("right2", getBufferedImage("player/sprites/right2.png"));
        images.put("up1", getBufferedImage("player/sprites/up1.png"));
        images.put("up2", getBufferedImage("player/sprites/up2.png"));
        images.put("down1", getBufferedImage("player/sprites/down1.png"));
        images.put("down2", getBufferedImage("player/sprites/down2.png"));

        setSpriteImages(images);
    }
}
