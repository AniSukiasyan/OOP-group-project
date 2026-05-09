package quizgame.engine;

import quizgame.questions.Question;

public class AnswerResult {
    private final Question question;
    private final boolean correct;
    private final int pointsEarned;

    public AnswerResult(Question question, boolean correct, int pointsEarned) {
        this.question = question;
        this.correct = correct;
        this.pointsEarned = pointsEarned;
    }

    public Question getQuestion() {
        return question;
    }

    public boolean isCorrect() {
        return correct;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }
}
