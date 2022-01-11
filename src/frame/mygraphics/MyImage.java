package frame.mygraphics;

import java.awt.*;

public class MyImage extends MyGraphicsObject {
    Image image;
    boolean border;

    public MyImage(Image image, boolean border) {
        this.image = image;
        this.border = border;

        if (image != null) {
            width = image.getWidth(null);
            height = image.getHeight(null);
        }
    }

    @Override
    public void drawAt(Graphics g, int x, int y) {
        g.drawImage(image, x, y, null);
        if (border)
            g.drawRect(x, y, width, height);
    }
}
