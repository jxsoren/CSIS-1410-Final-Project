import java.util.Map;

/**
 * @author Matthew Fitzgerald
 */

public class Trick {
    private final String question;
    private final Map<Character, String> answers;
    private final char correctAnswer;
    private final Statement incorrectTrickMessage;
    private final Statement correctTrickMessage;
    private boolean correctlyAnswered;

    public Trick(String question, Map<Character, String> answers, char correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.correctTrickMessage = new Statement("That's correct! Here's a GREEN Candy!");
        this.incorrectTrickMessage = new Statement("WRONG! I'm stealing one of your RED candies.");
    }

    public boolean isCorrectAnswer(char answerChoice) {
        if (Character.toUpperCase(answerChoice) == Character.toUpperCase(correctAnswer)) {
            correctlyAnswered = true;
            return true;
        }

        return false;
    }

    /**
     * @return Question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @return Choices
     */
    public Map<Character, String> getAnswers() {
        return answers;
    }

    public Statement getTrickMessage() {
        return correctlyAnswered ? correctTrickMessage : incorrectTrickMessage;
    }
}
