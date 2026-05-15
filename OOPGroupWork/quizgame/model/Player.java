// Player.java
package OOPGroupWork.quizgame.model;

import OOPGroupWork.quizgame.exceptions.InvalidPlayerNameException;
import OOPGroupWork.quizgame.exceptions.InvalidScoreException;
/**
 * Represents a player in the quiz game.
 * <p>
 * A player has a valid name and a score that increases when correct
 * answers are submitted.
 * </p>
 */
public class Player {
    private final String name;
    private int score;

    /**
     * Creates a new player with the given name.
     * @param name the player's name
     * @throws InvalidPlayerNameException if the name is {@code null} or empty
     */
    public Player(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidPlayerNameException();
        }
        this.name = name.trim();
    }

    /**
     * Gets the player's name.
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's current score.
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds points to the player's score.
     * @param points the number of points to add
     * @throws InvalidScoreException if {@code points} is negative
     */
    public void addScore(int points) {
        if (points < 0) {
            throw new InvalidScoreException(points);
        }
        score += points;
    }
}