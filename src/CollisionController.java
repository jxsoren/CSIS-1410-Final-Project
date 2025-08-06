import java.util.ArrayList;

public class CollisionController {
    private final GamePanel gamePanel;

    public CollisionController(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTileForCollision(Entity entity) {
        HitBox entityHitBox = entity.getHitBox();
        Direction currentDirection = entity.getCurrentDirection();
        int tileSize = gamePanel.getTileSize();
        int speed = entity.getSpeed();

        int entityLeftEdgeX = entity.getWorldX() + entityHitBox.x;
        int entityRightEdgeX = entity.getWorldX() + entityHitBox.x + entityHitBox.width;
        int entityTopEdgeY = entity.getWorldY() + entityHitBox.y;
        int entityBottomEdgeY = entity.getWorldY() + entityHitBox.y + entityHitBox.height;

        int entityLeftColum = entityLeftEdgeX / tileSize;
        int entityRightColum = entityRightEdgeX / tileSize;
        int entityTopRow = entityTopEdgeY / tileSize;
        int entityBottomRow = entityBottomEdgeY / tileSize;

        int firstTileNumber, secondTileNumber;

        checkTilesForCollision(entity.getWorldX() + speed >= gamePanel.WORLD_WIDTH, entity);

        switch (currentDirection) {
            case Direction.UP -> {
                entityTopRow = (entityTopEdgeY - speed) / tileSize;

                firstTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityLeftColum][entityTopRow];
                secondTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityRightColum][entityTopRow];

                checkTilesForCollision(gamePanel.getTileManager().getTiles().get(firstTileNumber).hasCollision() || gamePanel.getTileManager().getTiles().get(secondTileNumber).hasCollision(), entity);
            }
            case Direction.DOWN -> {
                entityBottomRow = (entityBottomEdgeY + speed) / tileSize;

                firstTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityLeftColum][entityBottomRow];
                secondTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityRightColum][entityBottomRow];

                checkTilesForCollision(gamePanel.getTileManager().getTiles().get(firstTileNumber).hasCollision() || gamePanel.getTileManager().getTiles().get(secondTileNumber).hasCollision(), entity);
            }
            case Direction.LEFT -> {
                entityLeftColum = (entityLeftEdgeX - speed) / tileSize;

                firstTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityLeftColum][entityTopRow];
                secondTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityRightColum][entityBottomRow];

                checkTilesForCollision(gamePanel.getTileManager().getTiles().get(firstTileNumber).hasCollision() || gamePanel.getTileManager().getTiles().get(secondTileNumber).hasCollision(), entity);
            }
            case Direction.RIGHT -> {
                entityRightColum = (entityRightEdgeX + speed) / tileSize;

                firstTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityRightColum][entityTopRow];
                secondTileNumber = gamePanel.getTileManager().getTileMapNumbers()[entityRightColum][entityBottomRow];

                checkTilesForCollision(gamePanel.getTileManager().getTiles().get(firstTileNumber).hasCollision() || gamePanel.getTileManager().getTiles().get(secondTileNumber).hasCollision(), entity);
            }
        }
    }

    private void checkTilesForCollision(boolean gamePanel, Entity entity) {
        if (gamePanel) {
            entity.setCollision(true);
        }
    }

    public GameObject checkObjectForCollision(Entity entity) {
        GameObject collidedGameObject = null;

        for (GameObject gameObject : gamePanel.getGameObjects()) {
            // update entity's hit box to their current position in the world
            entity.getHitBox().x = entity.getWorldX() + entity.getHitBox().x;
            entity.getHitBox().y = entity.getWorldY() + entity.getHitBox().y;

            // update object's hit box to its current position in the world
            gameObject.getHitBox().x += gameObject.getWorldX();
            gameObject.getHitBox().y += gameObject.getWorldY();

            switch (entity.getCurrentDirection()) {
                case Direction.UP -> {
                    entity.getHitBox().y -= entity.getSpeed();
                    if (collisionDetected(entity, gameObject)) {
                        checkTilesForCollision(gameObject.hasCollision(), entity);
                        collidedGameObject = gameObject;
                    }
                }
                case Direction.DOWN -> {
                    entity.getHitBox().y += entity.getSpeed();
                    if (collisionDetected(entity, gameObject)) {
                        checkTilesForCollision(gameObject.hasCollision(), entity);
                        collidedGameObject = gameObject;
                    }
                }
                case Direction.LEFT -> {
                    entity.getHitBox().x -= entity.getSpeed();
                    if (collisionDetected(entity, gameObject)) {
                        checkTilesForCollision(gameObject.hasCollision(), entity);
                        collidedGameObject = gameObject;
                    }
                }
                case Direction.RIGHT -> {
                    entity.getHitBox().x += entity.getSpeed();
                    if (collisionDetected(entity, gameObject)) {
                        checkTilesForCollision(gameObject.hasCollision(), entity);
                        collidedGameObject = gameObject;
                    }
                }
            }

            // reset hit boxes to original state
            entity.getHitBox().reset();
            gameObject.getHitBox().reset();
        }

        return collidedGameObject;
    }

    public Entity checkEntityForCollision(Entity entity, ArrayList<Entity> otherEntities) {
        Entity collidedEntity = null;

        for (Entity otherEntity : otherEntities) {
            // update entity's hit box to their current position in the world
            entity.getHitBox().x = entity.getWorldX() + entity.getHitBox().x;
            entity.getHitBox().y = entity.getWorldY() + entity.getHitBox().y;

            // update other entity's hit box to their current position in the world
            otherEntity.getHitBox().x = otherEntity.getWorldX() + otherEntity.getHitBox().x;
            otherEntity.getHitBox().y = otherEntity.getWorldY() + otherEntity.getHitBox().y;

            switch (entity.getCurrentDirection()) {
                case Direction.UP -> {
                    entity.getHitBox().y -= entity.getSpeed();
                    if (collisionDetected(entity, otherEntity)) {
                        entity.setCollision(true);
                        collidedEntity = otherEntity;
                    }
                }
                case Direction.DOWN -> {
                    entity.getHitBox().y += entity.getSpeed();
                    if (collisionDetected(entity, otherEntity)) {
                        entity.setCollision(true);
                        collidedEntity = otherEntity;
                    }
                }
                case Direction.LEFT -> {
                    entity.getHitBox().x -= entity.getSpeed();
                    if (collisionDetected(entity, otherEntity)) {
                        entity.setCollision(true);
                        collidedEntity = otherEntity;
                    }
                }
                case Direction.RIGHT -> {
                    entity.getHitBox().x += entity.getSpeed();
                    if (collisionDetected(entity, otherEntity)) {
                        entity.setCollision(true);
                        collidedEntity = otherEntity;
                    }
                }
            }

            // reset hit boxes to original state
            entity.getHitBox().reset();
            otherEntity.getHitBox().reset();
        }

        return collidedEntity;
    }

    public void checkPlayerForCollision(Entity entity) {
        // get entity's solid area coords
        entity.getHitBox().x = entity.getWorldX() + entity.getHitBox().x;
        entity.getHitBox().y = entity.getWorldY() + entity.getHitBox().y;

        // get the object's solid area coords
        gamePanel.getPlayer().getHitBox().x += gamePanel.getPlayer().getWorldX();
        gamePanel.getPlayer().getHitBox().y += gamePanel.getPlayer().getWorldY();

        switch (entity.getCurrentDirection()) {
            case Direction.UP -> {
                entity.getHitBox().y -= entity.getSpeed();
                checkTilesForCollision(collisionDetected(entity, gamePanel.getPlayer()), entity);
            }
            case Direction.DOWN -> {
                entity.getHitBox().y += entity.getSpeed();
                checkTilesForCollision(collisionDetected(entity, gamePanel.getPlayer()), entity);
            }
            case Direction.LEFT -> {
                entity.getHitBox().x -= entity.getSpeed();
                checkTilesForCollision(collisionDetected(entity, gamePanel.getPlayer()), entity);
            }
            case Direction.RIGHT -> {
                entity.getHitBox().x += entity.getSpeed();
                checkTilesForCollision(collisionDetected(entity, gamePanel.getPlayer()), entity);
            }
        }

        entity.getHitBox().reset();
        gamePanel.getPlayer().getHitBox().reset();
    }

    private static boolean collisionDetected(Entity entity, GameObject gameObject) {
        return entity.getHitBox().intersects(gameObject.getHitBox());
    }

    private static boolean collisionDetected(Entity entity, Entity targetEntity) {
        return entity.getHitBox().intersects(targetEntity.getHitBox());
    }
}

