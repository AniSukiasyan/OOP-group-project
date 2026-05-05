package quizgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quiz {
    private final String title;
    private final List<Question> questions;

    public Quiz(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getTitle() {
        return title;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public int getTotalPossibleScore() {
        int total = 0;
        for (Question question : questions) {
            total += question.getPoints();
        }
        return total;
    }

    public void start(Player player, Scanner scanner) {
        System.out.println();
        System.out.println("=== " + title + " ===");
        System.out.println("Player: " + player.getName());
        System.out.println("Questions: " + questions.size());
        System.out.println();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            System.out.println("Question " + (i + 1) + " - " + question.getPoints() + " point(s)");
            boolean correct = question.ask(scanner);

            if (correct) {
                player.addScore(question.getPoints());
                System.out.println("Correct! +" + question.getPoints() + " point(s)");
            } else {
                System.out.println("Wrong! Correct answer: " + question.getCorrectAnswer());
            }

            System.out.println();
        }

        System.out.println("Quiz finished.");
        System.out.println(player.getName() + ", your final score is " + player.getScore()
                + "/" + getTotalPossibleScore());
    }
}
