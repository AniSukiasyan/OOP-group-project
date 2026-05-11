package OOPGroupProject.OOPGroupWork.quizgame.model;

public enum DifficultyLevel {
    EASY("Easy", 5, 8),
    MEDIUM("Medium", 10, 10),
    HARD("Hard", 15, 10),
    EXTREMELY_HARD("Extremely Hard", 25, 8);

    private final String displayName;
    private final int basePoints;
    private final int questionCount;

    DifficultyLevel(String displayName, int basePoints, int questionCount) {
        this.displayName = displayName;
        this.basePoints = basePoints;
        this.questionCount = questionCount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getBasePoints() {
        return basePoints;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
