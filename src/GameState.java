import java.util.EnumMap;
import java.util.Map;

public class GameState {
    private final Map<CandyType, Integer> candyInventory;

    public GameState() {
        candyInventory = new EnumMap<>(CandyType.class);
        for (CandyType type : CandyType.values()) {
            candyInventory.put(type, 0);
        }
    }

    public void addCandy(CandyType type) {
        candyInventory.put(type, candyInventory.get(type) + 1);
    }

    public boolean removeGreenCandyPenalty() {
        int count = candyInventory.get(CandyType.GREEN);
        if (count > 0) {
            candyInventory.put(CandyType.GREEN, count - 1);
            return true;
        }
        return false;
    }

    public int getCandyCount(CandyType type) {
        return candyInventory.get(type);
    }

    public boolean hasWonGame() {
        return candyInventory.get(CandyType.RED) == 3 &&
                candyInventory.get(CandyType.GREEN) == 2 &&
                candyInventory.get(CandyType.BLUE) == 1;
    }

    public String getProgressSummary() {
        return "Red: " + candyInventory.get(CandyType.RED) + "/3, " +
                "Green: " + candyInventory.get(CandyType.GREEN) + "/2, " +
                "Blue: " + candyInventory.get(CandyType.BLUE) + "/1";
    }
}


