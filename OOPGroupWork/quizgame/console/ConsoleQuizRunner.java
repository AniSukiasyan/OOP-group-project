package OOPGroupWork.quizgame.console;

import java.util.Scanner;

import OOPGroupWork.quizgame.engine.AnswerResult;
import OOPGroupWork.quizgame.engine.QuizSession;
import OOPGroupWork.quizgame.exceptions.EmptyAnswerException;
import OOPGroupWork.quizgame.exceptions.InvalidDifficultyException;
import OOPGroupWork.quizgame.exceptions.InvalidPlayerNameException;
import OOPGroupWork.quizgame.factory.QuizFactory;
import OOPGroupWork.quizgame.model.DifficultyLevel;
import OOPGroupWork.quizgame.model.Player;
import OOPGroupWork.quizgame.model.Quiz;
import OOPGroupWork.quizgame.questions.AnswerType;
import OOPGroupWork.quizgame.questions.Question;
/**
 * Runs the OOP Quiz Game in console mode
 *
 * This class handles user interaction through the command line, including
 * reading the player's name, selecting a difficulty level, displaying
 * questions, accepting answers, and showing the final score
 */
public class ConsoleQuizRunner {
    private final QuizFactory factory;
    private final Scanner scanner;

    /**
     * Creates a new console quiz runner.
     *
     * @param factory the quiz factory used to create quizzes
     * @param scanner the scanner used to read user input from the console
     */
    public ConsoleQuizRunner(QuizFactory factory, Scanner scanner) {
        this.factory = factory;
        this.scanner = scanner;
    }

    /**
     * Starts the quiz game in console mode.
     * The method asks the player for their name, lets them choose a difficulty
     * level, creates a quiz session, and begins the quiz.
     */
    public void run() {
        System.out.println("GUI mode is not available in this environment.");
        System.out.println("Starting console mode instead.");
        System.out.println("Welcome to the OOP Quiz Game!");
        System.out.print("Enter your name: ");

        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println(new InvalidPlayerNameException().getMessage()
                    + " No name was entered, so the name has been assigned as \"Player\".");
            name = "Player";
        }

        DifficultyLevel difficulty = chooseDifficulty();
        Quiz quiz = factory.createOopQuiz(difficulty);
        QuizSession session = new QuizSession(quiz, new Player(name));
        play(session);
    }
    /**
     * Displays the available difficulty levels and reads the player's choice.
     * If the input is invalid, the Easy difficulty level is selected by default.
     *
     * @return the selected difficulty level, or {@code DifficultyLevel.EASY}
     * if the user enters an invalid choice
     */
    private DifficultyLevel chooseDifficulty() {
        DifficultyLevel[] difficulties = factory.getAvailableDifficulties();

        System.out.println();
        System.out.println("Choose a level:");
        for (int i = 0; i < difficulties.length; i++) {
            DifficultyLevel difficulty = difficulties[i];
            System.out.println((i + 1) + ". " + difficulty.getDisplayName()
                    + " (" + difficulty.getBasePoints() + "+ points per question)");
        }
        System.out.print("Level number: ");

        String answer = scanner.nextLine().trim();
        try {
            int index = Integer.parseInt(answer) - 1;
            if (index < 0 || index >= difficulties.length) {
                throw new InvalidDifficultyException(answer);
            }
            return difficulties[index];
        } catch (NumberFormatException | InvalidDifficultyException exception) {
            System.out.println("Invalid difficulty choice. Starting Easy level.");
            return DifficultyLevel.EASY;
        }
    }

    /**
     * Runs the main quiz loop for the given quiz
     * This method displays each question, reads the player's answer, submits it
     * to the session, and prints whether the answer was correct or incorrect.
     * When all questions are answered, it displays the final
     * @param session the quiz session to play
     */
    private void play(QuizSession session) {
        Quiz quiz = session.getQuiz();

        System.out.println();
        System.out.println("=== " + quiz.getTitle() + " ===");
        System.out.println("Level: " + quiz.getDifficultyLabel());
        System.out.println("Player: " + session.getPlayer().getName());
        System.out.println("Questions: " + quiz.getQuestionCount());
        System.out.println();

        while (!session.isFinished()) {
            Question question = session.getCurrentQuestion();
            System.out.println("Question " + (session.getCurrentQuestionIndex() + 1)
                    + " - " + question.getDifficultyLabel()
                    + " - " + question.getPoints() + " point(s)");
            printQuestion(question);
            System.out.print("Your answer: ");

            try {
                AnswerResult result = session.submitAnswer(scanner.nextLine().trim());
                if (result.isCorrect()) {
                    System.out.println("Correct! +" + result.getPointsEarned() + " point(s)");
                } else {
                    System.out.println("Wrong! Correct answer: " + question.getCorrectAnswer());
                }
            } catch (EmptyAnswerException exception) {
                System.out.println(exception.getMessage());
            }
            System.out.println();
        }

        System.out.println("Quiz finished.");
        System.out.println(session.getPlayer().getName() + ", your final score is "
                + session.getScoreSummary());
    }

    /**
     * Prints the question prompt and, if applicable, its answer choices.
     * For multiple-choice questions, each choice is labeled with a letter
     * starting from {@code A}.
     * @param question the question to display
     */
    private void printQuestion(Question question) {
        System.out.println(question.getPrompt());

        if (question.getAnswerType() == AnswerType.CHOICE) {
            String[] choices = question.getChoices();
            for (int i = 0; i < choices.length; i++) {
                char option = (char) ('A' + i);
                System.out.println(option + ". " + choices[i]);
            }
        }
    }
}
