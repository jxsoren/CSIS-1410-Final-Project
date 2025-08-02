import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TestTrick {

    private Trick riddle;


    @BeforeEach
    void setUp()  {
        riddle = new Trick("What do you call a witch's garage?",
                new String[]{"Broom closet", "Spell shed", "Cauldron cave", "Potion parking"},
                'A',
                "Did you eat all your candy already?");

    }


    @Test
    void testBadSnarky() {
        // bad snarky
        assertThrows(NullPointerException.class, () -> new Trick("hello world",
                new String[]{"Broom closet", "Spell shed", "Cauldron cave", "Potion parking"},
                'A',
                null));
    }

    @Test
    void testBadRiddle() {
        assertThrows(NullPointerException.class, () -> new Trick(null,
                new String[]{"Broom closet", "Spell shed", "Cauldron cave", "Potion parking"},
                'A',
                "Did you eat all your candy already?"));
    }

    @Test
    void testBadMultipleChoice() {
        assertThrows(NullPointerException.class, () -> new Trick("hello world",
                new String[]{},
                'Z',
                "hello world"));
    }

    @Test
    void testBadCorrectAnswer() {
        assertThrows(IllegalArgumentException.class, () -> new Trick("hello world",
                new String[]{"Broom closet", "Spell shed", "Cauldron cave", "Potion parking"},
                'Z',
                "hello world"));
    }

    @Test
    void testCheckAnswer_CorrectAnswer() {
        assertTrue(riddle.checkAnswer('A'));
    }

    @Test
    void testCheckAnswer_IncorrectAnswer() {
        assertFalse(riddle.checkAnswer('B'));
    }


    @Test
    void testGetSnarkyMessage() {
        assertEquals(riddle.getSnarkyMessage(), "Did you eat all your candy already?");
    }


    @Test
    void testGetQuestion() {
        assertEquals(riddle.getQuestion(), "What do you call a witch's garage?");
    }


    @Test
    void testGetChoices() {
        assertEquals(Arrays.toString(riddle.getChoices()),
                Arrays.toString(new String[]{"Broom closet", "Spell shed", "Cauldron cave", "Potion parking"}));
    }
    @Test
    void testInvalidChoices() {
        assertThrows(IllegalArgumentException.class,() -> riddle.checkAnswer('Z') );
    }
}