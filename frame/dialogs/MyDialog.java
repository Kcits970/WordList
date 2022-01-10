package frame.dialogs;

import frame.MyContainable;

import javax.swing.JDialog;
import java.awt.Frame;

public abstract class MyDialog extends JDialog implements MyContainable {
    public MyDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
    }

    @Override
    public void configureSettings() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(getOwner());
    }
}
