import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    public boolean up, down, left, right, enterPressed;
    private final GamePanel gamePanel;

    public InputHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode(); // keycode associated with inputted key

        switch (gamePanel.getGameStatus()) {
            case GameStatus.TITLE_SCREEN -> {
                switch (keyCode) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> gamePanel.getUi().decrementMenuOptionNumber();
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> gamePanel.getUi().incrementMenuOptionNumber();
                    case KeyEvent.VK_ENTER -> {
                        switch (gamePanel.getUi().getMenuOptionNumber()) {
                            case 0 -> gamePanel.setGameStatus(GameStatus.RUNNING);
                            case 1 -> System.exit(0);
                        }
                    }
                }
            }
            case GameStatus.RUNNING -> {
                switch (keyCode) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> up = true;
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> down = true;
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> left = true;
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = true;
                    case KeyEvent.VK_ESCAPE -> gamePanel.setGameStatus(GameStatus.PAUSED);
                    case KeyEvent.VK_ENTER -> gamePanel.handleRequestedDialog();
                }
            }
            case GameStatus.PAUSED -> {
                switch (keyCode) {
                    case KeyEvent.VK_ESCAPE -> gamePanel.setGameStatus(GameStatus.RUNNING);
                }
            }
            case GameStatus.DIALOG -> {
                switch (keyCode) {
                    case KeyEvent.VK_ENTER -> gamePanel.handleRequestedDialog();
                    case KeyEvent.VK_Q -> gamePanel.setGameStatus(GameStatus.RUNNING);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode(); // keycode associated with inputted key

        switch (keyCode) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> up = false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> down = false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = false;
        }
    }
}
