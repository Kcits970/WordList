package frame.mygraphics;

import java.awt.*;

public class BlankSpace extends MyGraphicsObject {
    public BlankSpace(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawAt(Graphics g, int x, int y) {
        return;
    }
}
