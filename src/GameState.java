import java.util.HashMap;
import java.util.Map;

public class GameState {
    private final CandyBucket candyBucket;
    private final Map<CandyType, Integer> candyCollectionQuotas = new HashMap<>(Map.ofEntries(
            Map.entry(CandyType.RED, 3),
            Map.entry(CandyType.GREEN, 2),
            Map.entry(CandyType.BLUE, 1)
    ));

    public GameState() {
        candyBucket = new CandyBucket();
    }

    public CandyBucket getCandyBucket() {
        return candyBucket;
    }

    public boolean gameWinConditionMet() {
        return candyBucket.getCandyQuantity(CandyType.RED) >= candyCollectionQuotas.get(CandyType.RED)
                && candyBucket.getCandyQuantity(CandyType.GREEN) >= candyCollectionQuotas.get(CandyType.GREEN)
                && candyBucket.getCandyQuantity(CandyType.BLUE) >= candyCollectionQuotas.get(CandyType.BLUE);
    }

    public Map<CandyType, String> candyCollectionProgress() {
        return new HashMap<>() {{
            for (CandyType candyType : CandyType.values()) {
                put(candyType, String.format("%s/%s", candyBucket.getCandyQuantity(candyType), candyCollectionQuotas.get(candyType)));
            }
        }};
    }
}