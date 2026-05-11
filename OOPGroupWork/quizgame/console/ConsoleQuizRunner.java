package OOPGroupProject.OOPGroupWork.quizgame.console;

import java.util.Scanner;

import OOPGroupProject.OOPGroupWork.quizgame.engine.AnswerResult;
import  OOPGroupProject.OOPGroupWork.quizgame.engine.QuizSession;
import OOPGroupProject.OOPGroupWork.quizgame.exceptions.EmptyAnswerException;
import OOPGroupProject.OOPGroupWork.quizgame.exceptions.InvalidDifficultyException;
import OOPGroupProject.OOPGroupWork.quizgame.exceptions.InvalidPlayerNameException;
import OOPGroupProject.OOPGroupWork.quizgame.factory.QuizFactory;
import  OOPGroupProject.OOPGroupWork.quizgame.model.DifficultyLevel;
import OOPGroupProject.OOPGroupWork.quizgame.model.Player;
import OOPGroupProject.OOPGroupWork.quizgame.model.Quiz;
import OOPGroupProject.OOPGroupWork.quizgame.questions.AnswerType;
import OOPGroupProject.OOPGroupWork.quizgame.questions.Question;

public class ConsoleQuizRunner {
    private final QuizFactory factory;
    private final Scanner scanner;

    public ConsoleQuizRunner(QuizFactory factory, Scanner scanner) {
        this.factory = factory;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("GUI mode is not available in this environment.");
        System.out.println("Starting console mode instead.");
        System.out.println("Welcome to the OOP Quiz Game!");
        System.out.print("Enter your name: ");

        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println(new InvalidPlayerNameException().getMessage() + " Using default name: Player.");
            name = "Player";
        }

        DifficultyLevel difficulty = chooseDifficulty();
        Quiz quiz = factory.createOopQuiz(difficulty);
        QuizSession session = new QuizSession(quiz, new Player(name));
        play(session);
    }

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
