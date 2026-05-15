package OOPGroupWork.quizgame.model;
/**
 * Represents the available quiz difficulty levels.
 * <p>
 * Each difficulty level defines a display name, the base number of
 * points awarded per question, and the number of questions included
 * in the quiz.
 * </p>
 */
public enum DifficultyLevel {
    /**
     * Easy difficulty level.
     */
    EASY("Easy", 5, 8),
    /**
     * Medium difficulty level.
     */
    MEDIUM("Medium", 10, 10),
    /**
     * Hard difficulty level.
     */
    HARD("Hard", 15, 10),
    /**
     * Extremely hard difficulty level.
     */
    EXTREMELY_HARD("Extremely Hard", 25, 8);

    private final String displayName;
    private final int basePoints;
    private final int questionCount;

    /**
     * Creates a difficulty level.
     * @param displayName the display name of the difficulty
     * @param basePoints the base points awarded per question
     * @param questionCount the number of questions in the quiz
     */
    DifficultyLevel(String displayName, int basePoints, int questionCount) {
        this.displayName = displayName;
        this.basePoints = basePoints;
        this.questionCount = questionCount;
    }

    /**
     * Gets the display name of the difficulty level.
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Gets the base points awarded per question.
     * @return the base point value
     */
    public int getBasePoints() {
        return basePoints;
    }

    /**
     * Gets the number of questions included in the quiz.
     * @return the question count
     */
    public int getQuestionCount() {
        return questionCount;
    }

    /**
     * Returns the display name of the difficulty level.
     * @return the display name
     */
    @Override
    public String toString() {
        return displayName;
    }
}