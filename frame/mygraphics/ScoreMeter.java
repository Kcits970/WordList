package frame.mygraphics;

import java.awt.*;

public class ScoreMeter extends MyGraphicsObject {
    double ratio;

    public ScoreMeter(int width, int height, int maxScore, int currentScore) {
        this.width = width;
        this.height = height;
        ratio = (maxScore == 0) ? 0 : (double) currentScore / maxScore;
    }

    @Override
    public void drawAt(Graphics g, int x, int y) {
        g.drawRect(x, y, width, height);
        if (ratio > 0) g.fillRect(x, y, (int) (width * ratio), height);
    }
}
