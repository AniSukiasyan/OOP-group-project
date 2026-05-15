// Question.java
package OOPGroupWork.quizgame.questions;

import OOPGroupWork.quizgame.model.DifficultyLevel;
/**
 * Represents a general quiz question.
 * <p>
 * This abstract class stores shared question data such as the prompt,
 * difficulty level, and point value. Specific question types must implement
 * answer checking and scoring-copy behavior.
 * </p>
 */
public abstract class Question {
    private final String prompt;
    private final DifficultyLevel difficulty;
    private final int points;

    /**
     * Creates a new question.
     * @param prompt the question prompt
     * @param difficulty the difficulty level of the question
     * @param points the number of points awarded for a correct answer
     */
    public Question(String prompt, DifficultyLevel difficulty, int points) {
        this.prompt = prompt;
        this.difficulty = difficulty;
        this.points = points;
    }

    /**
     * Gets the question prompt.
     * @return the question prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Gets the difficulty level.
     * @return the question difficulty
     */
    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the difficulty display label.
     * @return the difficulty display name
     */
    public String getDifficultyLabel() {
        return difficulty.getDisplayName();
    }

    /**
     * Gets the point value of the question.
     * @return the number of points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Gets the answer choices for choice-based questions.
     * @return an empty array by default
     */
    public String[] getChoices() {
        return new String[0];
    }

    /**
     * Gets the type of answer required by the question.
     * @return the answer type
     */
    public abstract AnswerType getAnswerType();

    /**
     * Checks whether the given answer is correct.
     * @param answer the player's answer
     * @return {@code true} if the answer is correct; otherwise {@code false}
     */
    public abstract boolean checkAnswer(String answer);

    /**
     * Gets the correct answer for display.
     * @return the correct answer
     */
    public abstract String getCorrectAnswer();

    /**
     * Creates a copy of this question with updated difficulty and points.
     * @param difficulty the new difficulty level
     * @param points the new point value
     * @return a copied question with updated scoring
     */
    public abstract Question copyWithScoring(DifficultyLevel difficulty, int points);
}