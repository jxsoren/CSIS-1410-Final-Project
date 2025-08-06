import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player extends Entity {
    private final InputHandler inputHandler;
    private int screenX;
    private int screenY;

    public Player(GamePanel gamePanel, InputHandler inputHandler) {
        super(gamePanel);
        this.inputHandler = inputHandler;
        initializePlayerInScreen(gamePanel);
        initializePlayerImages();

        setWorldX(gamePanel.getTileSize() * 25);
        setWorldY(gamePanel.getTileSize() * 35);
        setSpeed(6);
        setCollision(false);
        setCurrentDirection(Direction.RIGHT);
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

            GameObject collidedGameObject = getGamePanel().getCollisionController().checkObjectForCollision(this);
            if (collidedGameObject instanceof Candy) {
                pickUpObject(collidedGameObject);
            }

            NPC collidedNPC = (NPC) getGamePanel().getCollisionController().checkEntityForCollision(this, getGamePanel().getNPCs());
            interactWithNPC(collidedNPC);

            move();
        }
    }

    private void interactWithNPC(NPC collidedNPC) {
        //        if (collidedNPC == null) {
        //            return;
        //        }
        //
        //        getGamePanel().setGameStatus(GameStatus.DIALOG);
        //
        //        collidedNPC.facePlayerWhenTalking(getCurrentDirection());
        //        collidedNPC.speak(getGamePanel().getUi());
        //        getGamePanel().getUi().setCurrentDialogLine(collidedNPC.getDialog().currentLine());
        //        collidedNPC.getDialog().nextLine();
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
        //        graphics2D.drawRect(screenX + getHitBox().x, screenY + getHitBox().y, getHitBox().width, getHitBox().height);
    }

    public void pickUpObject(GameObject gameObject) {
        if (gameObject == null) {
            return;
        }

        Candy candyObject = (Candy) gameObject;

        if (candyObject.isConsumable()) {
            CandyType candyType = candyObject.getCandyType();

            getGamePanel().getGameObjects().remove(gameObject);
            getGamePanel().getUi().setMessage("You collected a " + candyType + " candy!");
            getGamePanel().getGameState().getCandyBucket().addCandy(candyType);
        }
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
}
