package quizgame.exceptions;

public class InvalidDifficultyException extends RuntimeException {
    public InvalidDifficultyException(String value) {
        super("Invalid difficulty choice: " + value);
    }
}
