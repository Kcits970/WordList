package frame.dialogs;

import frame.MyFrameTools;
import word.Word;

import javax.swing.*;
import java.awt.*;

public class RegistrationDialog extends MyDialog {
    WordRegistrationPanel wordRegistrationPanel;
    JButton okButton;
    JButton cancelButton;
    JButton clearButton;

    Word wordInstance;

    public RegistrationDialog(Frame owner) {
        super(owner, "", true);
        build();
    }

    @Override
    public void createComponents() {
        wordRegistrationPanel = new WordRegistrationPanel();
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        clearButton = new JButton("Clear");
    }

    @Override
    public void addComponents() {
        MyFrameTools.addVertically(
                getContentPane(),
                wordRegistrationPanel,
                MyFrameTools.addHorizontally(null, true, okButton, cancelButton, clearButton)
        );

        setContentPane(MyFrameTools.addComponentWithEdgeSpacing(null, getContentPane(), 10));
    }

    @Override
    public void bindActions() {
        okButton.addActionListener(e -> {
            wordInstance = new Word(wordRegistrationPanel.iterator());
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());
        clearButton.addActionListener(e -> wordRegistrationPanel.resetFields());
    }

    public Word showAddDialog() {
        setTitle("Add");
        wordRegistrationPanel.resetFields();
        return getWordInstance();
    }

    public Word showEditDialog(Word originalWord) {
        setTitle("Edit");
        wordRegistrationPanel.setFields(originalWord);
        return getWordInstance();
    }

    public Word getWordInstance() {
        wordInstance = null;
        setVisible(true);
        return wordInstance;
    }
}
