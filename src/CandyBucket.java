import java.util.EnumMap;
import java.util.Map;

/**
 * Represents a trick-or-treater's candy bucket for collecting Halloween candy.
 * Tracks the quantity of each candy type collected during trick-or-treating.
 *
 * @author Josh Sorensen
 */

public class CandyBucket {
    private final Map<CandyType, Integer> candyStash;

    public CandyBucket() {
        this.candyStash = new EnumMap<>(CandyType.class);
        initializeCandyStash();
    }

    public int getCandyQuantity(CandyType candyType) {
        return candyStash.get(candyType);
    }

    public void addCandy(CandyType candyType) {
        int candyQuantity = candyStash.get(candyType);
        candyStash.put(candyType, candyQuantity + 1);
    }

    public void removeCandy(CandyType candyType) {
        int candyQuantity = candyStash.get(candyType);
        //        if (candyQuantity > 0) {
        //        }
        candyStash.put(candyType, candyQuantity - 1);
    }

    private void initializeCandyStash() {
        for (CandyType type : CandyType.values()) {
            candyStash.put(type, 0);
        }
    }
}
