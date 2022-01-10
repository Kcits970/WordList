import frame.mainframe.MyFrame;
import word.WordBase;

public class Launcher {
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(() -> new MyFrame(new WordBase()));
	}
}