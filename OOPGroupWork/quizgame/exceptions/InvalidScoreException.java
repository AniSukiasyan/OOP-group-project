package OOPGroupWork.quizgame.exceptions;
/**
 * Exception thrown when an invalid score value is used.
 * <p>
 * This exception is typically thrown when attempting to add
 * a negative number of points to a player's score.
 * </p>
 */
public class InvalidScoreException extends RuntimeException {

    /**
     * Creates a new invalid score exception.
     *
     * @param points the invalid score value
     */
    public InvalidScoreException(int points) {
        super("Score cannot be increased by a negative value: " + points);
    }
}