package frame.dialogs;

import frame.*;
import frame.mainframe.SortableListModel;
import frame.mygraphics.*;
import word.Word;
import util.MyTimer;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class QuizDialog extends MyDialog {
    List<Word> wordsToAsk;
    Word currentWord = Word.NULL_WORD;

    QuizScorePanel quizScorePanel;
    WordSelectionPanel wordSelectionPanel;
    UserGuessPanel userGuessPanel;
    ButtonPanel buttonPanel;

    boolean quizStartFlag = false;

    public QuizDialog(Frame owner) {
        super(owner, "Quiz Prompt", true);
        build();
    }

    @Override
    public void createComponents() {
        quizScorePanel = new QuizScorePanel();
        userGuessPanel = new UserGuessPanel();
        wordSelectionPanel = new WordSelectionPanel();
        buttonPanel = new ButtonPanel();
    }

    @Override
    public void addComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5,5,5,5);
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(quizScorePanel, constraints);

        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(wordSelectionPanel, constraints);

        constraints.gridx = 1;
        panel.add(userGuessPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        panel.add(buttonPanel, constraints);

        MyFrameTools.addComponentWithEdgeSpacing(this, panel, 5);
    }

    public void showQuizDialog(List<Word> words) {
        this.wordsToAsk = words;
        reset();
        setVisible(true);
    }

    public void start() {
        if (!quizStartFlag) {
            quizStartFlag = true;
            quizScorePanel.resetScore();
            setupNextWord();
        }
    }

    public void setupNextWord() {
        wordSelectionPanel.removeItem(currentWord);
        currentWord = wordSelectionPanel.getRandomItem();

        if (currentWord == null) {
            currentWord = Word.NULL_WORD;
            quizStartFlag = false;
        }

        userGuessPanel.refresh(currentWord);
    }

    public void checkUserGuess(String guess) {
        if (!quizStartFlag) return;

        if (CharSequence.compare(currentWord.getName(), guess) == 0) {
            quizScorePanel.updateScore(true);
            setupNextWord();
        } else {
            quizScorePanel.updateScore(false);
        }
    }

    public void reset() {
        quizStartFlag = false;

        currentWord = Word.NULL_WORD;
        wordSelectionPanel.setItems(wordsToAsk);
        quizScorePanel.resetScore();
        userGuessPanel.refresh(currentWord);
    }

    class QuizScorePanel extends JPanel {
        private static final int EDGE_OFFSET = 10;
        private static final int MAX_INCREMENT = 300;
        private static final int DEFAULT_INCREMENT = 100;
        private static final int MAX_INCREMENT_THRESHOLD = 3;
        private static final int DEFAULT_INCREMENT_THRESHOLD = 10;

        MyTimer timer;
        int maxScore, score, scoreIncrement;


        public QuizScorePanel() {
            timer = new MyTimer();
            setPreferredSize(new Dimension(0, 60));
        }

        public void resetScore() {
            maxScore = MAX_INCREMENT * QuizDialog.this.wordsToAsk.size();
            score = 0;
            scoreIncrement = 0;
            timer.mark();

            this.repaint();
        }

        public void setScoreIncrement(boolean correct) {
            if (!correct) {
                scoreIncrement = (score - DEFAULT_INCREMENT < 0) ? -score : -DEFAULT_INCREMENT;
                return;
            }

            double elapsedSecond = timer.getElapsedSecond();
            if (elapsedSecond < MAX_INCREMENT_THRESHOLD) scoreIncrement = MAX_INCREMENT;
            else if (elapsedSecond > DEFAULT_INCREMENT_THRESHOLD) scoreIncrement = DEFAULT_INCREMENT;
            else {
                double gradient = (double) (MAX_INCREMENT - DEFAULT_INCREMENT) / (MAX_INCREMENT_THRESHOLD - DEFAULT_INCREMENT_THRESHOLD);
                scoreIncrement = (int) (gradient * (elapsedSecond - MAX_INCREMENT_THRESHOLD) + MAX_INCREMENT);
            }
        }

        public void updateScore(boolean correct) {
            setScoreIncrement(correct);
            score += scoreIncrement;
            this.repaint();

            if (correct)
                timer.mark();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            VerticalGroup pointLabelGroup = new VerticalGroup(
                    VerticalGroup.ALIGN_LEFT,
                    new MyString(String.format("%04dpts", score), new Font("Consolas", Font.BOLD, 36), g2),
                    new MyString(String.format("(%+d)", scoreIncrement), new Font("Consolas", Font.ITALIC, 16), g2));

            VerticalGroup scoreGroup = new VerticalGroup(
                    VerticalGroup.ALIGN_RIGHT,
                    new ScoreMeter(this.getWidth() - pointLabelGroup.width - EDGE_OFFSET*3, 30, maxScore, score),
                    new BlankSpace(0, 5),
                    new MyString(String.format("(%d/%d)", score, maxScore), new Font("Consolas", Font.ITALIC, 12), g2));

            pointLabelGroup.drawAtAnchor(g, EDGE_OFFSET, this.getHeight()/2, MyGraphicsObject.ANCHOR_LEFT);
            scoreGroup.drawAtAnchor(g, this.getWidth() - EDGE_OFFSET, this.getHeight()/2, MyGraphicsObject.ANCHOR_RIGHT);
        }
    }

    class WordSelectionPanel extends JPanel implements MyContainable {
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
            scrollPane.setPreferredSize(new Dimension(200, 200));

            setLayout(new BorderLayout());
            add(scrollPane);
        }

        @Override
        public void bindActions() {
            selectionList.addListSelectionListener(QuizDialog.this.userGuessPanel);
        }

        @Override
        public void configureSettings() {
            selectionList.setFont(MyFonts.ARIAL_UNICODE_18);
        }

        public void setItems(List<Word> words) {
            items.setElements(words);
        }

        public void removeItem(Word word) {
            items.remove(word);
            selectionList.clearSelection();
        }

        public Word getRandomItem() {
            if (items.getSize() == 0)
                return null;

            int randomIndex = (int) (Math.random() * items.getSize());
            return items.get(randomIndex);
        }
    }

    class UserGuessPanel extends JPanel implements MyContainable, ListSelectionListener {
        JTextField inputField;
        JButton okButton;
        JLabel posLabel;
        JTextArea definitionArea;

        public UserGuessPanel() {
            build();
        }

        @Override
        public void createComponents() {
            inputField = new JTextField(20);
            okButton = new JButton("OK");
            posLabel = new JLabel(String.format("(%s)", currentWord.getPOS()));
            definitionArea = new JTextArea();
        }

        @Override
        public void addComponents() {
            setLayout(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();

            constraints.gridx = 0;
            constraints.gridy = 0;
            add(inputField, constraints);

            constraints.gridx = 1;
            add(Box.createHorizontalStrut(5), constraints);

            constraints.gridx = 2;
            add(okButton, constraints);

            constraints.gridy = 1;
            add(Box.createVerticalStrut(5), constraints);

            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 3;
            add(posLabel, constraints);

            constraints.gridy = 3;
            constraints.weighty = 1.0;
            constraints.fill = GridBagConstraints.BOTH;
            JScrollPane scrollPane = new JScrollPane(definitionArea
                    , ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
                    , ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            add(scrollPane, constraints);
        }

        @Override
        public void bindActions() {
            inputField.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == KeyEvent.VK_ENTER) checkAndClearInput();
                }
            });

            okButton.addActionListener(e -> checkAndClearInput());
        }

        @Override
        public void configureSettings() {
            inputField.setFont(MyFonts.ARIAL_UNICODE_14);

            posLabel.setFont(new Font("Consolas", Font.ITALIC, 12));
            posLabel.setHorizontalAlignment(SwingConstants.CENTER);

            definitionArea.setFont(MyFonts.ARIAL_UNICODE_14);
            definitionArea.setEditable(false);
            definitionArea.setLineWrap(true);
            definitionArea.setWrapStyleWord(true);
        }

        public void refresh(Word word) {
            posLabel.setText(word.getPOS());
            definitionArea.setText(word.getDefinition());
        }

        public void checkAndClearInput() {
            QuizDialog.this.checkUserGuess(inputField.getText());
            inputField.setText("");
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                Word selectedWord = (Word) ((JList) e.getSource()).getSelectedValue();
                if (selectedWord == null)
                    inputField.setText("");
                else
                    inputField.setText(selectedWord.getName());
                inputField.requestFocus();
            }
        }
    }

    class ButtonPanel extends JPanel implements MyContainable {
        JButton startButton;
        JButton resetButton;
        JButton quitButton;

        public ButtonPanel() {
            build();
        }

        @Override
        public void createComponents() {
            startButton = new JButton("Start");
            resetButton = new JButton("Reset");
            quitButton = new JButton("Quit");
        }

        @Override
        public void addComponents() {
            MyFrameTools.addHorizontally(this, true, startButton, resetButton, quitButton);
        }

        @Override
        public void bindActions() {
            startButton.addActionListener(e -> QuizDialog.this.start());
            resetButton.addActionListener(e -> QuizDialog.this.reset());
            quitButton.addActionListener(e -> QuizDialog.this.dispose());
        }
    }
}
