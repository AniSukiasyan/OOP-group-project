package quizgame.exceptions;

public class InvalidPlayerNameException extends RuntimeException {
    public InvalidPlayerNameException() {
        super("Player name cannot be empty.");
    }
}
