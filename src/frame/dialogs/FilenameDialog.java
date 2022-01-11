package frame.dialogs;

import frame.MyFrameTools;
import word.WordBase;

import javax.swing.*;
import java.awt.*;

public class FilenameDialog extends MyDialog {
    JTextField filenameField;
    JButton okButton;
    JButton cancelButton;
    JButton defaultButton;

    String filename;

    public FilenameDialog(Frame owner) {
        super(owner, "", true);
        build();
    }

    @Override
    public void createComponents() {
        filenameField = new JTextField(20);
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        defaultButton = new JButton("Default");
    }

    @Override
    public void addComponents() {
        Container c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

        c.add(MyFrameTools.addComponentWithEdgeSpacing(null, new JLabel("Filename:"), 0));
        c.add(Box.createVerticalStrut(5));
        c.add(filenameField);
        c.add(Box.createVerticalStrut(5));
        c.add(MyFrameTools.addHorizontally(null, true, okButton, cancelButton, defaultButton));

        setContentPane(MyFrameTools.addComponentWithEdgeSpacing(null, c, 5));
    }

    @Override
    public void bindActions() {
        okButton.addActionListener(e -> {
            filename = filenameField.getText().replaceAll(" ", "");
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());
        defaultButton.addActionListener(e -> filenameField.setText(WordBase.DEFAULT_FILE_LOCATION));
    }

    public String getSaveFilename() {
        setTitle("Save To");
        return getFilenameInput();
    }

    public String getLoadFilename() {
        setTitle("Load From");
        return getFilenameInput();
    }

    public String getFilenameInput() {
        filename = null;
        setVisible(true);
        return filename;
    }
}
