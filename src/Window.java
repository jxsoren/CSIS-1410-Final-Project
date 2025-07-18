import javax.swing.*;

public class Window {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Game Window");
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        gamePanel.startGameThread();

        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.pack();
    }

}
