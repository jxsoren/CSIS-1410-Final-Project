import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MyServiceTest {

    private Trick riddle;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        riddle = new Trick("What do you call a witch's garage?",
                new String[]{"Broom closet", "Spell shed", "Cauldron cave", "Potion parking"},
                'A',
                "Did you eat all your candy already?");

    }


    @Test
    void testCheckAnswer_CorrectAnswer() {
        assertEquals(riddle.checkAnswer('A'), true);
    }

    @Test
    void testCheckAnswer_IncorrectAnswer() {
        assertEquals(riddle.checkAnswer('B'), false);
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