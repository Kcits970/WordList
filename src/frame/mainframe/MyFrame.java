package frame.mainframe;

import frame.*;
import frame.dialogs.*;
import util.MyEvent;
import util.MyObserver;
import util.WordBaseChangedEvent;
import word.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame implements MyContainable {
	WordBase wordBase;

	ButtonPanel buttonPanel;
	WordSelectionPanel wordSelectionPanel;
	WordDescriptionPanel wordDescriptionPanel;

	FilenameDialog filenameDialog;
	RegistrationDialog registrationDialog;
	QuizDialog quizDialog;
	AboutDialog aboutDialog;
	
	public MyFrame(WordBase base) {
		wordBase = base;
		build();
	}

	@Override
	public void createComponents() {
		buttonPanel = new ButtonPanel();
		wordDescriptionPanel = new WordDescriptionPanel();
		wordSelectionPanel = new WordSelectionPanel();

		filenameDialog = new FilenameDialog(this);
		registrationDialog = new RegistrationDialog(this);
		quizDialog = new QuizDialog(this);
		aboutDialog = new AboutDialog(this);
	}

	@Override
	public void addComponents() {
		Container c = getContentPane();
		c.setLayout(new GridBagLayout());

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(5,5,5,5);
		constraints.fill = GridBagConstraints.BOTH;

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		c.add(wordSelectionPanel, constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		c.add(wordDescriptionPanel, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.CENTER;
		c.add(buttonPanel, constraints);

		setContentPane(MyFrameTools.addComponentWithEdgeSpacing(null, c, 5));
	}

	@Override
	public void configureSettings() {
		wordBase.addObserver(wordSelectionPanel);

		setJMenuBar(new MyMenuBar());
		setTitle("WordList");
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	class MyMenuBar extends JMenuBar implements MyContainable {
		JMenuItem openMenuItem;
		JMenuItem saveMenuItem;
		JMenuItem exitMenuItem;
		JMenuItem aboutMenuItem;

		public MyMenuBar() {
			build();
		}

		@Override
		public void createComponents() {
			openMenuItem = new JMenuItem("Open...");
			saveMenuItem = new JMenuItem("Save As...");
			exitMenuItem = new JMenuItem("Exit");
			aboutMenuItem = new JMenuItem("About WordList");
		}

		@Override
		public void addComponents() {
			JMenu fileMenu = new JMenu("File");
			fileMenu.add(openMenuItem);
			fileMenu.add(saveMenuItem);
			fileMenu.addSeparator();
			fileMenu.add(exitMenuItem);

			JMenu aboutMenu = new JMenu("About");
			aboutMenu.add(aboutMenuItem);

			add(fileMenu);
			add(aboutMenu);
		}

		@Override
		public void bindActions() {
			openMenuItem.addActionListener(e -> {
				String location = filenameDialog.getLoadFilename();
				if (location != null)
					if (!wordBase.loadFrom(location))
						JOptionPane.showMessageDialog(
								MyFrame.this,
								String.format("Failed to load from '%s'", location),
								"Error",
								JOptionPane.ERROR_MESSAGE);
			});

			saveMenuItem.addActionListener(e -> {
				String location = filenameDialog.getSaveFilename();
				if (location != null)
					if (!wordBase.saveTo(location))
						JOptionPane.showMessageDialog(
								MyFrame.this,
								String.format("Failed to save to '%s'", location),
								"Error",
								JOptionPane.ERROR_MESSAGE);
			});

			exitMenuItem.addActionListener(e -> System.exit(0));
			aboutMenuItem.addActionListener(e -> aboutDialog.setVisible(true));
		}

		@Override
		public void configureSettings() {
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		}
	}

	class WordSelectionPanel extends JPanel implements MyContainable, MyObserver {
		SortableListModel<Word> items;
		JList<Word> selectionList;

		public WordSelectionPanel() {
			build();
		}

		@Override
		public void createComponents() {
			items = new SortableListModel<>();
			selectionList = new JList<>(items);
		}

		@Override
		public void addComponents() {
			JScrollPane scrollPane = new JScrollPane(selectionList
					, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
					, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			scrollPane.setPreferredSize(new Dimension(200, 300));

			setLayout(new BorderLayout());
			MyFrameTools.addComponentWithEdgeSpacing(this, scrollPane, 5);
		}

		@Override
		public void bindActions() {
			selectionList.addListSelectionListener(wordDescriptionPanel);
			selectionList.addListSelectionListener(buttonPanel);
		}

		@Override
		public void configureSettings() {
			selectionList.setFont(MyFonts.ARIAL_UNICODE_18);
			setBorder(MyFrameTools.createMyTitledBorder("Word Selection"));
		}

		@Override
		public void update(MyEvent e) {
			WordBaseChangedEvent event = (WordBaseChangedEvent) e;

			switch (event.getActionType()) {
				case WordBaseChangedEvent.LOAD_ACTION -> {
					selectionList.clearSelection();
					items.setElements(event.getSource().getWords());
					setSelectedWord(event.getModifiedWord());
				}

				case WordBaseChangedEvent.ADD_ACTION -> {
					items.add(event.getModifiedWord());
					setSelectedWord(event.getModifiedWord());
				}

				case WordBaseChangedEvent.EDIT_ACTION -> {
					items.sort();
					setSelectedWord(event.getModifiedWord());
				}

				case WordBaseChangedEvent.REMOVE_ACTION -> {
					Word removedWord = event.getModifiedWord();
					int removeIndex = items.indexOf(removedWord);
					items.remove(removedWord);

					if (removeIndex == items.getSize())
						setSelectedWord(items.get(removeIndex - 1));
				}
			}

			wordDescriptionPanel.changeDescription(getSelectedWord());
		}

		public void setSelectedWord(Word w) {
			selectionList.setSelectedValue(w, true);
		}

		public Word getSelectedWord() {
			return selectionList.getSelectedValue();
		}
	}

	class ButtonPanel extends JPanel implements MyContainable, ListSelectionListener {
		JButton addButton;
		JButton editButton;
		JButton deleteButton;
		JButton quizButton;

		public ButtonPanel() {
			build();
		}

		@Override
		public void createComponents() {
			addButton = new JButton("Add");
			editButton = new JButton("Edit");
			deleteButton = new JButton("Delete");
			quizButton = new JButton("Quiz");
		}

		@Override
		public void addComponents() {
			MyFrameTools.addHorizontally(this, true, addButton, editButton, deleteButton, quizButton);
		}

		@Override
		public void bindActions() {
			addButton.addActionListener(e -> {
				Word newWord = registrationDialog.showAddDialog();
				if (newWord == null) return;

				if (!wordBase.add(newWord, true))
					JOptionPane.showMessageDialog(
							MyFrame.this,
							"Failed to add new word.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
			});

			editButton.addActionListener(e -> {
				Word originalWord = wordSelectionPanel.getSelectedWord();
				Word modifiedWord = registrationDialog.showEditDialog(originalWord);
				if (modifiedWord == null) return;

				if (!wordBase.edit(originalWord, modifiedWord))
					JOptionPane.showMessageDialog(
							MyFrame.this,
							String.format("Failed to edit '%s'", originalWord),
							"Error",
							JOptionPane.ERROR_MESSAGE);
			});

			deleteButton.addActionListener(e -> {
				Word selectedWord = wordSelectionPanel.getSelectedWord();
				int confirmation = JOptionPane.showConfirmDialog(
						MyFrame.this,
						String.format("Delete '%s'?", selectedWord),
						"Item Deletion",
						JOptionPane.YES_NO_OPTION);

				if (confirmation == JOptionPane.YES_OPTION)
					wordBase.remove(selectedWord);
			});

			quizButton.addActionListener(e -> quizDialog.showQuizDialog(wordBase.getNRandomWords(15)));
		}

		@Override
		public void configureSettings() {
			enableDependantButtons(false);
		}

		public void enableDependantButtons(boolean enable) {
			/*
			editButton and deleteButton both depend on whether the list contains a valid selection or not.
			I define these buttons as 'dependant buttons'.
			*/

			editButton.setEnabled(enable);
			deleteButton.setEnabled(enable);
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				if (((JList) e.getSource()).getSelectedValue() == null)
					enableDependantButtons(false);
				else
					enableDependantButtons(true);
			}
		}
	}
}