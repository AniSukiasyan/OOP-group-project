package OOPGroupWork.quizgame.engine;

import OOPGroupWork.quizgame.questions.Question;
/**
 * Represents the result of submitting an answer to a quiz question.
 * <p>
 * This class stores the answered question, whether the answer was correct,
 * and the number of points earned for that answer.
 * </p>
 */
public class AnswerResult {
    private final Question question;
    private final boolean correct;
    private final int pointsEarned;

    /**
     * Creates a new answer result.
     *
     * @param question the question that was answered
     * @param correct whether the submitted answer was correct
     * @param pointsEarned the number of points earned for the answer
     */
    public AnswerResult(Question question, boolean correct, int pointsEarned) {
        this.question = question;
        this.correct = correct;
        this.pointsEarned = pointsEarned;
    }

    /**
     * Gets the question that was answered.
     *
     * @return the answered question
     */
    public Question getQuestion() {
        return question;
    }

    /**
     * Checks whether the submitted answer was correct.
     *
     * @return {@code true} if the answer was correct; otherwise {@code false}
     */
    public boolean isCorrect() {
        return correct;
    }

    /**
     * Gets the number of points earned for the answer.
     *
     * @return the earned points
     */
    public int getPointsEarned() {
        return pointsEarned;
    }
}