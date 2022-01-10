package frame.mygraphics;

import java.awt.*;

public class MyString extends MyGraphicsObject {
    String string;
    Font font;

    public MyString(String s, Font f, Graphics g) {
        FontMetrics metrics = g.getFontMetrics(f);

        font = f;
        string = s; //MyFrameTools.truncate(s, f, g, STRING_MAX_LENGTH);
        width = (int) metrics.getStringBounds(s, g).getWidth();
        height = metrics.getAscent() + metrics.getDescent();
    }

    @Override
    public void drawAt(Graphics g, int x, int y) {
        g.setFont(font);
        g.drawString(string, x, y + g.getFontMetrics().getAscent());
    }
}
