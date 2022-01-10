package frame.mygraphics;

import java.awt.Graphics;
import java.util.Arrays;

public class VerticalGroup extends GraphicsGroup {
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_RIGHT = 1;

    public VerticalGroup(int alignment, MyGraphicsObject... objs) {
        super(alignment, objs);
        width = Arrays.stream(objs).mapToInt(obj -> obj.width).max().getAsInt();
        height = Arrays.stream(objs).mapToInt(obj -> obj.height).sum();
    }

    @Override
    public void drawAt(Graphics g, int x, int y) {
        for (MyGraphicsObject obj : group) {
            obj.drawAt(g, x + alignment * (width - obj.width), y);
            y += obj.height;
        }
    }
}
