package quizgame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import quizgame.questions.Question;

public class Quiz {
    private final String title;
    private final DifficultyLevel difficulty;
    private final List<Question> questions;

    public Quiz(String title, DifficultyLevel difficulty) {
        this.title = title;
        this.difficulty = difficulty;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public String getTitle() {
        return title;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public String getDifficultyLabel() {
        return difficulty.getDisplayName();
    }

    public int getQuestionCount() {
        return questions.size();
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public int getTotalPossibleScore() {
        int total = 0;
        for (Question question : questions) {
            total += question.getPoints();
        }
        return total;
    }

    public int getAnsweredScorePercent(Player player) {
        int total = getTotalPossibleScore();
        if (total == 0) {
            return 0;
        }
        return Math.round((player.getScore() * 100.0f) / total);
    }

    public String getScoreSummary(Player player) {
        return player.getScore() + "/" + getTotalPossibleScore()
                + " (" + getAnsweredScorePercent(player) + "%)";
    }

    public String getProgressSummary(int answeredQuestions) {
        return answeredQuestions + "/" + questions.size() + " answered";
    }
}
