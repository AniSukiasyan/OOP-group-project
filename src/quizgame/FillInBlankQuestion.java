package quizgame;

public class FillInBlankQuestion extends Question {
    private final String[] acceptedAnswers;

    public FillInBlankQuestion(String prompt, int points, String... acceptedAnswers) {
        super(prompt, points);
        this.acceptedAnswers = acceptedAnswers.clone();
    }

    @Override
    public void display() {
        System.out.println(getPrompt());
    }

    @Override
    public boolean checkAnswer(String answer) {
        return AnswerUtils.matchesAny(answer, acceptedAnswers);
    }

    @Override
    public String getCorrectAnswer() {
        return AnswerUtils.formatAcceptedAnswers(acceptedAnswers);
    }
}
