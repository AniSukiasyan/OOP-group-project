package OOPGroupProject.OOPGroupWork.quizgame.model;

import OOPGroupProject.OOPGroupWork.quizgame.exceptions.InvalidPlayerNameException;
import OOPGroupProject.OOPGroupWork.quizgame.exceptions.InvalidScoreException;

public class Player {
    private final String name;
    private int score;

    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidPlayerNameException();
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        if (points < 0) {
            throw new InvalidScoreException(points);
        }
        score += points;
    }
}
