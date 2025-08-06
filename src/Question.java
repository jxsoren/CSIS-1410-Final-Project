import java.util.Map;

public class Question extends DialogLine {
    private final String question;
    private final Map<Character, String> answers;

    public Question(String question, Map<Character, String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public Map<Character, String> getAnswers() {
        return answers;
    }
}
