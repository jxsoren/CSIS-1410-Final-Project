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

                    // Need to add logic that basically will end the dialog if

                    // What if I make it so that this will set the game state in a status of dialog
                    // and then wherever I process the dialog state, I could check to see if there
                    // are any more dialog lines remaining and if there are, then we need to find
                    // some way to iterate through that list

                    case KeyEvent.VK_ENTER -> gamePanel.setDialogState();
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
