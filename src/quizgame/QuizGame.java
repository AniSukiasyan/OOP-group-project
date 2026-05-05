package quizgame;

import java.awt.GraphicsEnvironment;
import java.util.Scanner;

public class QuizGame {
    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless() || !QuizGameGUI.launch()) {
            launchConsoleMode();
            return;
        }
    }

    private static void launchConsoleMode() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("GUI mode is not available in this environment.");
        System.out.println("Starting console mode instead.");
        System.out.println("Welcome to the OOP Quiz Game!");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            name = "Player";
        }

        Player player = new Player(name);
        QuizFactory factory = new QuizFactory();
        Quiz quiz = factory.createOopQuiz();
        quiz.start(player, scanner);
        scanner.close();
    }
}
