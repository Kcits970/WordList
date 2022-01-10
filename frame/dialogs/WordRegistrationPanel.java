package frame.dialogs;

import frame.MyContainable;
import frame.MyFonts;
import frame.MyFrameTools;
import word.Word;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

public class WordRegistrationPanel extends JPanel implements MyContainable, Iterable<String> {
    JTextField wordField;
    ButtonGroup radioButtonGroup;
    JRadioButton[] radioButtonArray;
    JTextArea definitionArea;

    public WordRegistrationPanel() {
        build();
    }

    @Override
    public void createComponents() {
        wordField = new JTextField(20);
        radioButtonGroup = new ButtonGroup();
        radioButtonArray = new JRadioButton[] {
                new JRadioButton("noun"),
                new JRadioButton("verb"),
                new JRadioButton("adjective"),
                new JRadioButton("adverb")
        };
        definitionArea = new JTextArea();
    }

    @Override
    public void addComponents() {
        MyFrameTools.addVertically(this, getWordPanel(), getRadioButtonPanel(), getDefinitionPanel());
    }

    @Override
    public void configureSettings() {
        wordField.setFont(MyFonts.ARIAL_UNICODE_14);
        definitionArea.setFont(MyFonts.ARIAL_UNICODE_14);
        definitionArea.setLineWrap(true);
        definitionArea.setWrapStyleWord(true);
    }

    private JPanel getWordPanel() {
        JPanel panel = new JPanel();
        MyFrameTools.addComponentWithEdgeSpacing(panel, wordField, 5);
        panel.setBorder(MyFrameTools.createMyTitledBorder("Word"));

        return panel;
    }

    private JPanel getRadioButtonPanel() {
        Arrays.stream(radioButtonArray).forEach(button -> radioButtonGroup.add(button));

        JPanel panel = new JPanel();
        MyFrameTools.addHorizontally(panel, false, radioButtonArray);
        panel.setBorder(MyFrameTools.createMyTitledBorder("Part of Speech"));

        return panel;
    }

    private JPanel getDefinitionPanel() {
        JScrollPane scrollPane = new JScrollPane(definitionArea
                , ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
                , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(0, 150));

        JPanel panel = new JPanel();
        MyFrameTools.addComponentWithEdgeSpacing(panel, scrollPane, 5);
        panel.setBorder(MyFrameTools.createMyTitledBorder("Definition"));

        return panel;
    }

    public void setPOSSelection(String s) {
        for (JRadioButton button : radioButtonArray)
            if (button.getText().equals(s)) {
                button.setSelected(true);
                return;
            }
    }

    public String getPOSSelection() {
        for (JRadioButton button : radioButtonArray)
            if (button.isSelected())
                return button.getText();

        return "unknown";
    }

    public void setFields(Word w) {
        wordField.setText(w.getName());
        setPOSSelection(w.getPOS());
        definitionArea.setText(w.getDefinition());
    }

    public void resetFields() {
        wordField.setText("");
        definitionArea.setText("");
        radioButtonGroup.clearSelection();
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.asList(wordField.getText(), getPOSSelection(), definitionArea.getText()).iterator();
    }
}
