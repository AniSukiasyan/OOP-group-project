package quizgame;

public class TrueFalseQuestion extends Question {
    private final boolean correctAnswer;

    public TrueFalseQuestion(String prompt, boolean correctAnswer, int points) {
        super(prompt, points);
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public void display() {
        System.out.println(getPrompt());
        System.out.println("A. True");
        System.out.println("B. False");
    }

    @Override
    public boolean checkAnswer(String answer) {
        String normalized = answer.trim().toLowerCase();

        if (normalized.equals("a") || normalized.equals("true") || normalized.equals("t")) {
            return correctAnswer;
        }

        if (normalized.equals("b") || normalized.equals("false") || normalized.equals("f")) {
            return !correctAnswer;
        }

        return false;
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswer ? "True" : "False";
    }
}
