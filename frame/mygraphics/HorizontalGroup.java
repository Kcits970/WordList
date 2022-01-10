package frame.mygraphics;

import java.awt.Graphics;
import java.util.Arrays;

public class HorizontalGroup extends GraphicsGroup {
    public static final int ALIGN_TOP = 0;
    public static final int ALIGN_BOTTOM = 1;

    public HorizontalGroup(int alignment, MyGraphicsObject... objs) {
        super(alignment, objs);
        width = Arrays.stream(objs).mapToInt(obj -> obj.width).sum();
        height = Arrays.stream(objs).mapToInt(obj -> obj.height).max().getAsInt();
    }

    @Override
    public void drawAt(Graphics g, int x, int y) {
        for (MyGraphicsObject obj : group) {
            obj.drawAt(g, x, y + alignment * (height - obj.height));
            x += obj.width;
        }
    }
}
