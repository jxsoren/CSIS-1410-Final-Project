import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private final int baseTileSize = 16;
    private final int tileSizeScale = 3;
    private final int tileSize = baseTileSize * tileSizeScale;

    // World Settings
    public final int MAX_WORLD_COLUMNS = 40;
    public final int MAX_WORLD_ROWS = 30;

    public final int WORLD_WIDTH = tileSize * MAX_WORLD_COLUMNS;
    public final int WORLD_HEIGHT = tileSize * MAX_WORLD_ROWS;

    // Screen Settings
    public final int MAX_WINDOW_COLUMNS = 16;
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

    public GamePanel() {
        inputHandler = new InputHandler(this);
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
    }

    public void handleRequestedDialog() {
        int npcCollisionIndex = collisionController.checkEntityForCollision(player, getNPCs());
        int objectCollisionIndex = collisionController.checkObjectForCollision(player);

        if (npcCollisionIndex >= 0) {
            handleRequestedNpcDialog(npcCollisionIndex);
        } else if (objectCollisionIndex >= 0) {
            handleRequestedObjectDialog(objectCollisionIndex);
        }
    }

    private void handleRequestedNpcDialog(int npcCollisionIndex) {
        setGameStatus(GameStatus.DIALOG);

        NPC npc = (NPC) getNPCs().get(npcCollisionIndex);

        npc.speak();
        getUi().setCurrentDialogLine(npc.getDialog().currentLine());
        npc.getDialog().nextLine();
    }

    private void handleRequestedObjectDialog(int objectCollisionIndex) {
        setGameStatus(GameStatus.DIALOG);

        GameObject gameObject = gameObjects.get(objectCollisionIndex);

        if (!(gameObject instanceof Speakable)) {
            return;
        }

        ((Speakable) gameObject).speak();
        getUi().setCurrentDialogLine(((Speakable) gameObject).getDialog().currentLine());
        ((Speakable) gameObject).getDialog().nextLine();
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
}
