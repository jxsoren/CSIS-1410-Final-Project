import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NPC extends Entity implements Speakable {
    private int actionLockCounter = 0;
    private final Random random = new Random();
    private final NPCType npcType;

    public NPC(GamePanel gamePanel, NPCType npcType, List<DialogLine> dialogLines, Map<String, BufferedImage> spriteImages) {
        super(gamePanel);
        this.npcType = npcType;
        setHitBox(new HitBox(8, 16, gamePanel.getTileSize() / 2, gamePanel.getTileSize() / 2));
        walkRandomly();
        setSpeed(1);
        getDialog().addDialogLines(dialogLines);
        initializeSpriteImages(spriteImages);
        setWorldX(gamePanel.getTileSize() * random.nextInt(25, 35));
        setWorldY(gamePanel.getTileSize() * random.nextInt(25, 35));
    }

    @Override
    public void speak(UI ui) {

    }

    public void setAction() {
        actionLockCounter++;
        if (actionLockCounter == 120) {
            walkRandomly();
            actionLockCounter = 0;
        }
    }

    private void walkRandomly() {
        switch (random.nextInt(1, 5)) {
            case 1 -> setCurrentDirection(Direction.UP);
            case 2 -> setCurrentDirection(Direction.DOWN);
            case 3 -> setCurrentDirection(Direction.LEFT);
            case 4 -> setCurrentDirection(Direction.RIGHT);
        }
    }

    public void facePlayerWhenTalking(Direction playerDirection) {
        switch (playerDirection) {
            case Direction.UP -> setCurrentDirection(Direction.DOWN);
            case Direction.DOWN -> setCurrentDirection(Direction.UP);
            case Direction.LEFT -> setCurrentDirection(Direction.RIGHT);
            case Direction.RIGHT -> setCurrentDirection(Direction.LEFT);
        }
    }

    public void initializeSpriteImages(Map<String, BufferedImage> spriteImages) {
        setSpriteImages(spriteImages);
    }

    public NPCType getNpcType() {
        return npcType;
    }
}
