package OOPGroupWork.quizgame.questions;

import OOPGroupWork.quizgame.model.DifficultyLevel;
/**
 * Represents a true/false question in the quiz.
 */
public class TrueFalseQuestion extends Question {
    private final boolean correctAnswer;

    /**
     * Creates a true/false question with default Easy difficulty settings.
     * @param prompt the question prompt
     * @param correctAnswer the correct true/false answer
     */
    public TrueFalseQuestion(String prompt, boolean correctAnswer) {
        this(prompt, correctAnswer, DifficultyLevel.EASY, DifficultyLevel.EASY.getBasePoints());
    }

    /**
     * Creates a true/false question with custom difficulty and scoring.
     * @param prompt the question prompt
     * @param correctAnswer the correct true/false answer
     * @param difficulty the difficulty level
     * @param points the number of points awarded for a correct answer
     */
    public TrueFalseQuestion(String prompt, boolean correctAnswer, DifficultyLevel difficulty, int points) {
        super(prompt, difficulty, points);
        this.correctAnswer = correctAnswer;
    }

    /**
     * Gets the answer choices for this question.
     * @return an array containing {@code True} and {@code False}
     */
    @Override
    public String[] getChoices() {
        return new String[]{"True", "False"};
    }

    /**
     * Gets the answer type for this question.
     * @return {@code AnswerType.CHOICE}
     */
    @Override
    public AnswerType getAnswerType() {
        return AnswerType.CHOICE;
    }

    /**
     * Checks whether the player's answer matches the correct true/false value.
     * @param answer the player's answer
     * @return {@code true} if the answer is correct; otherwise {@code false}
     */
    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null) {
            return false;
        }

        String normalized = answer.trim().toLowerCase();
        if (normalized.equals("a") || normalized.equals("true") || normalized.equals("t")) {
            return correctAnswer;
        }
        if (normalized.equals("b") || normalized.equals("false") || normalized.equals("f")) {
            return !correctAnswer;
        }
        return false;
    }

    /**
     * Gets the correct answer as text.
     * @return {@code "True"} if the correct answer is true; otherwise {@code "False"}
     */
    @Override
    public String getCorrectAnswer() {
        return correctAnswer ? "True" : "False";
    }

    /**
     * Creates a copy of this question with updated scoring information.
     * @param difficulty the new difficulty level
     * @param points the new point value
     * @return a copied question with updated scoring
     */
    @Override
    public Question copyWithScoring(DifficultyLevel difficulty, int points) {
        return new TrueFalseQuestion(getPrompt(), correctAnswer, difficulty, points);
    }
}
