public class Trick {
    private final String Question;
    private final String[] Choices;
    private final char answer;
    private final String SnarkyMessage;

    /**
     * create a Trick (Riddle) with:
     *
     * @param question:      String, Riddle to be solved
     * @param answers:       String Array, multiple choices provided
     * @param correctChoice: char, correct option
     * @param snarkyMessage: String, failure message
     */
    public Trick(String question, String[] answers, char correctChoice, String snarkyMessage) {
        this.Question = question;
        this.Choices = answers;
        this.answer = correctChoice;
        this.SnarkyMessage = snarkyMessage;
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
        return SnarkyMessage;
    }

    /**
     * @return Question
     */
    public String getQuestion() {
        return Question;
    }

    /**
     * @return Choices
     */
    public String[] getChoices() {
        return Choices;
    }
}
