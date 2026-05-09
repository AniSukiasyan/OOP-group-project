package quizgame.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import quizgame.engine.AnswerResult;
import quizgame.engine.QuizSession;
import quizgame.exceptions.EmptyAnswerException;
import quizgame.exceptions.InvalidPlayerNameException;
import quizgame.exceptions.QuizFinishedException;
import quizgame.factory.QuizFactory;
import quizgame.model.DifficultyLevel;
import quizgame.model.Player;
import quizgame.model.Quiz;
import quizgame.questions.AnswerType;
import quizgame.questions.Question;

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

    private void startQuiz(String name, DifficultyLevel difficulty) {
        String finalName = name.trim();
        if (finalName.isEmpty()) {
            JOptionPane.showMessageDialog(this, new InvalidPlayerNameException().getMessage()
                    + "\nUsing default name: Player.");
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

    private void loadFillInBlankQuestion() {
        blankField = new JTextField();
        blankField.setColumns(25);
        styleTextField(blankField);
        dynamicAnswerPanel.add(createFieldLabel("Type your answer below:"));
        dynamicAnswerPanel.add(blankField);
    }

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

    private String collectAnswer() {
        if (blankField != null) {
            return blankField.getText();
        }
        if (optionGroup != null && optionGroup.getSelection() != null) {
            return optionGroup.getSelection().getActionCommand();
        }
        return null;
    }

    private void showFinalResult() {
        JOptionPane.showMessageDialog(
                this,
                "Quiz finished!\n" + session.getPlayer().getName() + ", your final score is "
                        + session.getScoreSummary() + "\nLevel: " + session.getQuiz().getDifficultyLabel(),
                "Final Score",
                JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }

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

    private JPanel createSurfacePanel(BorderLayout layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(PANEL_COLOR);
        return panel;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT_COLOR);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 16));
        field.setForeground(TEXT_COLOR);
        field.setBackground(Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(190, 210, 235), 1, true),
                new EmptyBorder(8, 10, 8, 10)));
    }

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

    private static class ColorButton extends JButton {
        private final Color backgroundColor;

        ColorButton(String text, Color backgroundColor, Color foregroundColor) {
            super(text);
            this.backgroundColor = backgroundColor;
            setForeground(foregroundColor);
        }

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

    private static class GradientPanel extends JPanel {
        GradientPanel(CardLayout layout) {
            super(layout);
        }

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

        @Override
        public boolean isOpaque() {
            return false;
        }
    }
}
