package OOPGroupProject.OOPGroupWork.quizgame;

import java.awt.GraphicsEnvironment;
import java.util.Scanner;

import OOPGroupProject.OOPGroupWork.quizgame.console.ConsoleQuizRunner;
import OOPGroupProject.OOPGroupWork.quizgame.factory.QuizFactory;
import OOPGroupProject.OOPGroupWork.quizgame.gui.QuizGameGUI;

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
