package quizgame.exceptions;

public class EmptyAnswerException extends RuntimeException {
    public EmptyAnswerException() {
        super("Please enter or select an answer first.");
    }
}
