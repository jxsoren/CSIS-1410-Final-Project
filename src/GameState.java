public class GameState {
    private final CandyBucket candyBucket;

    public GameState() {
        candyBucket = new CandyBucket();
    }

    public boolean gameWinConditionMet() {
        return candyBucket.getCandyQuantity(CandyType.RED) == 3 &&
                candyBucket.getCandyQuantity(CandyType.GREEN) == 2 &&
                candyBucket.getCandyQuantity(CandyType.BLUE) == 1;
    }

    public String getProgressSummary() {
        return "Red: " + candyBucket.getCandyQuantity(CandyType.RED) + "/3, " +
                "Green: " + candyBucket.getCandyQuantity(CandyType.GREEN) + "/2, " +
                "Blue: " + candyBucket.getCandyQuantity(CandyType.BLUE) + "/1";
    }
}