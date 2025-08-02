import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    private final GamePanel gamePanel;
    private final Font font;
    private final BufferedImage objectImage;
    private String message;
    private Graphics2D graphics2D;
    private String currentDialogLine;
    private boolean messageOn;
    private int messageCounter;
    private int menuOptionNumber;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        InputStream inputStream = getClass().getResourceAsStream("/font/to-the-point.ttf");

        try {
            this.font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }

        Candy candy = new Candy();
        this.objectImage = candy.getImage();
    }

    public void setMessage(String message) {
        this.message = message;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
        this.graphics2D.setFont(font);

        switch (gamePanel.getGameStatus()) {
            case GameStatus.TITLE_SCREEN -> drawTitleScreen();
            case GameStatus.RUNNING -> {
                drawGameState();
                drawDebugInfo();
            }
            case GameStatus.PAUSED -> drawPausedScreen();
            case GameStatus.DIALOG -> drawDialogState();
        }
    }

    private void drawTitleScreen() {
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.fillRect(0, 0, gamePanel.SCREEN_WIDTH, gamePanel.SCREEN_HEIGHT);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96f));
        String titleText = "Candy Quest";
        int x = getXForCenterText(titleText);
        int y = gamePanel.getTileSize() * 3;

        // Shadow
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(titleText, x + 5, y + 5);

        // Text
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(titleText, x, y);

        // Image
        x = gamePanel.SCREEN_WIDTH / 2 - gamePanel.getTileSize() * 2 / 2;
        y += gamePanel.getTileSize() * 2;
        BufferedImage candyImage = new Candy().getImage();
        graphics2D.drawImage(candyImage, x, y, gamePanel.getTileSize() * 2, gamePanel.getTileSize() * 2, null);

        // Menu
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48f));

        String menuText1 = "New Game";
        x = getXForCenterText(menuText1);
        y += gamePanel.getTileSize() * 4;
        graphics2D.drawString(menuText1, x, y);
        if (menuOptionNumber == 0) {
            graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
        }

        String menuText2 = "Quit";
        x = getXForCenterText(menuText2);
        y += gamePanel.getTileSize();
        graphics2D.drawString(menuText2, x, y);
        if (menuOptionNumber == 1) {
            graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
        }
    }

    private void drawDialogState() {
        int x = gamePanel.getTileSize() * 2;
        int y = gamePanel.getTileSize() / 2;
        int width = gamePanel.SCREEN_WIDTH - (gamePanel.getTileSize() * 4);
        int height = gamePanel.getTileSize() * 4;
        drawSubWindow(x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 42));
        x += gamePanel.getTileSize();
        y += gamePanel.getTileSize();

        graphics2D.drawString(currentDialogLine, x, y);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color black = new Color(0, 0, 0, 150);
        graphics2D.setColor(black);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        Color white = new Color(255, 255, 255);
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.setColor(white);
        graphics2D.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    private void drawGameState() {
        graphics2D.setFont(font);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawImage(objectImage, gamePanel.getTileSize() / 2, gamePanel.getTileSize() / 2, gamePanel.getTileSize(), gamePanel.getTileSize(), null);
        graphics2D.drawString("x" + gamePanel.getPlayer().getCandyCount(), 75, 60);

        if (messageOn) {
            graphics2D.setFont(graphics2D.getFont().deriveFont(30F));
            graphics2D.drawString(message, gamePanel.getTileSize() / 2, gamePanel.getTileSize() * 5);
            messageCounter++;

            if (messageCounter >= 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }

    private void drawDebugInfo() {
        graphics2D.setColor(Color.YELLOW);
        graphics2D.setFont(graphics2D.getFont().deriveFont(30F));

        String playerCoordinates = String.format("World X:  %s  | World Y:  %s", gamePanel.getPlayer().getWorldX(), gamePanel.getPlayer().getWorldY());
        graphics2D.drawString(playerCoordinates, gamePanel.getTileSize() / 2, gamePanel.getTileSize() * 3);

        String screenCoordinates = String.format("Screen X:  %s  | Screen Y:  %s", gamePanel.getPlayer().getScreenX(), gamePanel.getPlayer().getScreenY());
        graphics2D.drawString(screenCoordinates, gamePanel.getTileSize() / 2, gamePanel.getTileSize() * 4);

        String worldTileCoordinates = String.format("World X Tile:  %s  | World Y Tile:  %s", gamePanel.getPlayer().getWorldX() / gamePanel.getTileSize(), gamePanel.getPlayer().getWorldY() / gamePanel.getTileSize());
        graphics2D.drawString(worldTileCoordinates, gamePanel.getTileSize() / 2, gamePanel.getTileSize() * 5);
    }

    private void drawPausedScreen() {
        String text = "PAUSED";
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 80));
        int x = getXForCenterText(text);
        int y = gamePanel.SCREEN_HEIGHT / 2;
        graphics2D.drawString(text, x, y);
    }

    private int getXForCenterText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.SCREEN_WIDTH / 2 - length / 2;
    }

    public int getMenuOptionNumber() {
        return menuOptionNumber;
    }

    public void incrementMenuOptionNumber() {
        if (menuOptionNumber >= 1) {
            menuOptionNumber = 0;
        } else {
            menuOptionNumber++;
        }
    }

    public void decrementMenuOptionNumber() {
        if (menuOptionNumber <= 0) {
            menuOptionNumber = 1;
        } else {
            menuOptionNumber--;
        }
    }

    public void setCurrentDialogLine(String currentDialogLine) {
        this.currentDialogLine = currentDialogLine;
    }
}

