package quizgame;

import java.awt.GraphicsEnvironment;
import java.util.Scanner;

import quizgame.console.ConsoleQuizRunner;
import quizgame.factory.QuizFactory;
import quizgame.gui.QuizGameGUI;

public class QuizGame {
    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || !QuizGameGUI.launch()) {
            launchConsoleMode();
        }
    }

    private static void launchConsoleMode() {
        Scanner scanner = new Scanner(System.in);
        ConsoleQuizRunner runner = new ConsoleQuizRunner(new QuizFactory(), scanner);
        runner.run();
        scanner.close();
    }
}
