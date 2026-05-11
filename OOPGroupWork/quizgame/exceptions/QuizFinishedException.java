package OOPGroupProject.OOPGroupWork.quizgame.exceptions;

public class QuizFinishedException extends RuntimeException {
    public QuizFinishedException() {
        super("The quiz is already finished.");
    }
}
