import java.awt.*;

public class CollisionController {
    private final GamePanel gamePanel;

    public CollisionController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTileForCollision(Entity entity) {
        Rectangle entityHitBox = entity.getHitBox();
        int tileSize = gamePanel.getScaledTileSize();
        Direction currentDirection = entity.getCurrentDirection();
        int speed = entity.getSpeed();

        int entityLeftEdgeX = entity.getWorldX() + entity.getHitBox().x;
        int entityRightEdgeX = entity.getWorldX() + entityHitBox.x + entityHitBox.width;
        int entityTopEdgeY = entity.getWorldY() + entityHitBox.y;
        int entityBottomEdgeY = entity.getWorldY() + entityHitBox.y + entityHitBox.height;

        int entityLeftColum = entityLeftEdgeX / tileSize;
        int entityRightColum = entityRightEdgeX / tileSize;
        int entityTopRow = entityTopEdgeY / tileSize;
        int entityBottomRow = entityBottomEdgeY / tileSize;

        int firstTileNumber, secondTileNumber;

        switch (currentDirection) {
            case Direction.UP -> {
                entityTopRow = (entityTopEdgeY - speed) / tileSize;

                firstTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityLeftColum][entityTopRow];
                secondTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityRightColum][entityTopRow];

                if (gamePanel.getTileManager().getTiles()[firstTileNumber].hasCollision() || gamePanel.getTileManager().getTiles()[secondTileNumber].hasCollision()) {
                    entity.setCollision(true);
                }
            }
            case Direction.DOWN -> {
                entityBottomRow = (entityBottomEdgeY + speed) / tileSize;

                firstTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityLeftColum][entityBottomRow];
                secondTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityRightColum][entityBottomRow];

                if (gamePanel.getTileManager().getTiles()[firstTileNumber].hasCollision() || gamePanel.getTileManager().getTiles()[secondTileNumber].hasCollision()) {
                    entity.setCollision(true);
                }
            }
            case Direction.LEFT -> {
                entityLeftColum = (entityLeftEdgeX - speed) / tileSize;

                firstTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityLeftColum][entityTopRow];
                secondTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityRightColum][entityBottomRow];

                if (gamePanel.getTileManager().getTiles()[firstTileNumber].hasCollision() || gamePanel.getTileManager().getTiles()[secondTileNumber].hasCollision()) {
                    entity.setCollision(true);
                }
            }
            case Direction.RIGHT -> {
                entityRightColum = (entityRightEdgeX + speed) / tileSize;

                firstTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityRightColum][entityTopRow];
                secondTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityRightColum][entityBottomRow];

                if (gamePanel.getTileManager().getTiles()[firstTileNumber].hasCollision() || gamePanel.getTileManager().getTiles()[secondTileNumber].hasCollision()) {
                    entity.setCollision(true);
                }
            }
        }

    }
}
