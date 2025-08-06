import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class UI {
    private final GamePanel gamePanel;
    private final Font font;
    private String message;
    private Graphics2D graphics2D;
    private DialogLine currentDialogLine;
    private boolean messageOn;
    private int messageCounter;
    private int menuOptionNumber;
    private int dialogOptionNumber;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        InputStream inputStream = getClass().getResourceAsStream("/font/october-crow.ttf");

        try {
            this.font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
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
            case GameStatus.INTRO_SCREEN -> drawIntroScreen();
            case GameStatus.RUNNING -> {
                drawGameState();
                drawDebugInfo();
            }
            case GameStatus.PAUSED -> drawPausedScreen();
            case GameStatus.DIALOG -> drawDialogState();
            case GameStatus.GAME_OVER -> drawEndingCredits();
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
        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawString(titleText, x, y);

        // Image
        x = gamePanel.SCREEN_WIDTH / 2;
        y += gamePanel.getTileSize() * 2;
        BufferedImage redCandyImage = CandyFactory.createCandy(CandyType.RED).getImage();
        graphics2D.drawImage(redCandyImage, x, y, gamePanel.getTileSize() * 2, gamePanel.getTileSize() * 2, null);

        x = gamePanel.SCREEN_WIDTH / 2 - gamePanel.getTileSize() * 2 / 2;
        BufferedImage blueCandyImage = CandyFactory.createCandy(CandyType.BLUE).getImage();
        graphics2D.drawImage(blueCandyImage, x, y, gamePanel.getTileSize() * 2, gamePanel.getTileSize() * 2, null);

        x = gamePanel.SCREEN_WIDTH / 2 - gamePanel.getTileSize() * 4 / 2;
        BufferedImage greenCandyImage = CandyFactory.createCandy(CandyType.GREEN).getImage();
        graphics2D.drawImage(greenCandyImage, x, y, gamePanel.getTileSize() * 2, gamePanel.getTileSize() * 2, null);

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

    private void drawEndingCredits() {
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.fillRect(0, 0, gamePanel.SCREEN_WIDTH, gamePanel.SCREEN_HEIGHT);

        // TITLE

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 70f));
        String titleText = "Thank you for playing!!!";
        int x = getXForCenterText(titleText);
        int y = gamePanel.getTileSize();

        // Shadow
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(titleText, x + 5, y + 5);

        // Text
        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawString(titleText, x, y);

        y += (int) (gamePanel.getTileSize() * 1.5);

        // NOTE

        String noteText = "Made in 3 weeks w/ lots of busy evenings & weekends.";
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 34));
        graphics2D.setColor(Color.PINK);
        graphics2D.drawString(noteText, x, y);

        y += (int) (gamePanel.getTileSize() * 1.5);

        // CONTRIBUTOR SECTION 1

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 50f));
        String endCreditsTitle1 = "Programming:";

        // Shadow
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(endCreditsTitle1, x + 5, y + 5);

        // Text
        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawString(endCreditsTitle1, x, y);

        String[] programmingContributorNames = {
                "• Josh Sorensen",
                "• Matthew Fitzgerald",
                "• Eugene An",
        };

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40f));
        for (String contributorName : programmingContributorNames) {
            y += gamePanel.getTileSize();
            graphics2D.drawString(contributorName, x, y);
        }

        y += (int) (gamePanel.getTileSize() * 1.5);

        // CONTRIBUTOR SECTION 2

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 50f));
        String endCreditsTitle2 = "Art:";

        // Shadow
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(endCreditsTitle2, x + 5, y + 5);

        // Text
        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawString(endCreditsTitle2, x, y);

        String[] artContributorNames = {
                "• Josh Sorensen"
        };

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 40f));
        for (String contributorName : artContributorNames) {
            y += gamePanel.getTileSize();
            graphics2D.drawString(contributorName, x, y);
        }

        // MENU
        graphics2D.setColor(Color.ORANGE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48f));
        String menuText1 = "Quit";
        x = getXForCenterText(menuText1);
        y += gamePanel.getTileSize() * 2;
        graphics2D.drawString(menuText1, x, y);
        graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
    }

    private void drawIntroScreen() {
        graphics2D.setColor(new Color(0, 0, 0));
        graphics2D.fillRect(0, 0, gamePanel.SCREEN_WIDTH, gamePanel.SCREEN_HEIGHT);

        // Tile
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 80));
        String titleText = "Game Objectives";
        int x = getXForCenterText(titleText);
        int y = gamePanel.getTileSize() * 2;
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawString(titleText, x + 5, y + 5);

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(titleText, x, y);

        y += gamePanel.getTileSize();

        // Game Objectives
        String[] gameObjectives = {
                "• Find 3 Red Candies",
                "• Find 2 Green Candies",
                "• Find 1 Blue Candy"
        };

        String[] gameTips = {
                "- Explore the neighborhood to find Red Candies",
                "- Knock on house doors to get Green Candies",
                "- Talk to a particular NPC to discover the Blue Candy"
        };

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 30));
        for (int i = 0; i < gameObjectives.length; i++) {
            y += gamePanel.getTileSize();
            graphics2D.drawString(gameObjectives[i], x, y);

            y += gamePanel.getTileSize() / 2;
            x += gamePanel.getTileSize();
            graphics2D.drawString(gameTips[i], x, y);

            x -= gamePanel.getTileSize();
        }

        y += gamePanel.getTileSize();

        // Start Game
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48f));
        String optionText = "Start Game";
        x = getXForCenterText(optionText);
        y += gamePanel.getTileSize();
        graphics2D.setColor(Color.ORANGE);
        graphics2D.drawString(optionText, x, y);
        graphics2D.drawString(">", x - gamePanel.getTileSize(), y);
    }

    private void drawDialogState() {
        if (currentDialogLine == null) {
            gamePanel.setGameStatus(GameStatus.RUNNING);
            return;
        }

        if (currentDialogLine instanceof Question) {
            drawDialogQuestionState(((Question) currentDialogLine).getQuestion(), ((Question) currentDialogLine).getAnswers());
        }

        if (currentDialogLine instanceof Statement) {
            drawDialogStatement(currentDialogLine.getLine());
        }
    }

    private void drawDialogStatement(String line) {
        int x = gamePanel.getTileSize() * 2;
        int y = gamePanel.getTileSize() / 2;
        int width = gamePanel.SCREEN_WIDTH - (gamePanel.getTileSize() * 4);
        int height = gamePanel.getTileSize() * 4;
        drawSubWindow(x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 42));
        x += gamePanel.getTileSize();
        y += gamePanel.getTileSize();

        graphics2D.drawString(line, x, y);
    }

    private void drawDialogQuestionState(String questionHeader, Map<Character, String> answers) {
        int x = gamePanel.getTileSize() * 2;
        int y = gamePanel.getTileSize() / 2;
        int width = gamePanel.SCREEN_WIDTH - (gamePanel.getTileSize() * 4);
        int height = gamePanel.getTileSize() * 5;
        drawSubWindow(x, y, width, height);

        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 42));
        x += gamePanel.getTileSize() / 2;
        y += gamePanel.getTileSize();

        String[] splitQuestion = questionHeader.split("\n");
        for (String line : splitQuestion) {
            graphics2D.drawString(line, x, y);
            y += gamePanel.getTileSize() / 2;
        }

        x += gamePanel.getTileSize() / 4;

        graphics2D.setColor(Color.RED);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 42));
        int menuOptionPromptY = gamePanel.getTileSize() / 2 * dialogOptionNumber + y + 4;
        graphics2D.drawString(">", x - gamePanel.getTileSize() / 2, menuOptionPromptY);

        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 50));

        for (Map.Entry<Character, String> answerSet : answers.entrySet()) {
            y += gamePanel.getTileSize() / 2;
            graphics2D.drawString(answerSet.getKey() + "). " + answerSet.getValue(), x, y);
        }
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
        graphics2D.setColor(Color.WHITE);

        graphics2D.setFont(graphics2D.getFont().deriveFont(40F));

        int x = gamePanel.getTileSize() / 4;
        int y = gamePanel.getTileSize() / 2;

        Map<CandyType, String> candyCollectionProgress = gamePanel.getGameState().candyCollectionProgress();
        for (Map.Entry<CandyType, String> candyCollectionProgressLine : candyCollectionProgress.entrySet()) {
            BufferedImage candyImage = CandyFactory.createCandy(candyCollectionProgressLine.getKey()).getImage();
            graphics2D.drawImage(candyImage, x, y - gamePanel.getTileSize() / 3, gamePanel.getTileSize() / 2, gamePanel.getTileSize() / 2, null);
            x += gamePanel.getTileSize() / 2;

            String progressLine = candyCollectionProgressLine.getKey() + " " + candyCollectionProgressLine.getValue();
            graphics2D.drawString(progressLine, x + 10, y);

            // Reset X and set Y to go to next line
            x -= gamePanel.getTileSize() / 2;
            y += gamePanel.getTileSize() / 2;
        }

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
        graphics2D.setColor(Color.RED);
        graphics2D.setFont(graphics2D.getFont().deriveFont(30F));

        String playerCoordinates = String.format("World X:  %s  | World Y:  %s", gamePanel.getPlayer().getWorldX(), gamePanel.getPlayer().getWorldY());
        graphics2D.drawString(playerCoordinates, gamePanel.getTileSize() * 14, gamePanel.getTileSize() / 2);

        String screenCoordinates = String.format("Screen X:  %s  | Screen Y:  %s", gamePanel.getPlayer().getScreenX(), gamePanel.getPlayer().getScreenY());
        graphics2D.drawString(screenCoordinates, gamePanel.getTileSize() * 14, gamePanel.getTileSize());

        String worldTileCoordinates = String.format("World X Tile:  %s  | World Y Tile:  %s", gamePanel.getPlayer().getWorldX() / gamePanel.getTileSize(), gamePanel.getPlayer().getWorldY() / gamePanel.getTileSize());
        graphics2D.drawString(worldTileCoordinates, gamePanel.getTileSize() * 14, (int) (gamePanel.getTileSize() * 1.5));
    }

    private void drawPausedScreen() {
        String text = "PAUSED";
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.PLAIN, 100));
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

    public void incrementDialogOptionNumber() {
        if (dialogOptionNumber >= 4) {
            dialogOptionNumber = 1;
        } else {
            dialogOptionNumber++;
        }
    }

    public void decrementDialogOptionNumber() {
        if (dialogOptionNumber <= 1) {
            dialogOptionNumber = 4;
        } else {
            dialogOptionNumber--;
        }
    }

    public void setCurrentDialogLine(DialogLine currentDialogLine) {
        this.currentDialogLine = currentDialogLine;
    }

    public DialogLine getCurrentDialogLine() {
        return currentDialogLine;
    }

    public int getDialogOptionNumber() {
        return dialogOptionNumber;
    }

    public void resetDialogOptionNumber() {
        this.dialogOptionNumber = 0;
    }
}

