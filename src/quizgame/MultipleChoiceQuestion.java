package quizgame;

public class MultipleChoiceQuestion extends Question {
    private final String[] choices;
    private final char correctOption;

    public MultipleChoiceQuestion(String prompt, String[] choices, char correctOption, int points) {
        super(prompt, points);
        this.choices = choices;
        this.correctOption = Character.toUpperCase(correctOption);
    }

    public String[] getChoices() {
        return choices.clone();
    }

    public char getCorrectOption() {
        return correctOption;
    }

    @Override
    public void display() {
        System.out.println(getPrompt());
        for (int i = 0; i < choices.length; i++) {
            char option = (char) ('A' + i);
            System.out.println(option + ". " + choices[i]);
        }
    }

    @Override
    public boolean checkAnswer(String answer) {
        if (answer.isEmpty()) {
            return false;
        }
        return Character.toUpperCase(answer.charAt(0)) == correctOption;
    }

    @Override
    public String getCorrectAnswer() {
        int index = correctOption - 'A';
        if (index >= 0 && index < choices.length) {
            return correctOption + " (" + choices[index] + ")";
        }
        return String.valueOf(correctOption);
    }
}
