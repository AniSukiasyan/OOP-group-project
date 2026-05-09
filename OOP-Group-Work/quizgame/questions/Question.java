package quizgame.questions;

import quizgame.model.DifficultyLevel;

public abstract class Question {
    private final String prompt;
    private final DifficultyLevel difficulty;
    private final int points;

    public Question(String prompt, DifficultyLevel difficulty, int points) {
        this.prompt = prompt;
        this.difficulty = difficulty;
        this.points = points;
    }

    public String getPrompt() {
        return prompt;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public String getDifficultyLabel() {
        return difficulty.getDisplayName();
    }

    public int getPoints() {
        return points;
    }

    public String[] getChoices() {
        return new String[0];
    }

    public abstract AnswerType getAnswerType();

    public abstract boolean checkAnswer(String answer);

    public abstract String getCorrectAnswer();

    public abstract Question copyWithScoring(DifficultyLevel difficulty, int points);
}
