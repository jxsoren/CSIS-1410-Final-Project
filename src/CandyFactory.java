public class CandyFactory {
    public static Candy createCandy(CandyType candyType) {
        return switch (candyType) {
            case CandyType.RED -> new Candy(CandyType.RED, "/objects/red-candy.png");
            case CandyType.GREEN -> new Candy(CandyType.GREEN, "/objects/green-candy.png");
            case CandyType.BLUE -> new Candy(CandyType.BLUE, "/objects/blue-candy.png");
        };
    }
}
