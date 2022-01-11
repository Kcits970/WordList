package frame;

import java.awt.Font;

public class MyFonts {
    public static final Font CONSOLAS_16;
    public static final Font ARIAL_UNICODE_20;
    public static final Font ARIAL_UNICODE_18;
    public static final Font ARIAL_UNICODE_14;

    static {
        CONSOLAS_16 = new Font("Consolas", Font.PLAIN, 16);
        ARIAL_UNICODE_20 = new Font("Arial Unicode MS", Font.PLAIN, 20);
        ARIAL_UNICODE_18 = new Font("Arial Unicode MS", Font.PLAIN, 18);
        ARIAL_UNICODE_14 = new Font("Arial Unicode MS", Font.PLAIN, 14);
    }
}
