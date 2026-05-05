package quizgame;

import java.util.Scanner;

public abstract class Question {
    private final String prompt;
    private final int points;

    public Question(String prompt, int points) {
        this.prompt = prompt;
        this.points = points;
    }

    public String getPrompt() {
        return prompt;
    }

    public int getPoints() {
        return points;
    }

    public abstract void display();

    public abstract boolean checkAnswer(String answer);

    public abstract String getCorrectAnswer();

    public boolean ask(Scanner scanner) {
        display();
        System.out.print("Your answer: ");
        String answer = scanner.nextLine().trim();
        return checkAnswer(answer);
    }
}
