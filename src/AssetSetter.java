import java.util.List;

public class AssetSetter {
    private final GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void initializeObjects() {
        // Candy
        Candy redCandy1 = CandyFactory.createCandy(CandyType.RED);
        redCandy1.setWorldX(31 * gamePanel.getTileSize());
        redCandy1.setWorldY(22 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(redCandy1);

        Candy redCandy2 = CandyFactory.createCandy(CandyType.RED);
        redCandy2.setWorldX(14 * gamePanel.getTileSize());
        redCandy2.setWorldY(16 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(redCandy2);

        Candy redCandy3 = CandyFactory.createCandy(CandyType.RED);
        redCandy3.setWorldX(58 * gamePanel.getTileSize());
        redCandy3.setWorldY(34 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(redCandy3);

        Candy redCandy4 = CandyFactory.createCandy(CandyType.RED);
        redCandy4.setWorldX(45 * gamePanel.getTileSize());
        redCandy4.setWorldY(14 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(redCandy4);

        Candy redCandy5 = CandyFactory.createCandy(CandyType.RED);
        redCandy5.setWorldX(63 * gamePanel.getTileSize());
        redCandy5.setWorldY(20 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(redCandy5);

        Candy redCandy6 = CandyFactory.createCandy(CandyType.RED);
        redCandy6.setWorldX(19 * gamePanel.getTileSize());
        redCandy6.setWorldY(37 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(redCandy6);

        HouseDoor door1 = new HouseDoor(CandyType.GREEN, "tiles/mapTiles/tile_103.png", 1);
        door1.setWorldX(24 * gamePanel.getTileSize());
        door1.setWorldY(24 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(door1);

        HouseDoor door2 = new HouseDoor(CandyType.GREEN, "tiles/mapTiles/tile_237.png", 2);
        door2.setWorldX(38 * gamePanel.getTileSize());
        door2.setWorldY(24 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(door2);

        HouseDoor door3 = new HouseDoor(CandyType.GREEN, "tiles/mapTiles/tile_255.png", 3);
        door3.setWorldX(52 * gamePanel.getTileSize());
        door3.setWorldY(24 * gamePanel.getTileSize());
        gamePanel.getGameObjects().add(door3);
    }

    public void initializeNPCs() {
        gamePanel.getNPCs().addAll(List.of(
                NPCFactory.createNPC(gamePanel, NPCType.REGULAR_KID),
                NPCFactory.createNPC(gamePanel, NPCType.ZOMBIE_KID),
                NPCFactory.createNPC(gamePanel, NPCType.GHOST_KID)
        ));
    }
}
