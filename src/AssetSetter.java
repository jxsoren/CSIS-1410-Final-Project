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
        candy.setWorldX(11 * gamePanel.getTileSize());
        candy.setWorldY(11 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(candy2);

        Candy candy3 = new Candy();
        candy.setWorldX(12 * gamePanel.getTileSize());
        candy.setWorldY(12 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(candy3);


    }

    public void initializeNPCs() {
        gamePanel.getNPCs().add(new BullyKid(gamePanel));
        gamePanel.getNPCs().getFirst().setWorldX(gamePanel.getTileSize() * 9);
        gamePanel.getNPCs().getFirst().setWorldY(gamePanel.getTileSize() * 9);
    }
}
