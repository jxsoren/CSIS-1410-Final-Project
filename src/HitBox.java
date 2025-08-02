import java.awt.*;

public class HitBox extends Rectangle {
    private final int defaultX;
    private final int defaultY;

    public HitBox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.defaultX = x;
        this.defaultY = y;
        this.width = width;
        this.height = height;
    }

    public void reset() {
        this.x = defaultX;
        this.y = defaultY;
    }
}
