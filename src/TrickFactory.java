import java.util.HashMap;
import java.util.Map;

public class TrickFactory {
    public static Trick createTrick(int trickNumber) {
        return switch (trickNumber) {
            case 1 -> new Trick(
                    "I have a head and no body.\nMy smile lights up the night.\nWhat am I?",
                    new HashMap<>(Map.of(
                            'A', "A Ghoul",
                            'B', "A Witch",
                            'C', "A Jack-o'-lantern",
                            'D', "A Skeleton"
                    )),
                    'C'
            );
            case 2 -> new Trick(
                    "I have hundreds of ears, but I can’t hear a thing.\nWhat am I?",
                    new HashMap<>(Map.of(
                            'A', "A Spider",
                            'B', "A Cornfield",
                            'C', "A Bat",
                            'D', "A Scarecrow"
                    )),
                    'B'
            );
            case 3 -> new Trick(
                    "I have a body, arms, legs and a head, but I’m heartless and have no guts.\nWhat am I?",
                    new HashMap<>(Map.of(
                            'A', "A Zombie",
                            'B', "A Scarecrow",
                            'C', "A Mummy",
                            'D', "A Skeleton"
                    )),
                    'D'
            );
            default -> new Trick(
                    " I have a body, arms, legs and a head, but I’m heartless and have no guts.\nWhat am I?",
                    new HashMap<>(Map.of(
                            'A', "A Zombie",
                            'B', "A Scarecrow",
                            'C', "A Mummy",
                            'D', "A Skeleton"
                    )),
                    'D'
            );
        };
    }
}
