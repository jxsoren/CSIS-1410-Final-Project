import java.util.ArrayList;
import java.util.List;

public class Dialog {
    private final List<DialogLine> dialogLines;
    private int currentLineIndex;
    private boolean dialogSequenceCompleted;

    public Dialog() {
        this.currentLineIndex = 0;
        this.dialogLines = new ArrayList<>();
    }

    public DialogLine currentLine() {
        if (currentLineIndex < dialogLines.size()) {
            return dialogLines.get(currentLineIndex);
        }

        return null;
    }

    public void nextLine() {
        currentLineIndex++;
        if (linesCompleted()) {
            dialogSequenceCompleted = true;
            currentLineIndex = 0;
        }
    }

    public boolean linesCompleted() {
        return currentLineIndex >= dialogLines.size();
    }

    public void addDialogLines(List<DialogLine> dialogLines) {
        this.dialogLines.addAll(dialogLines);
    }

    public boolean isDialogSequenceCompleted() {
        return dialogSequenceCompleted;
    }

    public void resetDialog() {
        currentLineIndex = 0;
        dialogSequenceCompleted = false;
    }

    public void clearDialogLines() {
        this.dialogLines.clear();
    }
}
