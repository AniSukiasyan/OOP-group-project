import java.util.ArrayList;
import java.util.Scanner;

public class QuizGame {
    private ArrayList<Question> questions;
    private int score;

    public QuizGame() {
        questions = new ArrayList<>();
        score = 0;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        for (Question q : questions) {
            System.out.println(q.getText());

            String[] options = q.getOptions();
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + ": " + options[i]);
            }

            int answer = scanner.nextInt();

            if (q.isCorrect(answer)) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong!");
            }
        }

        System.out.println("Final score: " + score);
    }
}