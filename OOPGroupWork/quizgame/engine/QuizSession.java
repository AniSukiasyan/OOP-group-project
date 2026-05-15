package OOPGroupWork.quizgame.engine;

import OOPGroupWork.quizgame.exceptions.EmptyAnswerException;
import OOPGroupWork.quizgame.exceptions.QuizFinishedException;
import OOPGroupWork.quizgame.model.Player;
import OOPGroupWork.quizgame.model.Quiz;
import OOPGroupWork.quizgame.questions.Question;

/**
 * Represents an active quiz session for a specific player.
 * <p>
 * This class tracks the current question, submitted answers, progress,
 * and player score during a quiz.
 * </p>
 */
public class QuizSession {
    private final Quiz quiz;
    private final Player player;
    private int currentQuestionIndex;

    /**
     * Creates a new quiz session.
     *
     * @param quiz the quiz being played
     * @param player the player taking the quiz
     */
    public QuizSession(Quiz quiz, Player player) {
        this.quiz = quiz;
        this.player = player;
    }

    /**
     * Gets the quiz for this session.
     *
     * @return the quiz being played
     */
    public Quiz getQuiz() {
        return quiz;
    }

    /**
     * Gets the player for this session.
     *
     * @return the player taking the quiz
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the index of the current question.
     *
     * @return the zero-based current question index
     */
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    /**
     * Gets the number of questions already answered.
     *
     * @return the answered question count
     */
    public int getAnsweredQuestionCount() {
        return currentQuestionIndex;
    }

    /**
     * Checks whether the quiz session has finished.
     *
     * @return {@code true} if all questions have been answered;
     * otherwise {@code false}
     */
    public boolean isFinished() {
        return currentQuestionIndex >= quiz.getQuestionCount();
    }

    /**
     * Gets the current question.
     *
     * @return the current question, or {@code null} if the quiz is finished
     */
    public Question getCurrentQuestion() {
        if (isFinished()) {
            return null;
        }
        return quiz.getQuestion(currentQuestionIndex);
    }

    /**
     * Submits an answer for the current question.
     * <p>
     * If the answer is correct, the player's score is increased by the
     * question's point value. After submission, the session moves to the
     * next question.
     * </p>
     *
     * @param answer the answer submitted by the player
     * @return an {@link AnswerResult} containing the submitted question,
     * correctness result, and earned points
     * @throws QuizFinishedException if there are no more questions to answer
     * @throws EmptyAnswerException if the submitted answer is {@code null}
     * or empty
     */
    public AnswerResult submitAnswer(String answer) {
        Question question = getCurrentQuestion();
        if (question == null) {
            throw new QuizFinishedException();
        }
        if (answer == null || answer.trim().isEmpty()) {
            throw new EmptyAnswerException();
        }

        boolean correct = question.checkAnswer(answer);
        int pointsEarned = correct ? question.getPoints() : 0;
        if (correct) {
            player.addScore(pointsEarned);
        }
        currentQuestionIndex++;

        return new AnswerResult(question, correct, pointsEarned);
    }

    /**
     * Gets a formatted summary of the player's score.
     *
     * @return the score summary for the current player
     */
    public String getScoreSummary() {
        return quiz.getScoreSummary(player);
    }

    /**
     * Gets a formatted summary of the quiz progress.
     * @return the progress summary based on the current question index
     */
    public String getProgressSummary() {
        return quiz.getProgressSummary(currentQuestionIndex);
    }
}