import javax.swing.*;

public class Window {
    private static JFrame window;
    private static GamePanel gamePanel;

    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Game Window");
        window.setResizable(false);

        gamePanel = new GamePanel();
        window.add(gamePanel);

        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.pack();

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

}
