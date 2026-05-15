package OOPGroupWork.quizgame.exceptions;
/**
 * Exception thrown when the player submits an empty answer.
 */
public class EmptyAnswerException extends RuntimeException {

    /**
     * Creates a new empty answer exception.
     */
    public EmptyAnswerException() {
        super("Please enter or select an answer first.");
    }
}