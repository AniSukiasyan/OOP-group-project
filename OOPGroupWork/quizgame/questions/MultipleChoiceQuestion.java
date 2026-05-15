package OOPGroupWork.quizgame.questions;

import OOPGroupWork.quizgame.model.DifficultyLevel;
/**
 * Represents a multiple-choice question in the quiz.
 * <p>
 * Players answer by selecting one option from a list of choices.
 * </p>
 */
public class MultipleChoiceQuestion extends Question {
    private final String[] choices;
    private final char correctOption;

    /**
     * Creates a multiple-choice question with default Easy difficulty settings.
     *
     * @param prompt the question prompt
     * @param choices the available answer choices
     * @param correctOption the correct option letter
     */
    public MultipleChoiceQuestion(String prompt, String[] choices, char correctOption) {
        this(prompt, choices, correctOption, DifficultyLevel.EASY, DifficultyLevel.EASY.getBasePoints());
    }

    /**
     * Creates a multiple-choice question with custom difficulty and scoring.
     *
     * @param prompt the question prompt
     * @param choices the available answer choices
     * @param correctOption the correct option letter
     * @param difficulty the difficulty level
     * @param points the number of points awarded for a correct answer
     */
    public MultipleChoiceQuestion(String prompt, String[] choices, char correctOption,
                                  DifficultyLevel difficulty, int points) {
        super(prompt, difficulty, points);
        this.choices = choices.clone();
        this.correctOption = Character.toUpperCase(correctOption);
    }

    /**
     * Gets a copy of the answer choices.
     *
     * @return the available answer choices
     */
    @Override
    public String[] getChoices() {
        return choices.clone();
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
     * Checks whether the selected option matches the correct option.
     * @param answer the player's selected answer
     * @return {@code true} if the answer is correct; otherwise {@code false}
     */
    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null || answer.trim().isEmpty()) {
            return false;
        }
        return Character.toUpperCase(answer.trim().charAt(0)) == correctOption;
    }

    /**
     * Gets the correct answer with its option letter and choice text.
     * @return the formatted correct answer
     */
    @Override
    public String getCorrectAnswer() {
        int index = correctOption - 'A';
        if (index >= 0 && index < choices.length) {
            return correctOption + " (" + choices[index] + ")";
        }
        return String.valueOf(correctOption);
    }

    /**
     * Creates a copy of this question with updated scoring information.
     * @param difficulty the new difficulty level
     * @param points the new point value
     * @return a copied question with updated scoring
     */
    @Override
    public Question copyWithScoring(DifficultyLevel difficulty, int points) {
        return new MultipleChoiceQuestion(getPrompt(), choices, correctOption, difficulty, points);
    }
}