package frame.mygraphics;

import java.awt.Graphics;

public abstract class MyGraphicsObject {
    public static final int ANCHOR_LEFT = 1;
    public static final int ANCHOR_RIGHT = 2;
    public static final int ANCHOR_TOP = 3;
    public static final int ANCHOR_BOTTOM = 4;
    public static final int ANCHOR_CENTER = 5;
    public int height;
    public int width;

    public abstract void drawAt(Graphics g, int x, int y);

    public void drawAtAnchor(Graphics g, int x, int y, int anchor) {
        switch (anchor) {
            case ANCHOR_LEFT -> drawAt(g, x, y - height/2);
            case ANCHOR_RIGHT -> drawAt(g, x - width, y - height/2);
            case ANCHOR_TOP -> drawAt(g, x - width/2, y);
            case ANCHOR_BOTTOM -> drawAt(g, x - width/2, y - height);
            case ANCHOR_CENTER -> drawAt(g, x - width/2, y - height/2);
        }
    }
}
