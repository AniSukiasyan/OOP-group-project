package OOPGroupWork.quizgame.exceptions;
/**
 * Exception thrown when an invalid difficulty choice is provided.
 */
public class InvalidDifficultyException extends RuntimeException {

    /**
     * Creates a new invalid difficulty exception.
     *
     * @param value the invalid difficulty value entered by the user
     */
    public InvalidDifficultyException(String value) {
        super("Invalid difficulty choice: " + value);
    }
}