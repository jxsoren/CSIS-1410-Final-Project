

public class Trick {
    private final String question;
    private final String[] choices;
    private final char answer;
    private final String snarkyMessage;

    /**
     * create a Trick (Riddle) with:
     *
     * @param question:      String, Riddle to be solved
     * @param answers:       String Array, multiple choices provided
     * @param correctChoice: char, correct option
     * @param snarkyMessage: String, failure message
     */
    public Trick(String question, String[] answers, char correctChoice, String snarkyMessage) {
        correctChoice = Character.toUpperCase(correctChoice);
        if ((question == null) || (question.isEmpty())) {
            throw new NullPointerException("question must not be null");
        }

        if (answers.length == 0) {
            throw new NullPointerException("did not provide choices");
        }
        if (correctChoice != 'A' && correctChoice != 'B' && correctChoice != 'C' && correctChoice != 'D') {
            throw new IllegalArgumentException("correct answer must be: 'A', 'B' , 'C' or 'D' ");
        }

        if ((snarkyMessage == null) || (snarkyMessage.isEmpty())) {
            throw new NullPointerException("snarkyMessage must not be null");
        }

        this.question = question;
        this.choices = answers;
        this.answer = correctChoice;
        this.snarkyMessage = snarkyMessage;
    }

    public boolean checkAnswer(char playerChoice) {
        char upperChoice = Character.toUpperCase(playerChoice);
        if (upperChoice == 'A' || upperChoice == 'B' || upperChoice == 'C' || upperChoice == 'D') {
            return upperChoice == answer;
        } else {
            throw new IllegalArgumentException("bad input: choose A, B, C or D");
        }
    }

    /**
     * @return SnarkyMessage
     */
    public String getSnarkyMessage() {
        return snarkyMessage;
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
    public String[] getChoices() {
        return choices;
    }
}
