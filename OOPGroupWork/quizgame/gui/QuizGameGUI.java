package OOPGroupWork.quizgame.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import javax.swing.border.LineBorder;
import OOPGroupWork.quizgame.engine.AnswerResult;
import OOPGroupWork.quizgame.engine.QuizSession;
import OOPGroupWork.quizgame.exceptions.EmptyAnswerException;
import OOPGroupWork.quizgame.exceptions.InvalidPlayerNameException;
import OOPGroupWork.quizgame.exceptions.QuizFinishedException;
import OOPGroupWork.quizgame.factory.QuizFactory;
import OOPGroupWork.quizgame.model.DifficultyLevel;
import OOPGroupWork.quizgame.model.Player;
import OOPGroupWork.quizgame.model.Quiz;
import OOPGroupWork.quizgame.questions.AnswerType;
import OOPGroupWork.quizgame.questions.Question;
/**
 * Main graphical user interface for the OOP Quiz Game.
 * <p>
 * This class builds and controls the Swing-based quiz window. It manages
 * the welcome screen, quiz screen, answer input, progress display, score
 * updates, and final result dialog.
 * </p>
 */
public class QuizGameGUI extends JFrame {
    private static final Color BACKGROUND_TOP = new Color(28, 43, 91);
    private static final Color BACKGROUND_BOTTOM = new Color(16, 126, 137);
    private static final Color PANEL_COLOR = new Color(250, 252, 255);
    private static final Color SOFT_PANEL_COLOR = new Color(236, 246, 255);
    private static final Color PRIMARY_COLOR = new Color(29, 95, 216);
    private static final Color ACCENT_COLOR = new Color(247, 183, 49);
    private static final Color TEXT_COLOR = new Color(31, 41, 55);
    private static final Color MUTED_TEXT_COLOR = new Color(92, 107, 129);

    private final QuizFactory factory;
    private QuizSession session;

    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final JLabel questionNumberLabel;
    private final JLabel scoreLabel;
    private final JLabel levelLabel;
    private final JProgressBar progressBar;
    private final JTextArea questionArea;
    private final JPanel dynamicAnswerPanel;
    private final JButton submitButton;

    private JTextField blankField;
    private ButtonGroup optionGroup;

    /**
     * Creates the quiz game GUI and initializes all interface components.
     *
     * @param factory the quiz factory used to create quizzes
     */
    public QuizGameGUI(QuizFactory factory) {
        this.factory = factory;

        setTitle("OOP Quiz Game");
        setSize(900, 600);
        setMinimumSize(new Dimension(760, 520));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new GradientPanel(cardLayout);
        cardPanel.setBorder(new EmptyBorder(24, 24, 24, 24));

        JPanel welcomePanel = createWelcomePanel();
        JPanel quizPanel = createSurfacePanel(new BorderLayout(16, 16));
        quizPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 226, 245), 1, true),
                new EmptyBorder(22, 22, 22, 22)));

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 0, 8));
        topPanel.setOpaque(false);
        questionNumberLabel = new JLabel("Question 1", SwingConstants.LEFT);
        questionNumberLabel.setForeground(TEXT_COLOR);
        questionNumberLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        scoreLabel = new JLabel("Score: 0", SwingConstants.LEFT);
        scoreLabel.setForeground(PRIMARY_COLOR);
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        levelLabel = new JLabel("Level: Easy", SwingConstants.LEFT);
        levelLabel.setForeground(MUTED_TEXT_COLOR);
        levelLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setForeground(ACCENT_COLOR);
        progressBar.setBackground(new Color(220, 231, 245));
        progressBar.setBorderPainted(false);
        progressBar.setFont(new Font("SansSerif", Font.BOLD, 12));

        JPanel statusPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        statusPanel.setOpaque(false);
        statusPanel.add(scoreLabel);
        statusPanel.add(levelLabel);
        topPanel.add(questionNumberLabel);
        topPanel.add(statusPanel);

        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("SansSerif", Font.PLAIN, 18));
        questionArea.setForeground(TEXT_COLOR);
        questionArea.setBackground(SOFT_PANEL_COLOR);
        questionArea.setBorder(new EmptyBorder(18, 18, 18, 18));

        dynamicAnswerPanel = new JPanel();
        dynamicAnswerPanel.setOpaque(false);
        dynamicAnswerPanel.setLayout(new BoxLayout(dynamicAnswerPanel, BoxLayout.Y_AXIS));

        submitButton = new ColorButton("Submit Answer", PRIMARY_COLOR, Color.WHITE);
        styleButton(submitButton, PRIMARY_COLOR, Color.WHITE);
        submitButton.addActionListener(e -> submitCurrentAnswer());

        JPanel answerSection = new JPanel(new BorderLayout(0, 10));
        answerSection.setOpaque(false);
        answerSection.setPreferredSize(new Dimension(100, 145));
        answerSection.add(dynamicAnswerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(submitButton);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 10));
        bottomPanel.setOpaque(false);
        bottomPanel.add(progressBar, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        JScrollPane questionScrollPane = new JScrollPane(questionArea);
        questionScrollPane.setBorder(new LineBorder(new Color(214, 229, 247), 1, true));
        questionScrollPane.getViewport().setBackground(SOFT_PANEL_COLOR);

        JPanel questionAndAnswerPanel = new JPanel(new BorderLayout(0, 14));
        questionAndAnswerPanel.setOpaque(false);
        questionAndAnswerPanel.add(questionScrollPane, BorderLayout.CENTER);
        questionAndAnswerPanel.add(new JScrollPane(answerSection), BorderLayout.SOUTH);

        quizPanel.add(topPanel, BorderLayout.NORTH);
        quizPanel.add(questionAndAnswerPanel, BorderLayout.CENTER);
        quizPanel.add(bottomPanel, BorderLayout.SOUTH);

        cardPanel.add(welcomePanel, "WELCOME");
        cardPanel.add(quizPanel, "QUIZ");
        add(cardPanel);
    }

    /**
     * Creates the welcome screen where the player enters their name
     * and selects a difficulty level.
     *
     * @return the welcome panel
     */
    private JPanel createWelcomePanel() {
        JPanel panel = createSurfacePanel(new BorderLayout(18, 18));
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 226, 245), 1, true),
                new EmptyBorder(28, 30, 28, 30)));

        JLabel titleLabel = new JLabel("Object-Oriented Programming Quiz", SwingConstants.CENTER);
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));

        JTextArea introArea = new JTextArea(
                "Welcome to the Java OOP quiz game.\n"
                        + "This project demonstrates inheritance, abstraction, encapsulation, and polymorphism.\n"
                        + "Enter your name to begin.");
        introArea.setEditable(false);
        introArea.setOpaque(false);
        introArea.setLineWrap(true);
        introArea.setWrapStyleWord(true);
        introArea.setForeground(MUTED_TEXT_COLOR);
        introArea.setFont(new Font("SansSerif", Font.PLAIN, 17));

        JTextField nameField = new JTextField();
        styleTextField(nameField);
        JComboBox<DifficultyLevel> difficultyBox = new JComboBox<>(factory.getAvailableDifficulties());
        difficultyBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
        difficultyBox.setBackground(Color.WHITE);
        difficultyBox.setForeground(TEXT_COLOR);
        JButton startButton = new ColorButton("Start Quiz", ACCENT_COLOR, new Color(34, 46, 67));
        styleButton(startButton, ACCENT_COLOR, new Color(34, 46, 67));
        startButton.addActionListener(e -> startQuiz(nameField.getText(),
                (DifficultyLevel) difficultyBox.getSelectedItem()));

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 0, 8));
        formPanel.setOpaque(false);
        formPanel.add(createFieldLabel("Player name:"));
        formPanel.add(nameField);
        formPanel.add(createFieldLabel("Difficulty level:"));
        formPanel.add(difficultyBox);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setOpaque(false);
        actionPanel.add(startButton);

        JPanel lowerPanel = new JPanel(new BorderLayout(0, 10));
        lowerPanel.setOpaque(false);
        lowerPanel.add(formPanel, BorderLayout.CENTER);
        lowerPanel.add(actionPanel, BorderLayout.SOUTH);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(introArea, BorderLayout.CENTER);
        panel.add(lowerPanel, BorderLayout.SOUTH);
        return panel;
    }
    /**
     * Starts a new quiz using the provided player name and difficulty.
     * <p>
     * If the player name is empty, a default name is used.
     * </p>
     *
     * @param name the player's entered name
     * @param difficulty the selected difficulty level
     */
    private void startQuiz(String name, DifficultyLevel difficulty) {
        String finalName = name.trim();
        if (finalName.isEmpty()) {
            JOptionPane.showMessageDialog(this, new InvalidPlayerNameException().getMessage()
                    + "\nNo name was entered, so the name has been assigned as \"Player\".");
            finalName = "Player";
        }

        Quiz quiz = factory.createOopQuiz(difficulty);
        session = new QuizSession(quiz, new Player(finalName));
        scoreLabel.setText("Score: 0/" + quiz.getTotalPossibleScore());
        levelLabel.setText("Level: " + quiz.getDifficultyLabel());
        progressBar.setMaximum(quiz.getQuestionCount());
        progressBar.setValue(0);
        progressBar.setString(session.getProgressSummary());
        cardLayout.show(cardPanel, "QUIZ");
        loadQuestion();
    }

    /**
     * Loads and displays the current question.
     * <p>
     * If the quiz is finished, the final result dialog is shown.
     * </p>
     */
    private void loadQuestion() {
        if (session.isFinished()) {
            showFinalResult();
            return;
        }

        Quiz quiz = session.getQuiz();
        Question question = session.getCurrentQuestion();
        questionNumberLabel.setText("Question " + (session.getCurrentQuestionIndex() + 1)
                + " of " + quiz.getQuestionCount()
                + "  |  " + question.getDifficultyLabel()
                + "  |  " + question.getPoints() + " point(s)");
        questionArea.setText(question.getPrompt());

        dynamicAnswerPanel.removeAll();
        blankField = null;
        optionGroup = null;

        if (question.getAnswerType() == AnswerType.CHOICE) {
            loadChoiceQuestion(question);
        } else {
            loadFillInBlankQuestion();
        }

        dynamicAnswerPanel.revalidate();
        dynamicAnswerPanel.repaint();
    }

    /**
     * Displays answer choices for a multiple-choice or true/false question.
     *
     * @param question the question whose choices should be displayed
     */
    private void loadChoiceQuestion(Question question) {
        String[] choices = question.getChoices();
        optionGroup = new ButtonGroup();

        for (int i = 0; i < choices.length; i++) {
            char optionLetter = (char) ('A' + i);
            JRadioButton button = new JRadioButton(optionLetter + ". " + choices[i]);
            button.setActionCommand(String.valueOf(optionLetter));
            button.setOpaque(false);
            button.setForeground(TEXT_COLOR);
            button.setFont(new Font("SansSerif", Font.PLAIN, 16));
            button.setBorder(new EmptyBorder(6, 4, 6, 4));
            optionGroup.add(button);
            dynamicAnswerPanel.add(button);
        }
    }

    /**
     * Displays a text field for fill-in-the-blank or code-completion questions.
     */
    private void loadFillInBlankQuestion() {
        blankField = new JTextField();
        blankField.setColumns(25);
        styleTextField(blankField);
        dynamicAnswerPanel.add(createFieldLabel("Type your answer below:"));
        dynamicAnswerPanel.add(blankField);
    }

    /**
     * Collects and submits the current answer.
     * <p>
     * The method displays feedback, updates the score and progress bar,
     * and then loads the next question.
     * </p>
     */
    private void submitCurrentAnswer() {
        AnswerResult result;
        try {
            result = session.submitAnswer(collectAnswer());
        } catch (EmptyAnswerException | QuizFinishedException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage());
            return;
        }

        if (result.isCorrect()) {
            scoreLabel.setText("Score: " + session.getScoreSummary());
            JOptionPane.showMessageDialog(this, "Correct! +" + result.getPointsEarned() + " point(s)");
        } else {
            JOptionPane.showMessageDialog(this, "Wrong answer.\nCorrect answer: "
                    + result.getQuestion().getCorrectAnswer());
        }

        progressBar.setValue(session.getAnsweredQuestionCount());
        progressBar.setString(session.getProgressSummary());
        loadQuestion();
    }

    /**
     * Collects the answer entered or selected by the player.
     *
     * @return the selected option letter, typed answer, or {@code null}
     * if no answer was provided
     */
    private String collectAnswer() {
        if (blankField != null) {
            return blankField.getText();
        }
        if (optionGroup != null && optionGroup.getSelection() != null) {
            return optionGroup.getSelection().getActionCommand();
        }
        return null;
    }

    /**
     * Shows the final score dialog and closes the quiz window.
     */
    private void showFinalResult() {
        JOptionPane.showMessageDialog(
                this,
                "Quiz finished!\n" + session.getPlayer().getName() + ", your final score is "
                        + session.getScoreSummary() + "\nLevel: " + session.getQuiz().getDifficultyLabel(),
                "Final Score",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

    /**
     * Launches the quiz game GUI if the environment supports graphics.
     *
     * @return {@code true} if GUI launch was attempted; {@code false}
     * if the environment is headless
     */
    public static boolean launch() {
        if (GraphicsEnvironment.isHeadless()) {
            return false;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.put("OptionPane.messageFont", new Font("SansSerif", Font.PLAIN, 14));
                UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.BOLD, 13));
                QuizGameGUI gui = new QuizGameGUI(new QuizFactory());
                gui.setVisible(true);
            } catch (HeadlessException exception) {
                System.out.println("GUI mode is not available in this environment.");
            }
        });
        return true;
    }

    /**
     * Creates a panel with the standard surface background color.
     *
     * @param layout the layout manager for the panel
     * @return the styled surface panel
     */
    private JPanel createSurfacePanel(BorderLayout layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(PANEL_COLOR);
        return panel;
    }

    /**
     * Creates a styled form label.
     *
     * @param text the label text
     * @return the styled label
     */
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        return label;
    }

    /**
     * Applies standard styling to a text field.
     *
     * @param field the text field to style
     */
    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setForeground(TEXT_COLOR);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(190, 210, 235), 1, true),
                new EmptyBorder(8, 10, 8, 10)));
    }

    /**
     * Applies standard styling to a button.
     *
     * @param button the button to style
     * @param background the button background color
     * @param foreground the button text color
     */
    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setMargin(new Insets(10, 18, 10, 18));
    }

    /**
     * Custom rounded button with a colored background.
     */
    private static class ColorButton extends JButton {
        private final Color backgroundColor;
        /**
         * Creates a rounded color button.
         *
         * @param text the button text
         * @param backgroundColor the button background color
         * @param foregroundColor the button text color
         */
        ColorButton(String text, Color backgroundColor, Color foregroundColor) {
            super(text);
            this.backgroundColor = backgroundColor;
            setForeground(foregroundColor);
        }

        /**
         * Paints the rounded button background.
         *
         * @param graphics the graphics context
         */
        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(backgroundColor);
            graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
            graphics2D.dispose();
            super.paintComponent(graphics);
        }
    }

    /**
     * Custom panel that paints a gradient background.
     */
    private static class GradientPanel extends JPanel {
        /**
         * Creates a gradient panel using the given card layout.
         *
         * @param layout the card layout used by the panel
         */
        GradientPanel(CardLayout layout) {
            super(layout);
        }
        /**
         * Paints the gradient background.
         *
         * @param graphics the graphics context
         */
        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gradient = new GradientPaint(
                    0, 0, BACKGROUND_TOP,
                    getWidth(), getHeight(), BACKGROUND_BOTTOM);
            graphics2D.setPaint(gradient);
            graphics2D.fillRect(0, 0, getWidth(), getHeight());
            graphics2D.dispose();
            super.paintComponent(graphics);
        }

        /**
         * Indicates that this panel handles its own background painting.
         *
         * @return {@code false} because the panel is not fully opaque
         */
        @Override
        public boolean isOpaque() {
            return false;
        }
    }
}
