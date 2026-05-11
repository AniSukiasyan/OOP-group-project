package OOPGroupProject.OOPGroupWork.quizgame.exceptions;

public class InvalidScoreException extends RuntimeException {
    public InvalidScoreException(int points) {
        super("Score cannot be increased by a negative value: " + points);
    }
}
