package OOPGroupWork.quizgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import OOPGroupWork.quizgame.questions.Question;
import OOPGroupWork.quizgame.model.DifficultyLevel;
import OOPGroupWork.quizgame.model.Player;
/**
 * Represents a quiz with a title, difficulty level, and list of questions.
 */
public class Quiz {
    private final String title;
    private final DifficultyLevel difficulty;
    private final List<Question> questions;

    /**
     * Creates a new quiz.
     *
     * @param title the quiz title
     * @param difficulty the quiz difficulty level
     */
    public Quiz(String title, DifficultyLevel difficulty) {
        this.title = title;
        this.difficulty = difficulty;
        this.questions = new ArrayList<>();
    }

    /**
     * Adds a question to the quiz.
     *
     * @param question the question to add
     */
    public void addQuestion(Question question) {
        questions.add(question);
    }

    /**
     * Gets the quiz title.
     *
     * @return the quiz title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the quiz difficulty.
     *
     * @return the difficulty level
     */
    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    /**
     * Gets the display label for the quiz difficulty.
     *
     * @return the difficulty display name
     */
    public String getDifficultyLabel() {
        return difficulty.getDisplayName();
    }

    /**
     * Gets the number of questions in the quiz.
     *
     * @return the question count
     */
    public int getQuestionCount() {
        return questions.size();
    }

    /**
     * Gets a question by index.
     *
     * @param index the zero-based question index
     * @return the question at the given index
     */
    public Question getQuestion(int index) {
        return questions.get(index);
    }

    /**
     * Gets an unmodifiable list of quiz questions.
     *
     * @return the quiz questions
     */
    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    /**
     * Calculates the total possible score for the quiz.
     *
     * @return the total possible score
     */
    public int getTotalPossibleScore() {
        int total = 0;
        for (Question question : questions) {
            total += question.getPoints();
        }
        return total;
    }

    /**
     * Calculates the player's score percentage.
     *
     * @param player the player whose score is used
     * @return the score percentage, or {@code 0} if the total score is zero
     */
    public int getAnsweredScorePercent(Player player) {
        int total = getTotalPossibleScore();
        if (total == 0) {
            return 0;
        }
        return Math.round((player.getScore() * 100.0f) / total);
    }

    /**
     * Gets a formatted score summary for the player.
     *
     * @param player the player whose score is summarized
     * @return the formatted score summary
     */
    public String getScoreSummary(Player player) {
        return player.getScore() + "/" + getTotalPossibleScore()
                + " (" + getAnsweredScorePercent(player) + "%)";
    }

    /**
     * Gets a formatted progress summary.
     *
     * @param answeredQuestions the number of answered questions
     * @return the formatted progress summary
     */
    public String getProgressSummary(int answeredQuestions) {
        return answeredQuestions + "/" + questions.size() + " answered";
    }
}