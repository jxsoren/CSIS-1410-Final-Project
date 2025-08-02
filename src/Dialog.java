import java.util.ArrayList;
import java.util.List;

public class Dialog {
    private final List<String> dialogLines;
    private int currentLineIndex;

    public Dialog() {
        this.currentLineIndex = 0;
        this.dialogLines = new ArrayList<>();
    }

    public String currentLine() {
        if (currentLineIndex < dialogLines.size()) {
            return dialogLines.get(currentLineIndex);
        }

        return null;
    }

    public void nextLine() {
        currentLineIndex++;
        if (linesCompleted()) {
            currentLineIndex = 0;
        }
    }

    public boolean linesCompleted() {
        return currentLineIndex >= dialogLines.size();
    }

    public void setDialogLines(List<String> dialogLines) {
        this.dialogLines.addAll(dialogLines);
    }
}
