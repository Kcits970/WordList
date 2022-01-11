package frame.dialogs;

import frame.mygraphics.*;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends MyDialog {
    AboutPanel aboutPanel;

    public AboutDialog(Frame owner) {
        super(owner, "About WordList", false);
        build();
    }

    @Override
    public void createComponents() {
        aboutPanel = new AboutPanel();
    }

    @Override
    public void addComponents() {
        getContentPane().add(aboutPanel);
    }

    class AboutPanel extends JPanel {
        private static final String KCITS970_LOCATION = "kcits970.png";
        Image kcits970;

        public AboutPanel() {
            kcits970 = new ImageIcon(KCITS970_LOCATION).getImage();
            setPreferredSize(new Dimension(600, 250));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            new HorizontalGroup(
                    HorizontalGroup.ALIGN_TOP,
                    new MyImage(kcits970, true),
                    new BlankSpace(20, 0),
                    new VerticalGroup(
                            VerticalGroup.ALIGN_LEFT,
                            new MyString("WordList", new Font("Consolas", Font.BOLD, 64), g),
                            new MyString("Version 1.0", new Font("Consolas", Font.PLAIN, 18), g),
                            new BlankSpace(0, 40),
                            new MyString("programmed by Taehyun Ahn(Kcits970)", new Font("Consolas", Font.ITALIC, 12), g),
                            new MyString("(January 10, 2022)", new Font("Consolas", Font.ITALIC, 12), g)
                    )
            ).drawAtAnchor(g, getWidth()/2, getHeight()/2, MyGraphicsObject.ANCHOR_CENTER);
        }
    }
}
