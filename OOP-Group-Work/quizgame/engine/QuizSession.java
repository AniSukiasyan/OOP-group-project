package quizgame.engine;

import quizgame.exceptions.EmptyAnswerException;
import quizgame.exceptions.QuizFinishedException;
import quizgame.model.Player;
import quizgame.model.Quiz;
import quizgame.questions.Question;

public class QuizSession {
    private final Quiz quiz;
    private final Player player;
    private int currentQuestionIndex;

    public QuizSession(Quiz quiz, Player player) {
        this.quiz = quiz;
        this.player = player;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public int getAnsweredQuestionCount() {
        return currentQuestionIndex;
    }

    public boolean isFinished() {
        return currentQuestionIndex >= quiz.getQuestionCount();
    }

    public Question getCurrentQuestion() {
        if (isFinished()) {
            return null;
        }
        return quiz.getQuestion(currentQuestionIndex);
    }

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

    public String getScoreSummary() {
        return quiz.getScoreSummary(player);
    }

    public String getProgressSummary() {
        return quiz.getProgressSummary(currentQuestionIndex);
    }
}
