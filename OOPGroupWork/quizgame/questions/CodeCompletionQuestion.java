package OOPGroupWork.quizgame.questions;

import OOPGroupWork.quizgame.model.DifficultyLevel;
import OOPGroupWork.quizgame.util.AnswerUtils;
/**
 * Represents a code completion question in the quiz.
 * <p>
 * Players must type the missing code keyword, statement, or expression.
 * Multiple accepted answers may be provided.
 * </p>
 */
public class CodeCompletionQuestion extends Question {
    private final String[] acceptedAnswers;

    /**
     * Creates a code completion question with default Easy difficulty settings.
     * @param prompt the question prompt
     * @param acceptedAnswers the accepted correct answers
     */
    public CodeCompletionQuestion(String prompt, String... acceptedAnswers) {
        this(prompt, DifficultyLevel.EASY, DifficultyLevel.EASY.getBasePoints(), acceptedAnswers);
    }

    /**
     * Creates a code completion question with custom difficulty and scoring.
     * @param prompt the question prompt
     * @param difficulty the difficulty level
     * @param points the number of points awarded for a correct answer
     * @param acceptedAnswers the accepted correct answers
     */
    public CodeCompletionQuestion(String prompt, DifficultyLevel difficulty, int points, String... acceptedAnswers) {
        super(prompt, difficulty, points);
        this.acceptedAnswers = acceptedAnswers.clone();
    }

    /**
     * Gets the answer type for this question.
     * @return {@code AnswerType.TEXT}
     */
    @Override
    public AnswerType getAnswerType() {
        return AnswerType.TEXT;
    }

    /**
     * Checks whether the provided answer matches any accepted answer.
     * @param answer the player's answer
     * @return {@code true} if the answer is correct; otherwise {@code false}
     */
    @Override
    public boolean checkAnswer(String answer) {
        return AnswerUtils.matchesAny(answer, acceptedAnswers);
    }

    /**
     * Gets the formatted correct answer(s).
     * @return the accepted answers as a formatted string
     */
    @Override
    public String getCorrectAnswer() {
        return AnswerUtils.formatAcceptedAnswers(acceptedAnswers);
    }

    /**
     * Creates a copy of this question with updated scoring information.
     * @param difficulty the new difficulty level
     * @param points the new point value
     * @return a copied question with updated scoring
     */
    @Override
    public Question copyWithScoring(DifficultyLevel difficulty, int points) {
        return new CodeCompletionQuestion(getPrompt(), difficulty, points, acceptedAnswers);
    }
}
