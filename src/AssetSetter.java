import java.util.List;

public class AssetSetter {
    private final GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void initializeObjects() {
        // Candy
        Candy candy = new Candy();
        candy.setWorldX(10 * gamePanel.getTileSize());
        candy.setWorldY(10 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(candy);

        Candy candy2 = new Candy();
        candy2.setWorldX(10 * gamePanel.getTileSize());
        candy2.setWorldY(12 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(candy2);

        Candy candy3 = new Candy();
        candy3.setWorldX(10 * gamePanel.getTileSize());
        candy3.setWorldY(14 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(candy3);

        HouseDoor door1 = new HouseDoor();
        door1.getDialog().setDialogLines(List.of("Trick or Treat???", "Trick!!", "Treat!!"));
        door1.setWorldX(15 * gamePanel.getTileSize());
        door1.setWorldY(6 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(door1);
    }

    public void initializeNPCs() {
        gamePanel.getNPCs().add(new BullyKid(gamePanel));
        gamePanel.getNPCs().getFirst().setWorldX(gamePanel.getTileSize() * 10);
        gamePanel.getNPCs().getFirst().setWorldY(gamePanel.getTileSize() * 2);
    }
}
