import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private final int baseTileSize = 16;
    private final int tileSizeScale = 5;
    private final int tileSize = baseTileSize * tileSizeScale;

    // World Settings
    public final int MAX_WORLD_COLUMNS = 80;
    public final int MAX_WORLD_ROWS = 60;

    public final int WORLD_WIDTH = tileSize * MAX_WORLD_COLUMNS;
    public final int WORLD_HEIGHT = tileSize * MAX_WORLD_ROWS;

    // Screen Settings
    public final int MAX_WINDOW_COLUMNS = 20;
    public final int MAX_WINDOW_ROWS = 12;

    public final int SCREEN_WIDTH = tileSize * MAX_WINDOW_COLUMNS;
    public final int SCREEN_HEIGHT = tileSize * MAX_WINDOW_ROWS;

    private final Player player;
    private final InputHandler inputHandler;
    private final TileManager tileManager;
    private final CollisionController collisionController;
    private final ArrayList<GameObject> gameObjects;
    private final ArrayList<Entity> NPCs;
    private final AssetSetter assetSetter;
    private final UI ui;
    private GameStatus gameStatus;
    private final GameState gameState;

    public GamePanel() {
        this.gameState = new GameState();
        this.inputHandler = new InputHandler(this);
        this.player = new Player(this, inputHandler);
        this.tileManager = new TileManager(this);
        this.collisionController = new CollisionController(this);
        this.gameObjects = new ArrayList<>();
        this.NPCs = new ArrayList<>();
        this.assetSetter = new AssetSetter(this);
        this.ui = new UI(this);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(inputHandler);
        this.setFocusable(true); // ensure window is focused to receive input
    }

    public void setupGame() {
        assetSetter.initializeObjects();
        assetSetter.initializeNPCs();
        setGameStatus(GameStatus.TITLE_SCREEN);
    }

    public void update(int currentFrameNumber) {
        if (gameState.gameWinConditionMet()) {
            setGameStatus(GameStatus.GAME_OVER);
        }

        if (gameStatus == GameStatus.RUNNING) {
            for (Entity npc : NPCs) {
                npc.update(currentFrameNumber);
            }

            player.update(currentFrameNumber);
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        if (gameStatus == GameStatus.TITLE_SCREEN) {
            // render UI
            ui.draw(graphics2D);
        } else {
            // render map
            tileManager.draw(graphics2D);

            // render game objects
            for (GameObject gameObject : gameObjects) {
                gameObject.draw(graphics2D, this);
            }

            // render NPCs
            for (Entity npc : NPCs) {
                npc.draw(graphics2D);
            }

            // render player
            player.draw(graphics2D);

            // render UI
            ui.draw(graphics2D);
        }

        graphics2D.dispose();
    }

    public void handleRequestedDialog() {
        NPC collidedNPC = (NPC) collisionController.checkEntityForCollision(player, getNPCs());
        GameObject collidedObject = collisionController.checkObjectForCollision(player);

        if (collidedNPC != null) {
            handleRequestedNpcDialog(collidedNPC);
        } else if (collidedObject != null) {
            handleRequestedObjectDialog(collidedObject);
        }
    }

    public void handleRequestedDialogQuestion(GameObject collidedObject) {
        HouseDoor collidedDoor = (HouseDoor) collidedObject;

        if (collidedDoor.getDialog().isDialogSequenceCompleted()) {
            ui.setCurrentDialogLine(null);
            setGameStatus(GameStatus.RUNNING);
            collidedDoor.getDialog().resetDialog();
            return;
        }

        if (collidedDoor.isRiddleAttempted()) {
            collidedDoor.alreadyVisitedResponse();
            getUi().setCurrentDialogLine(collidedDoor.getDialog().currentLine());
            return;
        }

        setGameStatus(GameStatus.DIALOG);

        collidedDoor.speak(this.ui);
        Trick houseTrick = collidedDoor.getTrick();
        getUi().setCurrentDialogLine(collidedDoor.getDialog().currentLine());

        int chosenOption = getUi().getDialogOptionNumber();

        if (chosenOption > 0) {
            char letterForChosenOption = 0;

            switch (chosenOption) {
                case 1 -> letterForChosenOption = 'A';
                case 2 -> letterForChosenOption = 'B';
                case 3 -> letterForChosenOption = 'C';
                case 4 -> letterForChosenOption = 'D';
            }

            if (houseTrick.isCorrectAnswer(letterForChosenOption)) {
                collidedDoor.giveCandy(gameState.getCandyBucket());
            } else {
                collidedDoor.takeCandy(CandyType.RED, gameState.getCandyBucket());
            }

            collidedDoor.setTrickResponse();
            getUi().setCurrentDialogLine(collidedDoor.getDialog().currentLine());
            collidedDoor.getDialog().nextLine();
            getUi().resetDialogOptionNumber();
        }
    }


    private void handleRequestedNpcDialog(NPC collidedNPC) {
        if (collidedNPC.getDialog().isDialogSequenceCompleted()) {
            ui.setCurrentDialogLine(null);
            setGameStatus(GameStatus.RUNNING);
            collidedNPC.getDialog().resetDialog();
            return;
        }

        // reward player w/ rare blue candy for finding ghost costume
        if (collidedNPC.getNpcType() == NPCType.GHOST_KID) {
            // only reward the player once
            if (gameState.getCandyBucket().getCandyQuantity(CandyType.BLUE) < 1) {
                gameState.getCandyBucket().addCandy(CandyType.BLUE);
            }
        }

        setGameStatus(GameStatus.DIALOG);

        collidedNPC.speak(ui);
        getUi().setCurrentDialogLine(collidedNPC.getDialog().currentLine());
        collidedNPC.getDialog().nextLine();
    }

    private void handleRequestedObjectDialog(GameObject collidedObject) {
        handleRequestedDialogQuestion(collidedObject);
    }

    public int getTileSize() {
        return tileSize;
    }

    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public CollisionController getCollisionController() {
        return collisionController;
    }

    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    public UI getUi() {
        return ui;
    }

    public ArrayList<Entity> getNPCs() {
        return NPCs;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameState getGameState() {
        return gameState;
    }
}
