package quizgame.questions;

import quizgame.model.DifficultyLevel;
import quizgame.util.AnswerUtils;

public class FillInBlankQuestion extends Question {
    private final String[] acceptedAnswers;

    public FillInBlankQuestion(String prompt, String... acceptedAnswers) {
        this(prompt, DifficultyLevel.EASY, DifficultyLevel.EASY.getBasePoints(), acceptedAnswers);
    }

    public FillInBlankQuestion(String prompt, DifficultyLevel difficulty, int points, String... acceptedAnswers) {
        super(prompt, difficulty, points);
        this.acceptedAnswers = acceptedAnswers.clone();
    }

    @Override
    public AnswerType getAnswerType() {
        return AnswerType.TEXT;
    }

    @Override
    public boolean checkAnswer(String answer) {
        return AnswerUtils.matchesAny(answer, acceptedAnswers);
    }

    @Override
    public String getCorrectAnswer() {
        return AnswerUtils.formatAcceptedAnswers(acceptedAnswers);
    }

    @Override
    public Question copyWithScoring(DifficultyLevel difficulty, int points) {
        return new FillInBlankQuestion(getPrompt(), difficulty, points, acceptedAnswers);
    }
}
