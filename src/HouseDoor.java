import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class HouseDoor extends GameObject implements Speakable {
    private final Dialog dialog;
    private final CandyType rewardCandyType;
    private final Trick trick;
    private boolean canGiveOutCandy;
    private boolean riddleAttempted;

    public HouseDoor(CandyType rewardCandyType, String imagePath, int trickNumber) {
        setCollision(true);
        setInteractable(true);
        setConsumable(false);
        setHitBox(new HitBox(0, 0, 80, 80));
        this.trick = TrickFactory.createTrick(trickNumber);
        this.rewardCandyType = rewardCandyType;
        this.canGiveOutCandy = true;
        this.dialog = new Dialog();
        dialog.addDialogLines(List.of(new Question(trick.getQuestion(), trick.getAnswers())));

        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            setImage(image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void speak(UI ui) {
        //        ui.setCurrentDialogLine(dialog.currentLine());
        //
        //        if (!dialog.linesCompleted()) {
        //            dialog.nextLine();
        //        }
    }

    @Override
    public Dialog getDialog() {
        return dialog;
    }

    public void giveCandy(CandyBucket candyBucket) {
        if (canGiveOutCandy) {
            candyBucket.addCandy(rewardCandyType);
            canGiveOutCandy = false;
        }
    }

    public void alreadyVisitedResponse() {
        if (riddleAttempted) {
            dialog.clearDialogLines();
            dialog.addDialogLines(List.of(new Statement("Sorry, kid. I don't have any tricks or treats.")));
        }
    }

    public boolean isRiddleAttempted() {
        return riddleAttempted;
    }

    public void setTrickResponse() {
        if (!riddleAttempted) {
            dialog.clearDialogLines();
            dialog.addDialogLines(List.of(trick.getTrickMessage()));
            riddleAttempted = true;
        }
    }

    public void takeCandy(CandyType candyType, CandyBucket candyBucket) {
        candyBucket.removeCandy(candyType);
    }

    public Trick getTrick() {
        return trick;
    }
}
