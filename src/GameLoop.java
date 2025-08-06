public class GameLoop implements Runnable {
    private Thread gameThread;
    private final GamePanel gamePanel;

    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * This method gets called after the thread has started
     */
    @Override
    public void run() {
        int FPS = 60;

        double oneSecondInNanoSeconds = 1_000_000_000d;
        double repaintInterval = oneSecondInNanoSeconds / FPS; // repaint screen 60 times per second (every ~16ms)
        double delta = 0;
        long previousRepaintTime = System.nanoTime();
        long currentRepaintTime;
        int currentFrameNumber = 1;

        while (gameThread != null) {
            currentRepaintTime = System.nanoTime(); // get current time in nanoseconds for high precision

            double elapsedTime = currentRepaintTime - previousRepaintTime;
            delta += elapsedTime / repaintInterval;

            // previous repaint time now becomes the repaint time that just occurred
            previousRepaintTime = currentRepaintTime;

            if (delta >= 1) {
                currentFrameNumber++;

                if (currentFrameNumber > FPS) {
                    currentFrameNumber = 1;
                }

                gamePanel.update(currentFrameNumber);
                gamePanel.repaint();
                delta--;
            }
        }
    }
}
