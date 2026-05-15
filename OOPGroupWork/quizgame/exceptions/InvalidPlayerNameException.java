package OOPGroupWork.quizgame.exceptions;
/**
 * Exception thrown when a player name is invalid or empty.
 */
public class InvalidPlayerNameException extends RuntimeException {

    /**
     * Creates a new invalid player name exception.
     */
    public InvalidPlayerNameException() {
        super("Player name cannot be empty.");
    }
}