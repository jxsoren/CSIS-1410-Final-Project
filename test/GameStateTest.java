import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameStateTest {

    @Test
    public void testAddCandy() {
        GameState game = new GameState();
        game.addCandy(CandyType.RED);
        assertEquals(1, game.getCandyCount(CandyType.RED));
    }

    @Test
    public void testRemoveGreenCandyPenalty() {
        GameState game = new GameState();
        game.addCandy(CandyType.GREEN);
        assertTrue(game.removeGreenCandyPenalty());
        assertEquals(0, game.getCandyCount(CandyType.GREEN));
    }

    @Test
    public void testRemoveGreenCandyWhenEmpty() {
        GameState game = new GameState();
        assertFalse(game.removeGreenCandyPenalty());
    }

    @Test
    public void testHasWonGame() {
        GameState game = new GameState();
        for (int i = 0; i < 3; i++) game.addCandy(CandyType.RED);
        for (int i = 0; i < 2; i++) game.addCandy(CandyType.GREEN);
        game.addCandy(CandyType.BLUE);
        assertTrue(game.hasWonGame());
    }

    @Test
    public void testProgressSummary() {
        GameState game = new GameState();
        game.addCandy(CandyType.RED);
        assertEquals("Red: 1/3, Green: 0/2, Blue: 0/1", game.getProgressSummary());
    }
}

