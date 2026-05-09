package quizgame.questions;

import quizgame.model.DifficultyLevel;

public class MultipleChoiceQuestion extends Question {
    private final String[] choices;
    private final char correctOption;

    public MultipleChoiceQuestion(String prompt, String[] choices, char correctOption) {
        this(prompt, choices, correctOption, DifficultyLevel.EASY, DifficultyLevel.EASY.getBasePoints());
    }

    public MultipleChoiceQuestion(String prompt, String[] choices, char correctOption,
            DifficultyLevel difficulty, int points) {
        super(prompt, difficulty, points);
        this.choices = choices.clone();
        this.correctOption = Character.toUpperCase(correctOption);
    }

    @Override
    public String[] getChoices() {
        return choices.clone();
    }

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.CHOICE;
    }

    @Override
    public boolean checkAnswer(String answer) {
        if (answer == null || answer.trim().isEmpty()) {
            return false;
        }
        return Character.toUpperCase(answer.trim().charAt(0)) == correctOption;
    }

    @Override
    public String getCorrectAnswer() {
        int index = correctOption - 'A';
        if (index >= 0 && index < choices.length) {
            return correctOption + " (" + choices[index] + ")";
        }
        return String.valueOf(correctOption);
    }

    @Override
    public Question copyWithScoring(DifficultyLevel difficulty, int points) {
        return new MultipleChoiceQuestion(getPrompt(), choices, correctOption, difficulty, points);
    }
}
