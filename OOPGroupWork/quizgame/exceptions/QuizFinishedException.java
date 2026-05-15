package OOPGroupWork.quizgame.exceptions;
/**
 * Exception thrown when an action is attempted after the quiz has finished.
 */
public class QuizFinishedException extends RuntimeException {

    /**
     * Creates a new quiz finished exception.
     */
    public QuizFinishedException() {
        super("The quiz is already finished.");
    }
}