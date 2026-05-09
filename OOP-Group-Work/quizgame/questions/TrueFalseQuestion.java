package quizgame.questions;

import quizgame.model.DifficultyLevel;

public class TrueFalseQuestion extends Question {
    private final boolean correctAnswer;

    public TrueFalseQuestion(String prompt, boolean correctAnswer) {
        this(prompt, correctAnswer, DifficultyLevel.EASY, DifficultyLevel.EASY.getBasePoints());
    }

    public TrueFalseQuestion(String prompt, boolean correctAnswer, DifficultyLevel difficulty, int points) {
        super(prompt, difficulty, points);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String[] getChoices() {
        return new String[]{"True", "False"};
    }

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.CHOICE;
    }

    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null) {
            return false;
        }

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

    @Override
    public Question copyWithScoring(DifficultyLevel difficulty, int points) {
        return new TrueFalseQuestion(getPrompt(), correctAnswer, difficulty, points);
    }
}
