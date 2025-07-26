public class AssetSetter {
    private final GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void initializeObjects() {
        gamePanel.getGameObjects().add(new CandyObject());
        gamePanel.getGameObjects().getFirst().setWorldX(10 * gamePanel.getScaledTileSize());
        gamePanel.getGameObjects().getFirst().setWorldY(10 * gamePanel.getScaledTileSize());
    }
}
