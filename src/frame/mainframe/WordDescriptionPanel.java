package frame.mainframe;

import frame.MyContainable;
import frame.MyFonts;
import frame.MyFrameTools;
import frame.mygraphics.HorizontalGroup;
import frame.mygraphics.MyString;
import frame.mygraphics.MyGraphicsObject;
import word.Word;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class WordDescriptionPanel extends JPanel implements MyContainable, ListSelectionListener {
    Word currentWord = Word.NULL_WORD;
    JPanel descriptionTitle;
    JTextArea definitionArea;

    public WordDescriptionPanel() {
        build();
    }

    @Override
    public void createComponents() {
        descriptionTitle = new DescriptionTitle();
        definitionArea = new JTextArea();
    }

    @Override
    public void addComponents() {
        JScrollPane scrollPane = new JScrollPane(definitionArea
                , ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
                , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(400, 0));

        MyFrameTools.addComponentWithEdgeSpacing(this, MyFrameTools.addVertically(null, descriptionTitle, scrollPane), 5);
    }

    @Override
    public void configureSettings() {
        definitionArea.setFont(MyFonts.ARIAL_UNICODE_14);
        definitionArea.setEditable(false);
        definitionArea.setLineWrap(true);
        definitionArea.setWrapStyleWord(true);
        setBorder(MyFrameTools.createMyTitledBorder("Description"));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            changeDescription((Word) ((JList) e.getSource()).getSelectedValue());
        }
    }

    public void changeDescription(Word w) {
        currentWord = (w == null) ? Word.NULL_WORD : w;
        descriptionTitle.repaint();
        definitionArea.setText(currentWord.getDefinition());
    }

    class DescriptionTitle extends JPanel {
        public DescriptionTitle() {
            setPreferredSize(new Dimension(0, 50));
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            new HorizontalGroup(
                    HorizontalGroup.ALIGN_BOTTOM,
                    new MyString(currentWord.getName(), new Font("Consolas", Font.BOLD, 36), g),
                    new MyString(currentWord.getPOS(), new Font("Consolas", Font.ITALIC, 12), g)
            ).drawAtAnchor(g, 10, this.getHeight()/2, MyGraphicsObject.ANCHOR_LEFT);
        }
    }
}
