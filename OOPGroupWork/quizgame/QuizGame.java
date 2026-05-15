package OOPGroupWork.quizgame;

import java.awt.GraphicsEnvironment;
import java.util.Scanner;

import OOPGroupWork.quizgame.console.ConsoleQuizRunner;
import OOPGroupWork.quizgame.factory.QuizFactory;
import OOPGroupWork.quizgame.gui.QuizGameGUI;
/**
 * Main entry point for the OOP Quiz Game application.
 * <p>
 * The program attempts to launch the graphical user interface first.
 * If GUI mode is unavailable, it starts the console version instead.
 * </p>
 */
public class QuizGame {

    /**
     * Starts the quiz game application.
     *
     * @param args command-line arguments, not used
     */
    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || !QuizGameGUI.launch()) {
            launchConsoleMode();
        }
    }

    /**
     * Starts the quiz game in console mode.
     */
    private static void launchConsoleMode() {
        Scanner scanner = new Scanner(System.in);
        ConsoleQuizRunner runner = new ConsoleQuizRunner(new QuizFactory(), scanner);
        runner.run();
        scanner.close();
    }
}