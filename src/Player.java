import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player extends Entity {
    private final InputHandler inputHandler;
    private int screenX;
    private int screenY;
    private int candyCount;

    public Player(GamePanel gamePanel, InputHandler inputHandler) {
        super(gamePanel);
        this.inputHandler = inputHandler;
        initializePlayerInScreen(gamePanel);
        initializePlayerImages();

        setWorldX(700);
        setWorldY(700);
        setSpeed(4);
        setCollision(false);
        setCurrentDirection(Direction.DOWN);
        setHitBox(new HitBox(8, 16, gamePanel.getTileSize() / 2, gamePanel.getTileSize() / 2));
    }

    public void update(int currentFrameNumber) {
        if (playerIsInMotion()) {
            setCollision(false);

            // every 10 frames change the sprite image
            if (currentFrameNumber % 10 == 0) {
                int currentSpriteVariationNumber = getCurrentSpriteVariant();
                setCurrentSpriteVariant(currentSpriteVariationNumber + 1);
            }

            if (inputHandler.up) {
                setCurrentDirection(Direction.UP);
            } else if (inputHandler.down) {
                setCurrentDirection(Direction.DOWN);
            } else if (inputHandler.left) {
                setCurrentDirection(Direction.LEFT);
            } else if (inputHandler.right) {
                setCurrentDirection(Direction.RIGHT);
            }

            getGamePanel().getCollisionController().checkTileForCollision(this);

            int gameObjectIndex = getGamePanel().getCollisionController().checkObjectForCollision(this);
            if (gameObjectIndex >= 0) {
                pickUpObject(gameObjectIndex);
            }

            int npcCollisionIndex = getGamePanel().getCollisionController().checkEntityForCollision(this, getGamePanel().getNPCs());
            interactWithNPC(npcCollisionIndex);

            if (getGamePanel().getInputHandler().enterPressed) {

            }

            move();
        }
    }

    private void interactWithNPC(int npcCollisionIndex) {
        if (npcCollisionIndex < 0) {
            return;
        }

        getGamePanel().setGameStatus(GameStatus.DIALOG);

        Entity npc = getGamePanel().getNPCs().get(npcCollisionIndex);

        npc.speak();
        getGamePanel().getUi().setCurrentDialogLine(npc.getDialog().currentLine());
        npc.getDialog().nextLine();

        //        getGamePanel().getInputHandler().enterPressed = false;
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage playerImage = null;

        int currentSpriteVariationNumber = getCurrentSpriteVariant();

        switch (getCurrentDirection()) {
            case UP -> playerImage = getSpriteImages().get("up" + currentSpriteVariationNumber);
            case DOWN -> playerImage = getSpriteImages().get("down" + currentSpriteVariationNumber);
            case LEFT -> playerImage = getSpriteImages().get("left" + currentSpriteVariationNumber);
            case RIGHT -> playerImage = getSpriteImages().get("right" + currentSpriteVariationNumber);
        }

        graphics2D.drawImage(playerImage, getScreenX(), getScreenY(), getGamePanel().getTileSize(), getGamePanel().getTileSize(), null);
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(screenX + getHitBox().x, screenY + getHitBox().y, getHitBox().width, getHitBox().height);
    }

    public void pickUpObject(int objectIndex) {
        if (objectIndex < 0) {
            return;
        }

        candyCount++;
        getGamePanel().getGameObjects().remove(objectIndex);
        getGamePanel().getUi().setMessage("You collected something!");
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    /**
     * Initializes the player in the center of the game window
     *
     * @param gamePanel main game panel
     */

    private void initializePlayerInScreen(GamePanel gamePanel) {
        int halfTileLength = gamePanel.getTileSize() / 2;
        this.screenX = gamePanel.SCREEN_WIDTH / 2 - halfTileLength;
        this.screenY = gamePanel.SCREEN_HEIGHT / 2 - halfTileLength;
    }

    private void initializePlayerImages() {
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

    private boolean playerIsInMotion() {
        return inputHandler.up || inputHandler.down || inputHandler.left || inputHandler.right;
    }

    public int getCandyCount() {
        return candyCount;
    }

    public void setCandyCount(int candyCount) {
        this.candyCount = candyCount;
    }
}
