package quizgame;

import java.awt.HeadlessException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class QuizGameGUI extends JFrame {
    private final Quiz quiz;
    private Player player;
    private int currentQuestionIndex;

    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final JLabel questionNumberLabel;
    private final JLabel scoreLabel;
    private final JTextArea questionArea;
    private final JPanel dynamicAnswerPanel;
    private final JButton submitButton;

    private JTextField blankField;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionGroup;

    public QuizGameGUI(Quiz quiz) {
        this.quiz = quiz;
        this.currentQuestionIndex = 0;

        setTitle("OOP Quiz Game");
        setSize(760, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        JPanel welcomePanel = createWelcomePanel();
        JPanel quizPanel = new JPanel(new BorderLayout(12, 12));
        quizPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 0, 6));
        questionNumberLabel = new JLabel("Question 1", SwingConstants.LEFT);
        questionNumberLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        scoreLabel = new JLabel("Score: 0", SwingConstants.LEFT);
        topPanel.add(questionNumberLabel);
        topPanel.add(scoreLabel);

        questionArea = new JTextArea();
        questionArea.setEditable(false);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("SansSerif", Font.PLAIN, 18));
        questionArea.setOpaque(false);

        dynamicAnswerPanel = new JPanel();
        dynamicAnswerPanel.setLayout(new BoxLayout(dynamicAnswerPanel, BoxLayout.Y_AXIS));

        submitButton = new JButton("Submit Answer");
        submitButton.addActionListener(e -> submitCurrentAnswer());

        JPanel answerSection = new JPanel(new BorderLayout(0, 10));
        answerSection.add(dynamicAnswerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(submitButton);
        answerSection.add(bottomPanel, BorderLayout.SOUTH);

        quizPanel.add(topPanel, BorderLayout.NORTH);
        quizPanel.add(new JScrollPane(questionArea), BorderLayout.CENTER);
        quizPanel.add(answerSection, BorderLayout.SOUTH);

        cardPanel.add(welcomePanel, "WELCOME");
        cardPanel.add(quizPanel, "QUIZ");

        add(cardPanel);
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(quiz.getTitle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        JTextArea introArea = new JTextArea(
                "Welcome to the Java OOP quiz game.\n"
                        + "This project demonstrates inheritance, abstraction, encapsulation, and polymorphism.\n"
                        + "Enter your name to begin.");
        introArea.setEditable(false);
        introArea.setOpaque(false);
        introArea.setLineWrap(true);
        introArea.setWrapStyleWord(true);
        introArea.setFont(new Font("SansSerif", Font.PLAIN, 16));

        JTextField nameField = new JTextField();
        JButton startButton = new JButton("Start Quiz");
        startButton.addActionListener(e -> startQuiz(nameField.getText()));

        JPanel formPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        formPanel.add(new JLabel("Player name:"));
        formPanel.add(nameField);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(startButton);

        JPanel lowerPanel = new JPanel(new BorderLayout(0, 10));
        lowerPanel.add(formPanel, BorderLayout.CENTER);
        lowerPanel.add(actionPanel, BorderLayout.SOUTH);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(introArea, BorderLayout.CENTER);
        panel.add(lowerPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void startQuiz(String name) {
        String finalName = name.trim().isEmpty() ? "Player" : name.trim();
        player = new Player(finalName);
        currentQuestionIndex = 0;
        scoreLabel.setText("Score: 0");
        cardLayout.show(cardPanel, "QUIZ");
        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= quiz.getQuestionCount()) {
            showFinalResult();
            return;
        }

        Question question = quiz.getQuestion(currentQuestionIndex);
        questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + quiz.getQuestionCount()
                + "  |  " + question.getPoints() + " point(s)");
        questionArea.setText(question.getPrompt());

        dynamicAnswerPanel.removeAll();
        blankField = null;
        optionButtons = null;
        optionGroup = null;

        if (question instanceof MultipleChoiceQuestion) {
            loadMultipleChoice((MultipleChoiceQuestion) question);
        } else if (question instanceof TrueFalseQuestion) {
            loadTrueFalseQuestion();
        } else if (question instanceof FillInBlankQuestion || question instanceof CodeCompletionQuestion) {
            loadFillInBlankQuestion();
        }

        dynamicAnswerPanel.revalidate();
        dynamicAnswerPanel.repaint();
    }

    private void loadMultipleChoice(MultipleChoiceQuestion question) {
        String[] choices = question.getChoices();
        optionButtons = new JRadioButton[choices.length];
        optionGroup = new ButtonGroup();

        for (int i = 0; i < choices.length; i++) {
            char optionLetter = (char) ('A' + i);
            JRadioButton button = new JRadioButton(optionLetter + ". " + choices[i]);
            button.setActionCommand(String.valueOf(optionLetter));
            optionButtons[i] = button;
            optionGroup.add(button);
            dynamicAnswerPanel.add(button);
        }
    }

    private void loadTrueFalseQuestion() {
        optionButtons = new JRadioButton[2];
        optionGroup = new ButtonGroup();

        JRadioButton trueButton = new JRadioButton("A. True");
        trueButton.setActionCommand("True");
        JRadioButton falseButton = new JRadioButton("B. False");
        falseButton.setActionCommand("False");

        optionButtons[0] = trueButton;
        optionButtons[1] = falseButton;

        optionGroup.add(trueButton);
        optionGroup.add(falseButton);
        dynamicAnswerPanel.add(trueButton);
        dynamicAnswerPanel.add(falseButton);
    }

    private void loadFillInBlankQuestion() {
        blankField = new JTextField();
        blankField.setColumns(25);
        dynamicAnswerPanel.add(new JLabel("Type your answer below:"));
        dynamicAnswerPanel.add(blankField);
    }

    private void submitCurrentAnswer() {
        Question question = quiz.getQuestion(currentQuestionIndex);
        String answer = collectAnswer();

        if (answer == null || answer.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter or select an answer first.");
            return;
        }

        boolean correct = question.checkAnswer(answer);
        if (correct) {
            player.addScore(question.getPoints());
            scoreLabel.setText("Score: " + player.getScore());
            JOptionPane.showMessageDialog(this, "Correct! +" + question.getPoints() + " point(s)");
        } else {
            JOptionPane.showMessageDialog(this, "Wrong answer.\nCorrect answer: " + question.getCorrectAnswer());
        }

        currentQuestionIndex++;
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
                "Quiz finished!\n" + player.getName() + ", your final score is "
                        + player.getScore() + "/" + quiz.getTotalPossibleScore(),
                "Final Score",
                JOptionPane.INFORMATION_MESSAGE
        );
        dispose();
    }

    public static boolean launch() {
        if (GraphicsEnvironment.isHeadless()) {
            return false;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                QuizFactory factory = new QuizFactory();
                QuizGameGUI gui = new QuizGameGUI(factory.createOopQuiz());
                gui.setVisible(true);
            } catch (HeadlessException exception) {
                System.out.println("GUI mode is not available in this environment.");
            }
        });
        return true;
    }
}
